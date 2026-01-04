package com.example.homeserve;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;


public class HomeActivity extends AppCompatActivity implements ServiceAdapter.OnServiceClickListener {

    private RecyclerView rvServices;
    private Button btnMyBookings;

    private TextView btnMenu;

    private ArrayList<ServiceModel> serviceList;
    private ServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnMenu = findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(v -> showMenu(v));

        // If user is not logged in, send to Login
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
            return;
        }

        rvServices = findViewById(R.id.rvServices);
        btnMyBookings = findViewById(R.id.btnMyBookings);
      //  btnProfile = findViewById(R.id.btnProfile);

        // ✅ Open Profile screen
        //btnProfile.setOnClickListener(v -> {
          //  startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        //});

        // RecyclerView settings
        rvServices.setLayoutManager(new LinearLayoutManager(this));

        // Create service list
        serviceList = new ArrayList<>();
        serviceList.add(new ServiceModel("Electrician", "⚡"));
        serviceList.add(new ServiceModel("Plumber", "🚰"));
        serviceList.add(new ServiceModel("Mechanic", "🔧"));
        serviceList.add(new ServiceModel("AC Repair", "❄️"));
        serviceList.add(new ServiceModel("Carpenter", "🪚"));

        // Adapter
        adapter = new ServiceAdapter(serviceList, this);
        rvServices.setAdapter(adapter);

        // ✅ My Bookings button
        btnMyBookings.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MyBookingsActivity.class));
        });
    }
    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(HomeActivity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.home_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {

            int id = item.getItemId();

            if (id == R.id.menu_profile) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;
            }

            if (id == R.id.menu_mybookings) {
                startActivity(new Intent(HomeActivity.this, MyBookingsActivity.class));
                return true;
            }

            if (id == R.id.menu_privacy) {
                startActivity(new Intent(HomeActivity.this, PrivacyPolicyActivity.class));
                return true;
            }

            if (id == R.id.menu_about) {
                startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                return true;
            }

            if (id == R.id.menu_logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                return true;
            }

            return false;
        });

        popupMenu.show();
    }

    // When a service is clicked
    @Override
    public void onServiceClick(ServiceModel service) {
        Intent intent = new Intent(HomeActivity.this, ProviderListActivity.class);
        intent.putExtra("category", service.getName());
        startActivity(intent);
    }
}
