package com.example.funpark.ui.salesTicket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funpark.R;
import com.example.funpark.adapter.RecyclerAdapter;
import com.example.funpark.database.entity.SalesTicketEntity;
import com.example.funpark.ui.BaseCustomerActivity;
import com.example.funpark.util.RecyclerViewItemClickListener;
import com.example.funpark.viewmodel.salesTicket.SalesTicketListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Activité pour afficher la liste des billets vendus
 */

public class SalesTicketsActivity extends BaseCustomerActivity {

    private static final String TAG = "SalesTicketsActivity";

    private List<SalesTicketEntity> salesTickets;
    private RecyclerAdapter<SalesTicketEntity> adapter;
    private SalesTicketListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_sales_tickets, frameLayout);
        setTitle(R.string.title_activity_salestickets);

        RecyclerView recyclerView = findViewById(R.id.salesTicketRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        salesTickets = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + salesTickets.get(position).getFirstname());


                Intent intent = new Intent(SalesTicketsActivity.this, SalesTicketDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("salesTicketId", salesTickets.get(position).getId());
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + salesTickets.get(position).getId());

            }
        }, this);

        SalesTicketListViewModel.Factory factory = new SalesTicketListViewModel.Factory(
                getApplication());
        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(SalesTicketListViewModel.class);
        viewModel.getSalesTickets().observe(this, salesTicketEntities -> {
            if (salesTicketEntities != null) {
                salesTickets = salesTicketEntities;
                adapter.setData(salesTickets);
            }
        });

        recyclerView.setAdapter(adapter);

    }


}