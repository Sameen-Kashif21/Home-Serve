package com.example.homeserve;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class WorkerLoginActivity extends AppCompatActivity {

    private TextInputEditText etWorkerEmail, etWorkerPassword;
    private MaterialButton btnWorkerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_login);

        etWorkerEmail = findViewById(R.id.etWorkerEmail);
        etWorkerPassword = findViewById(R.id.etWorkerPassword);
        btnWorkerLogin = findViewById(R.id.btnWorkerLogin);

        findViewById(R.id.tvGoWorkerRegister).setOnClickListener(v -> {
            startActivity(new Intent(WorkerLoginActivity.this, WorkerRegisterActivity.class));
            finish();
        });

        btnWorkerLogin.setOnClickListener(v -> loginWorker());
    }

    private void loginWorker() {
        String email = etWorkerEmail.getText() != null ? etWorkerEmail.getText().toString().trim() : "";
        String pass = etWorkerPassword.getText() != null ? etWorkerPassword.getText().toString().trim() : "";

        if (TextUtils.isEmpty(email)) {
            etWorkerEmail.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            etWorkerPassword.setError("Password required");
            return;
        }

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "Worker Login Successful ✅", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(WorkerLoginActivity.this, WorkerHomeActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Login Failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
