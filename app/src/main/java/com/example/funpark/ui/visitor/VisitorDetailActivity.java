package com.example.funpark.ui.visitor;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

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

        int visitorId = getIntent().getIntExtra("visitorId", 0);

        initiateView();

        VisitorViewModel.Factory factory = new VisitorViewModel.Factory(
                getApplication(), visitorId);
        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(VisitorViewModel.class);
        viewModel.getTicketTypes().observe(this, ticketTypeEntities ->{
            if(ticketTypeEntities!=null){
                for (TicketTypeEntity t:ticketTypeEntities) {
                    ticketTypes.add(t);
                }
                adapterTicketType.updateData(ticketTypes);
            }
        });
        viewModel.getVisitor().observe(this, visitorEntity -> {
            if (visitorEntity != null) {
                visitor = visitorEntity;
            }
            updateContent();
        });

        if (visitorId != 0) {
            setTitle(R.string.title_activity_visitor_details);
        } else {
            setTitle(R.string.title_activity_create_visitor);
            switchEditableMode();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (visitor != null && visitor.getFirstName()!=null)  {
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
                    birthDate,
                    visitDate,
                    spTicketType.getSelectedItemPosition()
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

        ticketTypes = new ArrayList<>();
        adapterTicketType = new ListAdapter<>(this, R.layout.row_ticket_type, new ArrayList<>());
        spTicketType.setAdapter(adapterTicketType);
        }

    private void switchEditableMode() {
        if (!isEditable) {
            etFirstName.setFocusable(true);
            etFirstName.setEnabled(true);
            etFirstName.setFocusableInTouchMode(true);

            etLastName.setFocusable(true);
            etLastName.setEnabled(true);
            etLastName.setFocusableInTouchMode(true);

            etBirthDate.setFocusable(true);
            etBirthDate.setEnabled(true);
            etBirthDate.setFocusableInTouchMode(true);

            etVisitDate.setFocusable(true);
            etVisitDate.setEnabled(true);
            etVisitDate.setFocusableInTouchMode(true);

            spTicketType.setFocusable(true);
            spTicketType.setEnabled(true);
            spTicketType.setFocusableInTouchMode(true);

            etFirstName.requestFocus();
        } else {
            saveChanges(
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    birthDate,
                    visitDate,
                    spTicketType.getSelectedItemPosition()
            );
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

        }
        isEditable = !isEditable;
    }

    private void createVisitor(String firstName, String lastName, Date birthDate, Date visitDate, int ticketType) {

        visitor = new VisitorEntity();
        visitor.setVisitDate(visitDate);
        visitor.setBirthDate(birthDate);
        visitor.setFirstName(firstName);
        visitor.setLastName(lastName);
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

    private void saveChanges(String firstName, String lastName, Date birthDate, Date visitDate, int ticketType) {

        visitor.setVisitDate(visitDate);
        visitor.setBirthDate(birthDate);
        visitor.setFirstName(firstName);
        visitor.setLastName(lastName);
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

    public void showDatePickerBirthDate(View view){
       birthDateDialog.show();
    }

    public void showDatePickerVisitDate(View view){
        visitDateDialog.show();
    }

    private void updateContent() {

        spTicketType.setAdapter(adapterTicketType);
       if (visitor != null) {
            etFirstName.setText(visitor.getFirstName());
            etLastName.setText(visitor.getLastName());
            spTicketType.setSelection(visitor.getTicketType());
        }else {
           visitor = new VisitorEntity();
           Date currentTime = Calendar.getInstance().getTime();
           visitor.setVisitDate(currentTime);
           visitor.setBirthDate(currentTime);
       }
        birthDate = new DateAdapter(visitor.getBirthDate());
        birthDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                birthDate.setYear(year);
                birthDate.setMonth(month);
                birthDate.setDate(dayOfMonth);
                etBirthDate.setText(birthDate.toString());
            }
        },birthDate.getYear(),birthDate.getMonth(), birthDate.getDate());
        etBirthDate.setText(birthDate.toString());

        visitDate=new DateAdapter(visitor.getVisitDate());
        visitDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                visitDate.setYear(year);
                visitDate.setMonth(month);
                visitDate.setDate(dayOfMonth);
                etVisitDate.setText(visitDate.toString());
            }
        },visitDate.getYear(),visitDate.getMonth(), visitDate.getDate());
        etVisitDate.setText(visitDate.toString());
    }
}