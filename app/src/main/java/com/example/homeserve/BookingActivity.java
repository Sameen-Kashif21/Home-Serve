package com.example.homeserve;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    private TextView tvProviderInfo;
    private EditText etDate, etTime, etAddress, etDetails;
    private Button btnConfirmBooking;

    private FirebaseFirestore db;

    // ✅ Make these CLASS variables (so saveBooking() can use them)
    private String userId = "";

    private String providerId = "";
    private String providerName = "";
    private String providerPhone = "";
    private double providerRating = 0;
    private String category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Views
        tvProviderInfo = findViewById(R.id.tvProviderInfo);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        etAddress = findViewById(R.id.etAddress);
        etDetails = findViewById(R.id.etDetails);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);

        db = FirebaseFirestore.getInstance();

        // ✅ If not logged in, go to Login
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(BookingActivity.this, LoginActivity.class));
            finish();
            return;
        }
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // ✅ Get provider info from Intent (ONLY ONCE)
        providerId = getIntent().getStringExtra("providerId");
        providerName = getIntent().getStringExtra("providerName");
        providerPhone = getIntent().getStringExtra("providerPhone");
        providerRating = getIntent().getDoubleExtra("providerRating", 0);
        category = getIntent().getStringExtra("category");

        if (providerId == null) providerId = "";
        if (providerName == null) providerName = "";
        if (providerPhone == null) providerPhone = "";
        if (category == null) category = "";

        // ✅ Show provider info nicely
        tvProviderInfo.setText(
                providerName + " • " + category +
                        "\n⭐ Rating: " + providerRating +
                        "\n📞 Phone: " + providerPhone
        );

        // Date picker
        etDate.setOnClickListener(v -> openDatePicker());

        // Time picker
        etTime.setOnClickListener(v -> openTimePicker());

        // Confirm booking
        btnConfirmBooking.setOnClickListener(v -> saveBooking());
    }

    private void openDatePicker() {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                BookingActivity.this,
                (view, y, m, d) -> {
                    String date = d + "/" + (m + 1) + "/" + y;
                    etDate.setText(date);
                },
                year, month, day
        );

        dialog.show();
    }

    private void openTimePicker() {
        Calendar cal = Calendar.getInstance();

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(
                BookingActivity.this,
                (view, h, m) -> {
                    String time = String.format("%02d:%02d", h, m);
                    etTime.setText(time);
                },
                hour, minute, true
        );

        dialog.show();
    }

    private void saveBooking() {
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String details = etDetails.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(date)) {
            etDate.setError("Select date");
            return;
        }

        if (TextUtils.isEmpty(time)) {
            etTime.setError("Select time");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            etAddress.setError("Enter address");
            return;
        }

        if (TextUtils.isEmpty(details)) {
            etDetails.setError("Enter problem details");
            return;
        }

        // ✅ Booking data map (use ONE name: booking)
        Map<String, Object> booking = new HashMap<>();
        booking.put("userId", userId);

        booking.put("providerId", providerId);
        booking.put("providerName", providerName);
        booking.put("providerPhone", providerPhone);
        booking.put("providerRating", providerRating);
        booking.put("category", category);

        booking.put("date", date);
        booking.put("time", time);
        booking.put("address", address);
        booking.put("details", details);
        booking.put("status", "Pending");
        booking.put("createdAt", System.currentTimeMillis());
        booking.put("userEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());

        db.collection("bookings")
                .add(booking)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(BookingActivity.this,
                            "Booking Confirmed ✅",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(BookingActivity.this, MyBookingsActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(BookingActivity.this,
                            "Booking Failed: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }
}
