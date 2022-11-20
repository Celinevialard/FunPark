package com.example.funpark.ui.salesTicket;

import android.os.Bundle;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.funpark.R;
import com.example.funpark.adapter.DateAdapter;
import com.example.funpark.database.pojo.SalesTicketWithTickets;
import com.example.funpark.ui.BaseCustomerActivity;
import com.example.funpark.viewmodel.salesTicket.SalesTicketViewModel;

/**
 * Activité pour afficher le détail d'un billet vendu
 */

public class SalesTicketDetailActivity extends BaseCustomerActivity {

    private EditText etTicket;
    private EditText etFirstname;
    private EditText etLastname;
    private EditText etBirthDate;
    private DateAdapter birthDate;

    private SalesTicketWithTickets salesTicket;

    private SalesTicketViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_sales_ticket_detail, frameLayout);

        setTitle(getString(R.string.title_activity_saleticket));

        int salesTicketId = getIntent().getIntExtra("salesTicketId", 0);

        initiateView();

        SalesTicketViewModel.Factory factory = new SalesTicketViewModel.Factory(
                getApplication(), salesTicketId);
        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(SalesTicketViewModel.class);

        viewModel.getSalesTicket().observe(this, salesTicketEntity -> {
            if (salesTicketEntity != null) {
                salesTicket = salesTicketEntity;
            }
            updateContent();
        });
    }

    private void initiateView() {
        etTicket = findViewById(R.id.ticket);
        etFirstname = findViewById(R.id.firstname);
        etLastname = findViewById(R.id.lastname);
        etBirthDate = findViewById(R.id.birthdate);

        etTicket.setFocusable(false);
        etTicket.setEnabled(false);
        etFirstname.setFocusable(false);
        etFirstname.setEnabled(false);
        etLastname.setFocusable(false);
        etLastname.setEnabled(false);
        etBirthDate.setFocusable(false);
        etBirthDate.setEnabled(false);

    }

    private void updateContent() {

        if (salesTicket != null) {

            //Chercher le nom du ticket via le pojo
            etTicket.setText(salesTicket.ticket.toString(this));
            etFirstname.setText(salesTicket.salesTicket.getFirstname());
            etLastname.setText((salesTicket.salesTicket.getLastname()));

            birthDate = new DateAdapter(salesTicket.salesTicket.getBirthDate());

            etBirthDate.setText(birthDate.toString());

        }

    }
}