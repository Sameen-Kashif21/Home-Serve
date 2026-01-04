package com.example.homeserve;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private ArrayList<ServiceModel> serviceList;
    private OnServiceClickListener listener;

    // Interface to detect clicks
    public interface OnServiceClickListener {
        void onServiceClick(ServiceModel service);
    }

    // Constructor
    public ServiceAdapter(ArrayList<ServiceModel> serviceList, OnServiceClickListener listener) {
        this.serviceList = serviceList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Load item_service.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);

        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {

        ServiceModel service = serviceList.get(position);

        holder.tvServiceName.setText(service.getName());
        holder.tvIcon.setText(service.getIcon());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onServiceClick(service);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    // ViewHolder = holds item_service.xml views
    static class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView tvServiceName, tvIcon;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            tvIcon = itemView.findViewById(R.id.tvIcon);
        }
    }
}
