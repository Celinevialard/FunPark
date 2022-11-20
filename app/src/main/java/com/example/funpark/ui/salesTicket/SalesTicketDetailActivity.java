package com.example.funpark.ui.salesTicket;

import android.os.Bundle;

import com.example.funpark.R;
import com.example.funpark.ui.BaseCustomerActivity;

public class SalesTicketDetailActivity extends BaseCustomerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_sales_ticket_detail, frameLayout);
        setTitle(R.string.title_activity_settings);
    }
}