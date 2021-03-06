package com.smokescreem.shash.foodscout.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smokescreem.shash.foodscout.R;
import com.smokescreem.shash.foodscout.data.DiaryData;

import java.util.ArrayList;

/**
 * Created by Shash on 5/20/2017.
 */

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.Memory> {

    private static final String TAG = "MemoryAdapter";
    ArrayList<DiaryData> memory;
    OnItemClickListener clickListener;

    public MemoryAdapter(ArrayList<DiaryData> memory, OnItemClickListener clickListener) {
        this.memory = memory;
        this.clickListener = clickListener;
    }

    @Override
    public Memory onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_card_layout, parent, false);
        return new Memory(view);
    }

    @Override
    public void onBindViewHolder(Memory holder, int position) {

        Log.d(TAG, "onBindViewHolder: " + memory.get(position).getHeader());
        holder.heading.setText(memory.get(position).getHeader());
        String message = memory.get(position).getBody();
        message = (message.length() > 120) ? message.substring(0, 120) + "..." : message;
        holder.body.setText(message);
        holder.bind(memory.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return memory.size();
    }

    public interface OnItemClickListener {
        void onItemClick(DiaryData data);
    }

    public class Memory extends RecyclerView.ViewHolder {

        TextView heading, body;

        public Memory(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.memory_author);
            body = (TextView) itemView.findViewById(R.id.memory_body);
        }

        public void bind(final DiaryData clickData, final OnItemClickListener clickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(clickData);
                }
            });
        }
    }
}