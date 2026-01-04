package com.example.homeserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.VH> {

    private final Context context;
    private final ArrayList<RequestModel> list;

    public RequestAdapter(Context context, ArrayList<RequestModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        RequestModel m = list.get(position);

        String userLine = "User: " + safe(m.getUserName()) + " (" + safe(m.getUserEmail()) + ")";
        h.tvReqUser.setText(userLine);

        String info = "📅 " + safe(m.getDate()) + "  ⏰ " + safe(m.getTime()) +
                "\n📍 " + safe(m.getAddress()) +
                "\n📝 " + safe(m.getDetails());
        h.tvReqInfo.setText(info);

        h.btnAccept.setOnClickListener(v -> updateStatus(m.getBookingId(), "Accepted"));
        h.btnDecline.setOnClickListener(v -> updateStatus(m.getBookingId(), "Declined"));
    }

    private void updateStatus(String bookingId, String newStatus) {
        Map<String, Object> update = new HashMap<>();
        update.put("status", newStatus);

        FirebaseFirestore.getInstance()
                .collection("bookings")
                .document(bookingId)
                .update(update)
                .addOnSuccessListener(unused ->
                        Toast.makeText(context, "Updated: " + newStatus, Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    private String safe(String s) {
        return (s == null) ? "" : s;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvReqUser, tvReqInfo;
        MaterialButton btnAccept, btnDecline;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvReqUser = itemView.findViewById(R.id.tvReqUser);
            tvReqInfo = itemView.findViewById(R.id.tvReqInfo);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnDecline = itemView.findViewById(R.id.btnDecline);
        }
    }
}
