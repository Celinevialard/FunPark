package com.example.funpark.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.funpark.R;
import com.example.funpark.ui.salesTicket.SalesTicketsActivity;
import com.example.funpark.ui.visitor.VisitorsActivity;

/**
 * Classe pour la premier page de launch
 */
public class MainActivity extends BaseCustomerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        Button adminBtn = findViewById(R.id.btnAdmin);
        adminBtn.setOnClickListener(view -> {
            runAdmin();
        });
        Button visitorBtn = findViewById(R.id.btnVisitor);
        visitorBtn.setOnClickListener(view -> {
            runVisitor();
        });

    }

    /**
     * Lance l'activité admin
     */
    public void runAdmin() {
        Intent intent = new Intent(this, VisitorsActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NO_ANIMATION
        );
        startActivity(intent);
    }

    /**
     * Lance l'activité visiteur
     */
    public void runVisitor() {
        Intent intent = new Intent(this, SalesTicketsActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NO_ANIMATION
        );
        startActivity(intent);
    }
}