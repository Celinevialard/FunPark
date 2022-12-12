package com.example.funpark.ui.visitor;

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
import com.example.funpark.database.entity.VisitorEntity;
import com.example.funpark.ui.BaseActivity;
import com.example.funpark.util.OnAsyncEventListener;
import com.example.funpark.util.RecyclerViewItemClickListener;
import com.example.funpark.viewmodel.visitor.VisitorListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Activité pour afficher la liste des visiteurs
 * Permet la suppression d'un visiteur
 */
public class VisitorsActivity extends BaseActivity {

    private static final String TAG = "VisitorsActivity";

    private List<VisitorEntity> visitors;
    private RecyclerAdapter<VisitorEntity> adapter;
    private VisitorListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_visitors, frameLayout);

        setTitle(getString(R.string.title_activity_visitor));
        navigationView.setCheckedItem(tabulation);

        RecyclerView recyclerView = findViewById(R.id.visitorRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        visitors = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(VisitorsActivity.this, VisitorDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("visitorId", visitors.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                createDeleteDialog(position);
            }
        }, this);


        // bouton pour ajouter un visiteur
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
                    Intent intent = new Intent(VisitorsActivity.this, VisitorDetailActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    startActivity(intent);
                }
        );

        VisitorListViewModel.Factory factory = new VisitorListViewModel.Factory(
                getApplication());
        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(VisitorListViewModel.class);
        viewModel.getVisitors().observe(this, visitorEntities -> {
            if (visitorEntities != null) {
                visitors = visitorEntities;
                adapter.setData(visitors);
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
        //mettre fin manuellement à l'activité en cours
        finish();
        return super.onNavigationItemSelected(item);
    }

    /**
     * Créer une boite de dialog pour valider la suppression d'un utilisateur
     *
     * @param position
     */
    private void createDeleteDialog(final int position) {
        final VisitorEntity visitor = visitors.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.title_activity_delete_visitor));
        alertDialog.setCancelable(false);

        final TextView deleteMessage = view.findViewById(R.id.tv_delete_item);
        deleteMessage.setText(String.format(getString(R.string.visitor_delete_msg), visitor.getFirstname()));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_accept), (dialog, which) -> {
            Toast toast = Toast.makeText(this, getString(R.string.visitor_deleted), Toast.LENGTH_LONG);
            viewModel.deleteVisitor(visitor, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "deleteVisitor: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "deleteVisitor: failure", e);
                }
            });
            toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}