package com.example.homeserve;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ProviderListActivity extends AppCompatActivity {

    private TextView tvCategory, tvEmpty;
    private RecyclerView rvProviders;

    private ArrayList<ProviderModel> providerList;
    private ProviderAdapter adapter;

    private FirebaseFirestore db;
    private String selectedCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list);

        // 1) Connect XML views
        tvCategory = findViewById(R.id.tvCategory);
        tvEmpty = findViewById(R.id.tvEmpty);
        rvProviders = findViewById(R.id.rvProviders);

        // 2) Firebase instance
        db = FirebaseFirestore.getInstance();

        // 3) Get category from previous screen
        selectedCategory = getIntent().getStringExtra("category");
        if (selectedCategory == null) selectedCategory = "";

        tvCategory.setText("Category: " + selectedCategory);

        // 4) RecyclerView setup
        providerList = new ArrayList<>();
        adapter = new ProviderAdapter(this, providerList);
        rvProviders.setLayoutManager(new LinearLayoutManager(this));
        rvProviders.setAdapter(adapter);

        // 5) Load providers
        loadProvidersTop3();
    }

    private void loadProvidersTop3() {

        db.collection("providers")
                .whereEqualTo("category", selectedCategory)
                .orderBy("rating", Query.Direction.DESCENDING)
                .limit(3)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    providerList.clear();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {

                        String id = doc.getId();
                        String name = doc.getString("name");
                        String category = doc.getString("category");
                        String phone = doc.getString("phone");

                        Double ratingObj = doc.getDouble("rating");
                        double rating = ratingObj != null ? ratingObj : 0;

                        ProviderModel model = new ProviderModel();
                        model.setId(id);
                        model.setName(name);
                        model.setCategory(category);
                        model.setPhone(phone);
                        model.setRating(rating);

                        providerList.add(model);
                    }

                    adapter.notifyDataSetChanged();

                    // Empty state
                    if (providerList.isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                    } else {
                        tvEmpty.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ProviderListActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }
}
