package com.example.homeserve;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private final Context context;
    private final ArrayList<BookingModel> list;

    public BookingAdapter(Context context, ArrayList<BookingModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingModel booking = list.get(position);

        // ✅ safe display (no blank)
        String name = booking.getProviderName();
        String cat = booking.getCategory();
        if (name == null || name.trim().isEmpty()) name = "Unknown Provider";
        if (cat == null || cat.trim().isEmpty()) cat = "Unknown Category";

        holder.tvProvider.setText(name);
        holder.tvCategory.setText(cat);
        holder.tvDateTime.setText("Date: " + booking.getDate() + "  Time: " + booking.getTime());
        holder.tvStatus.setText("Status: " + booking.getStatus());

        // ✅ CLICK CARD → OPEN DETAILS SCREEN
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, BookingDetailsActivity.class);

            i.putExtra("providerName", booking.getProviderName());
            i.putExtra("category", booking.getCategory());
            i.putExtra("providerPhone", booking.getProviderPhone());
            i.putExtra("providerRating", booking.getProviderRating());

            i.putExtra("date", booking.getDate());
            i.putExtra("time", booking.getTime());
            i.putExtra("address", booking.getAddress());
            i.putExtra("details", booking.getDetails());
            i.putExtra("status", booking.getStatus());

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView tvProvider, tvCategory, tvDateTime, tvStatus;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProvider = itemView.findViewById(R.id.tvBookingProvider);
            tvCategory = itemView.findViewById(R.id.tvBookingCategory);
            tvDateTime = itemView.findViewById(R.id.tvBookingDateTime);
            tvStatus = itemView.findViewById(R.id.tvBookingStatus);
        }
    }
}
