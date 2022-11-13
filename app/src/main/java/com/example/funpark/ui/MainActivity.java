package com.example.funpark.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.funpark.R;
import com.example.funpark.ui.visitor.VisitorsActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button saveBtn = findViewById(R.id.btnAdmin);
        saveBtn.setOnClickListener(view -> {
            runAdmin();
        });

    }

    public void runAdmin() {
        Intent intent = new Intent(this, VisitorsActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NO_ANIMATION
        );
        startActivity(intent);
    }
}