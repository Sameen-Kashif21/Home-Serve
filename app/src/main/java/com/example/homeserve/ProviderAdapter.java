package com.example.homeserve;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ProviderModel> list;

    public ProviderAdapter(Context context, ArrayList<ProviderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_provider, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        ProviderModel p = list.get(position);

        h.tvName.setText(p.getName());
        h.tvCategory.setText(p.getCategory());
        h.tvPhone.setText("Phone: " + p.getPhone());
        h.tvStars.setText(makeStars(p.getRating()) + " (" + p.getRating() + ")");

        // ✅ CALL button
        h.btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + p.getPhone()));
            context.startActivity(intent);
        });

        // ✅ BOOK NOW button
        h.btnBook.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingActivity.class);
            intent.putExtra("providerId", p.getId());
            intent.putExtra("providerName", p.getName());
            intent.putExtra("providerPhone", p.getPhone());
            intent.putExtra("providerRating", p.getRating());
            intent.putExtra("category", p.getCategory());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvCategory, tvPhone, tvStars;
        MaterialButton btnCall, btnBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProviderName);
            tvCategory = itemView.findViewById(R.id.tvProviderCategory);
            tvPhone = itemView.findViewById(R.id.tvProviderPhone);
            tvStars = itemView.findViewById(R.id.tvProviderStars);

            btnCall = itemView.findViewById(R.id.btnCall);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }

    private String makeStars(double rating) {
        int r = (int) Math.round(rating);
        if (r < 1) r = 1;
        if (r > 5) r = 5;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < r; i++) sb.append("⭐");
        return sb.toString();
    }
}
