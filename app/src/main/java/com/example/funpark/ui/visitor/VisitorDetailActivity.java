package com.example.funpark.ui.visitor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.funpark.R;
import com.example.funpark.adapter.DateAdapter;
import com.example.funpark.adapter.ListAdapter;
import com.example.funpark.database.entity.TicketTypeEntity;
import com.example.funpark.database.entity.VisitorEntity;
import com.example.funpark.ui.BaseActivity;
import com.example.funpark.util.OnAsyncEventListener;
import com.example.funpark.viewmodel.visitor.VisitorViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Activité pour afficher le détail d'un visiteur
 * Permet la modification et la suppression
 * Si le visiteur n'existe pas permet la création
 */
public class VisitorDetailActivity extends BaseActivity {

    private static final String TAG = "VisitorDetailActivity";

    private static final int CREATE_VISITOR = 0;
    private static final int EDIT_VISITOR = 1;
    private static final int DELETE_VISITOR = 2;

    private Toast statusToast;

    private boolean isEditable;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etBirthDate;
    private DatePickerDialog birthDateDialog;
    private DateAdapter birthDate;
    private EditText etVisitDate;
    private DatePickerDialog visitDateDialog;
    private DateAdapter visitDate;
    private Spinner spTicketType;

    private VisitorEntity visitor;
    private ArrayList<TicketTypeEntity> ticketTypes;
    ListAdapter<TicketTypeEntity> adapterTicketType;

