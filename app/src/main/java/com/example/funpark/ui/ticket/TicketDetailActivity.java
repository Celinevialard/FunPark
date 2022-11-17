package com.example.funpark.ui.ticket;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.funpark.R;
import com.example.funpark.adapter.ListAdapter;
import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.entity.TicketTypeEntity;
import com.example.funpark.ui.BaseActivity;
import com.example.funpark.util.OnAsyncEventListener;
import com.example.funpark.viewmodel.ticket.TicketViewModel;

import java.util.ArrayList;

public class TicketDetailActivity extends BaseActivity {

private static final String TAG = "TicketDetailActivity";


private static final int CREATE_TICKET = 0;
private static final int EDIT_TICKET = 1;
private static final int DELETE_TICKET = 2;

private Toast statusToast;

private boolean isEditable;

    private EditText etTicketName;
    private EditText etPriceSummer;
    private EditText etPriceWinter;
    private EditText etDuration;
    private Spinner spTicketType;

private TicketEntity ticket;
private ArrayList<TicketTypeEntity> ticketTypes;
ListAdapter<TicketTypeEntity> adapterTicketType;

private TicketViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_ticket_detail, frameLayout);

        setTitle(getString(R.string.title_activity_tickets));

        navigationView.setCheckedItem(tabulation);

        int ticketId = getIntent().getIntExtra("ticketId", 0);

        initiateView();

        TicketViewModel.Factory factory = new TicketViewModel.Factory(
                getApplication(), ticketId);
        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(TicketViewModel.class);
        viewModel.getTicketTypes().observe(this, ticketTypeEntities -> {
            if (ticketTypeEntities != null) {
                for (TicketTypeEntity t:ticketTypeEntities) {
                    ticketTypes.add(t);
                }
                adapterTicketType.updateData(ticketTypes);
            }
        });

        viewModel.getTicket().observe(this, ticketEntity -> {
            if (ticketEntity != null) {
                ticket = ticketEntity;
            }
            updateContent();
        });

        if (ticketId != 0) {
            setTitle(R.string.title_activity_ticket_details);
        } else {
            setTitle(R.string.title_activity_create_ticket);
            switchEditableMode();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if(ticket!= null){
            menu.add(0,EDIT_TICKET, Menu.NONE, getString(R.string.action_edit))
                    .setIcon(R.drawable.ic_mode_edit_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0,DELETE_TICKET, Menu.NONE, getString(R.string.action_delete))
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        } else{
            menu.add(0,CREATE_TICKET, Menu.NONE, getString(R.string.action_create))
                    .setIcon(R.drawable.ic_add_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == EDIT_TICKET) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_mode_edit_white_24dp);
                switchEditableMode();
            } else {
                item.setIcon(R.drawable.ic_done_white_24dp);
                switchEditableMode();
            }
        }
        if (item.getItemId() == DELETE_TICKET) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.visitor_delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel.deleteTicket(ticket, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "DeleteTicket : success");
                        onBackPressed();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "DeleteTicket : failure", e);
                    }
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }
        if (item.getItemId() == CREATE_TICKET) {
            createTicket(
                    etTicketName.getText().toString(),
                    etPriceSummer.getText().toString(),
                    etPriceWinter.getText().toString(),
                    etDuration.getText().toString(),
                    spTicketType.getSelectedItemPosition()
            );
        }
        return super.onOptionsItemSelected(item);
    }


    private void initiateView() {
        isEditable = false;
        etTicketName = findViewById(R.id.ticketName);
        etPriceSummer = findViewById(R.id.priceSummer);
        etPriceWinter = findViewById(R.id.priceWinter);
        etDuration = findViewById(R.id.duration);
        spTicketType = findViewById(R.id.ticketType);

        etTicketName.setFocusable(false);
        etTicketName.setEnabled(false);
        etPriceSummer.setFocusable(false);
        etPriceSummer.setEnabled(false);
        etPriceWinter.setFocusable(false);
        etPriceWinter.setEnabled(false);
        etDuration.setFocusable(false);
        etDuration.setEnabled(false);

        spTicketType.setFocusable(false);
        spTicketType.setEnabled(false);

        ticketTypes = new ArrayList<>();
        adapterTicketType = new ListAdapter<>(this, R.layout.row_ticket_type, new ArrayList<>());
        spTicketType.setAdapter(adapterTicketType);
    }

    private void switchEditableMode() {
        if (!isEditable) {
            etTicketName.setFocusable(true);
            etTicketName.setEnabled(true);
            etTicketName.setFocusableInTouchMode(true);

            etPriceSummer.setFocusable(true);
            etPriceSummer.setEnabled(true);
            etPriceSummer.setFocusableInTouchMode(true);

            etPriceWinter.setFocusable(true);
            etPriceWinter.setEnabled(true);
            etPriceWinter.setFocusableInTouchMode(true);

            etDuration.setFocusable(true);
            etDuration.setEnabled(true);
            etDuration.setFocusableInTouchMode(true);

            spTicketType.setFocusable(true);
            spTicketType.setEnabled(true);
            spTicketType.setFocusableInTouchMode(true);

            etTicketName.requestFocus();
        } else {
            saveChanges(
                    etTicketName.getText().toString(),
                    etPriceSummer.getText().toString(),
                    etPriceWinter.getText().toString(),
                    etDuration.getText().toString(),
                    spTicketType.getSelectedItemPosition()


            );
            etTicketName.setFocusable(false);
            etTicketName.setEnabled(false);

            etPriceSummer.setFocusable(false);
            etPriceSummer.setEnabled(false);

            etPriceWinter.setFocusable(false);
            etPriceWinter.setEnabled(false);

            etDuration.setFocusable(false);
            etDuration.setEnabled(false);

            spTicketType.setFocusable(false);
            spTicketType.setEnabled(false);

        }
        isEditable = !isEditable;
    }

    private void createTicket(String ticketname, String priceSummer, String priceWinter, String duration, int ticketType){
        ticket = new TicketEntity();
        ticket.setTicketName(ticketname);
        ticket.setPriceSummer(Double.parseDouble(priceSummer));
        ticket.setPriceWinter(Double.parseDouble(priceWinter));
        ticket.setDuration(Integer.parseInt(duration));
        ticket.setTicketType(ticketType);

        viewModel.createTicket(ticket, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "CreateTicket : success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "CreateTicket : failure", e);
            }
        });
    }

    private void saveChanges(String ticketname, String priceSummer, String priceWinter, String duration, int ticketType) {

        ticket.setTicketName(ticketname);
        ticket.setPriceSummer(Double.parseDouble(priceSummer));
        ticket.setPriceWinter(Double.parseDouble(priceWinter));
        ticket.setDuration(Integer.parseInt(duration));
        ticket.setTicketType(ticketType);

        viewModel.updateTicket(ticket, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "UpdateTicket : success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "UpdateTicket : failure", e);
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(this, getString(R.string.ticket_edited), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }

    private void updateContent() {

        spTicketType.setAdapter(adapterTicketType);

        if (ticket != null) {

            //String test1 = String.valueOf(ticket.getPriceSummer());

            //String test2 = String.valueOf(ticket.getPriceWinter());

            //String test3 = String.valueOf(ticket.getDuration());

            etTicketName.setText(ticket.getTicketName());
            etPriceSummer.setText(String.valueOf(ticket.getPriceSummer()));
            etPriceWinter.setText(String.valueOf(ticket.getPriceWinter()));
            etDuration.setText(String.valueOf(ticket.getDuration()));
            spTicketType.setSelection(ticket.getTicketType());
        }

    }


}
