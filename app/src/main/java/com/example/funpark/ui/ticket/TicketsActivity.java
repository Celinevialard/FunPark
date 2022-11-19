package com.example.funpark.ui.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funpark.R;
import com.example.funpark.adapter.RecyclerAdapter;
import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.entity.VisitorEntity;
import com.example.funpark.ui.BaseActivity;
import com.example.funpark.util.OnAsyncEventListener;
import com.example.funpark.util.RecyclerViewItemClickListener;
import com.example.funpark.viewmodel.ticket.TicketListViewModel;
import com.example.funpark.viewmodel.ticket.TicketViewModel;
import com.example.funpark.viewmodel.visitor.VisitorListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TicketsActivity extends BaseActivity {

    private static final String TAG = "TicketsActivity";

    private List<TicketEntity> tickets;
    private RecyclerAdapter<TicketEntity> adapter;
    private TicketListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_tickets, frameLayout);

        setTitle(getString(R.string.title_activity_tickets));
        navigationView.setCheckedItem(tabulation);

        RecyclerView recyclerView = findViewById(R.id.ticketRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // TODO
        //SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        //String user = settings.getString(BaseActivity.PREFS_USER, null);

        tickets = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + tickets.get(position).getTicketNameEn());


                Intent intent = new Intent(TicketsActivity.this,TicketDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("ticketId", tickets.get(position).getId());
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + tickets.get(position).getTicketNameEn());

                createDeleteDialog(position);
            }
        }, this);


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
                    Intent intent = new Intent(TicketsActivity.this, TicketDetailActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    startActivity(intent);
                }
        );

        TicketListViewModel.Factory factory = new TicketListViewModel.Factory(
                getApplication());
        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(TicketListViewModel.class);
        viewModel.getTickets().observe(this, ticketEntities -> {
            if (ticketEntities != null) {
                tickets = ticketEntities;
                adapter.setData(tickets);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == BaseActivity.tabulation) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        /*
        The activity has to be finished manually in order to guarantee the navigation hierarchy working.
        */
        finish();
        return super.onNavigationItemSelected(item);
    }

    private void createDeleteDialog(final int position) {
        final TicketEntity ticket = tickets.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.title_activity_delete_ticket));
        alertDialog.setCancelable(false);

        final TextView deleteMessage = view.findViewById(R.id.tv_delete_item);
        deleteMessage.setText(String.format(getString(R.string.ticket_delete_msg), ticket.getTicketNameEn()));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_accept), (dialog, which) -> {
            Toast toast = Toast.makeText(this, getString(R.string.ticket_deleted), Toast.LENGTH_LONG);
            viewModel.deleteTicket(ticket, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "deleteTicket: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "deleteTicket: failure", e);
                }
            });
            toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}