    private VisitorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_visitor_detail, frameLayout);

        setTitle(getString(R.string.title_activity_visitor));

        navigationView.setCheckedItem(tabulation);

        String visitorId = getIntent().getStringExtra("visitorId");

        initiateView();

        VisitorViewModel.Factory factory = new VisitorViewModel.Factory(
                getApplication(), visitorId);
        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(VisitorViewModel.class);
        // gestion des données du spinner
        viewModel.getTicketTypes().observe(this, ticketTypeEntities -> {
            if (ticketTypeEntities != null) {
                for (TicketTypeEntity t : ticketTypeEntities) {
                    ticketTypes.add(t);
                }
                adapterTicketType.updateData(ticketTypes);
            }
        });
        // gestion des données du visiteur
        viewModel.getVisitor().observe(this, visitorEntity -> {
            if (visitorEntity != null) {
                visitor = visitorEntity;
            }
            updateContent();
        });

        if (visitorId != null) {
            setTitle(R.string.title_activity_visitor_details);
        } else {
            setTitle(R.string.title_activity_create_visitor);
            switchEditableMode();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (visitor != null && visitor.getFirstname() != null) {
            menu.add(0, EDIT_VISITOR, Menu.NONE, getString(R.string.action_edit))
                    .setIcon(R.drawable.ic_mode_edit_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, DELETE_VISITOR, Menu.NONE, getString(R.string.action_delete))
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menu.add(0, CREATE_VISITOR, Menu.NONE, getString(R.string.action_create))
                    .setIcon(R.drawable.ic_add_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_VISITOR) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_mode_edit_white_24dp);
                switchEditableMode();
            } else {
                item.setIcon(R.drawable.ic_done_white_24dp);
                switchEditableMode();
            }
        }
        if (item.getItemId() == DELETE_VISITOR) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.visitor_delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel.deleteVisitor(visitor, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "deleteVisitor: success");
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "deleteVisitor: failure", e);
                    }
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }
        if (item.getItemId() == CREATE_VISITOR) {
            createVisitor(
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    birthDate.toString(),
                    visitDate.toString(),
                    "adult"//spTicketType.getSelectedItemPosition()
            );
        }
        return super.onOptionsItemSelected(item);
    }

    private void initiateView() {
        isEditable = false;
        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etBirthDate = findViewById(R.id.birthDate);
        etVisitDate = findViewById(R.id.visitDate);
        spTicketType = findViewById(R.id.ticketType);

        etFirstName.setFocusable(false);
        etFirstName.setEnabled(false);
        etLastName.setFocusable(false);
        etLastName.setEnabled(false);
        etBirthDate.setFocusable(false);
        etBirthDate.setEnabled(false);
        etVisitDate.setFocusable(false);
        etVisitDate.setEnabled(false);

        spTicketType.setFocusable(false);
        spTicketType.setEnabled(false);

        //Initialise le spinner
        ticketTypes = new ArrayList<>();
        adapterTicketType = new ListAdapter<>(this, R.layout.row_ticket_type, new ArrayList<>());
        spTicketType.setAdapter(adapterTicketType);
    }

    private void switchEditableMode() {
        if (isEditable) {
            saveChanges(
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    birthDate.toString(),
                    visitDate.toString(),
                    "adult"//spTicketType.getSelectedItemPosition()
            );
        }

        etFirstName.setFocusable(!isEditable);
        etFirstName.setEnabled(!isEditable);
        etFirstName.setFocusableInTouchMode(!isEditable);

        etLastName.setFocusable(!isEditable);
        etLastName.setEnabled(!isEditable);
        etLastName.setFocusableInTouchMode(!isEditable);

        etBirthDate.setFocusable(!isEditable);
        etBirthDate.setEnabled(!isEditable);
        etBirthDate.setFocusableInTouchMode(!isEditable);

        etVisitDate.setFocusable(!isEditable);
        etVisitDate.setEnabled(!isEditable);
        etVisitDate.setFocusableInTouchMode(!isEditable);

        spTicketType.setFocusable(!isEditable);
        spTicketType.setEnabled(!isEditable);
        spTicketType.setFocusableInTouchMode(!isEditable);
        if (!isEditable)
            etFirstName.requestFocus();
        isEditable = !isEditable;
    }

    private void createVisitor(String firstName, String lastName, String birthDate, String visitDate, String ticketType) {

        visitor = new VisitorEntity();
        visitor.setVisitDate(visitDate);
        visitor.setBirthDate(birthDate);
        visitor.setFirstname(firstName);
        visitor.setLastname(lastName);
        visitor.setTicketType(ticketType);

        viewModel.createVisitor(visitor, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createVisitor: success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createVisitor: failure", e);
            }
        });
    }

    private void saveChanges(String firstName, String lastName, String birthDate, String visitDate, String ticketType) {

        visitor.setVisitDate(visitDate);
        visitor.setBirthDate(birthDate);
        visitor.setFirstname(firstName);
        visitor.setLastname(lastName);
        visitor.setTicketType(ticketType);

        viewModel.updateVisitor(visitor, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateVisitor: success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateVisitor: failure", e);
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(this, getString(R.string.visitor_edited), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }

    public void showDatePickerBirthDate(View view) {
        birthDateDialog.show();
    }

    public void showDatePickerVisitDate(View view) {
        visitDateDialog.show();
    }

    private void updateContent() {
        spTicketType.setAdapter(adapterTicketType);
        if (visitor != null) {
            etFirstName.setText(visitor.getFirstname());
            etLastName.setText(visitor.getLastname());
            birthDate = new DateAdapter(visitor.getBirthDate());
            visitDate = new DateAdapter(visitor.getVisitDate());

            //spTicketType.setSelection(visitor.getTicketType());
        } else {
            visitor = new VisitorEntity();
            //mettre une valeur par défaut
            Date currentTime = Calendar.getInstance().getTime();
            visitDate = new DateAdapter(currentTime);
            birthDate = new DateAdapter(currentTime);
            visitor.setVisitDate(visitDate.toString());
            visitor.setBirthDate(birthDate.toString());
        }
        // gestion du picker date
        birthDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                birthDate.setYear(year);
                birthDate.setMonth(month);
                birthDate.setDate(dayOfMonth);
                etBirthDate.setText(birthDate.toString());
            }
        }, birthDate.getYear(), birthDate.getMonth(), birthDate.getDate());
        etBirthDate.setText(birthDate.toString());

        visitDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                visitDate.setYear(year);
                visitDate.setMonth(month);
                visitDate.setDate(dayOfMonth);
                etVisitDate.setText(visitDate.toString());
            }
        }, visitDate.getYear(), visitDate.getMonth(), visitDate.getDate());
        etVisitDate.setText(visitDate.toString());
    }
}