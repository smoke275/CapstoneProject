package com.smokescreem.shash.foodscout.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smokescreem.shash.foodscout.R;

import java.util.ArrayList;

/**
 * Created by Shash on 5/20/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Review> {

    ArrayList<com.smokescreem.shash.foodscout.utils.Review> reviews;

    public ReviewAdapter(ArrayList<com.smokescreem.shash.foodscout.utils.Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public Review onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewlist_layout, parent, false);
        return new Review(view);
    }

    @Override
    public void onBindViewHolder(Review holder, int position) {
        holder.author.setText(reviews.get(position).getAuthor());
        holder.text.setText(reviews.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class Review extends RecyclerView.ViewHolder {
        TextView author, text;

        public Review(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            text = (TextView) itemView.findViewById(R.id.message);
        }
    }
}