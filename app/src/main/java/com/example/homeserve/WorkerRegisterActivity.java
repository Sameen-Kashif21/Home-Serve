package com.example.homeserve;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WorkerRegisterActivity extends AppCompatActivity {

    private TextInputEditText etProviderDocId, etWorkerEmail, etWorkerPassword;
    private MaterialButton btnWorkerRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_register);

        etProviderDocId = findViewById(R.id.etProviderDocId);
        etWorkerEmail = findViewById(R.id.etWorkerEmail);
        etWorkerPassword = findViewById(R.id.etWorkerPassword);
        btnWorkerRegister = findViewById(R.id.btnWorkerRegister);

        findViewById(R.id.tvGoWorkerLogin).setOnClickListener(v -> {
            startActivity(new Intent(WorkerRegisterActivity.this, WorkerLoginActivity.class));
            finish();
        });

        btnWorkerRegister.setOnClickListener(v -> registerWorker());
    }

    private void registerWorker() {
        String providerDocId = etProviderDocId.getText() != null ? etProviderDocId.getText().toString().trim() : "";
        String email = etWorkerEmail.getText() != null ? etWorkerEmail.getText().toString().trim() : "";
        String pass = etWorkerPassword.getText() != null ? etWorkerPassword.getText().toString().trim() : "";

        if (TextUtils.isEmpty(providerDocId)) {
            etProviderDocId.setError("Provider Document ID required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etWorkerEmail.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(pass) || pass.length() < 6) {
            etWorkerPassword.setError("Password (min 6) required");
            return;
        }

        // 1) Create worker auth account
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {
                    String uid = FirebaseAuth.getInstance().getUid();

                    // 2) Save worker login info into providers document
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("email", email);
                    updates.put("uid", uid);

                    FirebaseFirestore.getInstance()
                            .collection("providers")
                            .document(providerDocId)
                            .update(updates)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(this, "Worker Registered ✅", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(WorkerRegisterActivity.this, WorkerHomeActivity.class));
                                finish();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Provider Doc ID wrong OR Firestore Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );

                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Register Failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
