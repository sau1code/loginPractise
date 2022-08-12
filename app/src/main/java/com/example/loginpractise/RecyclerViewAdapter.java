package com.example.loginpractise;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> listName, listInfo;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewCardName, textViewCardInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCardName = (TextView)itemView.findViewById(R.id.textView_card_name);
            textViewCardInfo = (TextView)itemView.findViewById(R.id.textView_card_info);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), listName.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }

    public RecyclerViewAdapter(Map<String, String> map) {
        listName = new ArrayList<>();
        listInfo = new ArrayList<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                listName.add(entry.getKey());
                listInfo.add(entry.getValue());
            }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewCardName.setText(listName.get(position));
        holder.textViewCardInfo.setText(listInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return listName.size();
    }
}
