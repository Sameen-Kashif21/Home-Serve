package com.example.homeserve;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class WorkerHomeActivity extends AppCompatActivity {

    private TextView tvWorkerInfo, tvEmpty;
    private RecyclerView rvRequests;

    private ArrayList<RequestModel> requestList;
    private RequestAdapter adapter;

    private String providerDocId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_home);

        tvWorkerInfo = findViewById(R.id.tvWorkerInfo);
        tvEmpty = findViewById(R.id.tvEmpty);
        rvRequests = findViewById(R.id.rvRequests);

        // Must be logged in
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) {
            startActivity(new Intent(this, WorkerLoginActivity.class));
            finish();
            return;
        }

        rvRequests.setLayoutManager(new LinearLayoutManager(this));
        requestList = new ArrayList<>();
        adapter = new RequestAdapter(this, requestList);
        rvRequests.setAdapter(adapter);

        // 1) Find provider document by worker uid
        findProviderDocByUid(uid);
    }

    private void findProviderDocByUid(String uid) {
        FirebaseFirestore.getInstance()
                .collection("providers")
                .whereEqualTo("uid", uid)
                .limit(1)
                .get()
                .addOnSuccessListener(qs -> {
                    if (qs.isEmpty()) {
                        tvWorkerInfo.setText("No provider found for this worker account.");
                        tvEmpty.setText("Contact admin: provider not linked.");
                        tvEmpty.setVisibility(TextView.VISIBLE);
                        return;
                    }

                    // Get provider doc id
                    providerDocId = qs.getDocuments().get(0).getId();
                    String name = qs.getDocuments().get(0).getString("name");
                    String category = qs.getDocuments().get(0).getString("category");

                    tvWorkerInfo.setText("Logged in as: " + name + " • " + category);

                    // 2) Load pending requests for this provider
                    loadPendingRequests();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    private void loadPendingRequests() {
        FirebaseFirestore.getInstance()
                .collection("bookings")
                .whereEqualTo("providerId", providerDocId)
                .whereEqualTo("status", "Pending")
                .get()
                .addOnSuccessListener(qs -> {

                    requestList.clear();

                    for (QueryDocumentSnapshot doc : qs) {
                        RequestModel m = new RequestModel();
                        m.setBookingId(doc.getId());

                        m.setUserId(doc.getString("userId"));
                        m.setUserName(doc.getString("userName"));
                        m.setUserEmail(doc.getString("userEmail"));

                        m.setDate(doc.getString("date"));
                        m.setTime(doc.getString("time"));
                        m.setAddress(doc.getString("address"));
                        m.setDetails(doc.getString("details"));
                        m.setStatus(doc.getString("status"));

                        requestList.add(m);
                    }

                    adapter.notifyDataSetChanged();

                    if (requestList.isEmpty()) {
                        tvEmpty.setVisibility(TextView.VISIBLE);
                    } else {
                        tvEmpty.setVisibility(TextView.GONE);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
