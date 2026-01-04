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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MyBookingsActivity extends AppCompatActivity {

    private RecyclerView rvBookings;
    private TextView tvEmptyBookings;

    private ArrayList<BookingModel> bookingList;
    private BookingAdapter adapter;

    private FirebaseFirestore db;
    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        rvBookings = findViewById(R.id.rvBookings);
        tvEmptyBookings = findViewById(R.id.tvEmptyBookings);

        db = FirebaseFirestore.getInstance();

        // Check login
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(MyBookingsActivity.this, LoginActivity.class));
            finish();
            return;
        }

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // RecyclerView setup
        rvBookings.setLayoutManager(new LinearLayoutManager(this));
        bookingList = new ArrayList<>();
        adapter = new BookingAdapter(this ,bookingList);
        rvBookings.setAdapter(adapter);

        loadMyBookings();
    }

    private void loadMyBookings() {

        db.collection("bookings")
                .whereEqualTo("userId", userId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    bookingList.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        String id = doc.getId();
                        String providerName = doc.getString("providerName");
                        String category = doc.getString("category");
                        String date = doc.getString("date");
                        String time = doc.getString("time");
                        String status = doc.getString("status");
                        String userIdDb = doc.getString("userId");
                        String providerPhone = doc.getString("providerPhone");
                        Double ratingObj = doc.getDouble("providerRating");
                        double providerRating = ratingObj != null ? ratingObj : 0;
                        String address = doc.getString("address");
                        String details = doc.getString("details");

                        BookingModel model = new BookingModel();
                        model.setId(id);
                        model.setProviderName(providerName);
                        model.setCategory(category);
                        model.setDate(date);
                        model.setTime(time);
                        model.setStatus(status);
                        model.setUserId(userIdDb);
                        model.setProviderPhone(providerPhone);
                        model.setProviderRating(providerRating);
                        model.setAddress(address);
                        model.setDetails(details);

                        bookingList.add(model);

                    }

                    adapter.notifyDataSetChanged();

                    if (bookingList.isEmpty()) {
                        tvEmptyBookings.setVisibility(TextView.VISIBLE);
                    } else {
                        tvEmptyBookings.setVisibility(TextView.GONE);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MyBookingsActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }
}
