package com.example.homeserve;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class RoleSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        MaterialButton btnUser = findViewById(R.id.btnUser);
        MaterialButton btnWorker = findViewById(R.id.btnWorker);

        // USER -> open normal LoginActivity
        btnUser.setOnClickListener(v -> {
            Intent i = new Intent(RoleSelectionActivity.this, LoginActivity.class);
            i.putExtra("role", "user");
            startActivity(i);
        });

        // WORKER -> open WorkerLoginActivity (we will make it in Step 2)
        btnWorker.setOnClickListener(v -> {
            Intent i = new Intent(RoleSelectionActivity.this, WorkerLoginActivity.class);
            i.putExtra("role", "worker");
            startActivity(i);
        });
    }
}
