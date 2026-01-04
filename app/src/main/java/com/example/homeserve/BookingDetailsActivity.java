package com.example.homeserve;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class BookingDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        TextView tvDetails = findViewById(R.id.tvDetails);
        MaterialButton btnCall = findViewById(R.id.btnCallProvider);

        // ✅ Get data from Intent (ONLY ONCE)
        String providerName = getIntent().getStringExtra("providerName");
        String category = getIntent().getStringExtra("category");
        String providerPhone = getIntent().getStringExtra("providerPhone");
        double providerRating = getIntent().getDoubleExtra("providerRating", 0);

        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String address = getIntent().getStringExtra("address");
        String problemDetails = getIntent().getStringExtra("details");
        String status = getIntent().getStringExtra("status");

        // ✅ Null safety
        if (providerName == null || providerName.trim().isEmpty()) providerName = "Unknown Provider";
        if (category == null || category.trim().isEmpty()) category = "Unknown Category";
        if (providerPhone == null) providerPhone = "";
        if (date == null) date = "";
        if (time == null) time = "";
        if (address == null) address = "";
        if (problemDetails == null) problemDetails = "";
        if (status == null) status = "";

        // ✅ Show booking details
        tvDetails.setText(
                "Provider: " + providerName +
                        "\nCategory: " + category +
                        "\n⭐ Rating: " + providerRating +
                        "\nPhone: " + providerPhone +
                        "\n\nDate: " + date +
                        "\nTime: " + time +
                        "\nAddress: " + address +
                        "\nProblem: " + problemDetails +
                        "\nStatus: " + status
        );

        // ✅ FINAL copy for lambda
        final String finalProviderPhone = providerPhone;

        // ✅ Call button
        btnCall.setOnClickListener(v -> {
            if (!finalProviderPhone.trim().isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + finalProviderPhone));
                startActivity(intent);
            } else {
                Toast.makeText(BookingDetailsActivity.this,
                        "Phone number not available",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
