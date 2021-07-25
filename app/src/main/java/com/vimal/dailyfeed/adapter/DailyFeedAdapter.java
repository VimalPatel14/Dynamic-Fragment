/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vimal.dailyfeed.R;
import com.vimal.dailyfeed.activity.DailyFeedActivity;
import com.vimal.dailyfeed.activity.DailyFeedDisplayActivity;
import com.vimal.dailyfeed.model.JsonItems;

import java.io.Serializable;
import java.util.List;

public class DailyFeedAdapter extends RecyclerView.Adapter<DailyFeedAdapter.MyView> {

    Context context;
    private List<JsonItems> list;

    public class MyView extends RecyclerView.ViewHolder {

        TextView textView;
        CardView mainlay;
        ImageView image, nextwallpaper;

        public MyView(View view) {
            super(view);

            mainlay = view.findViewById(R.id.mainlay);
            textView = view.findViewById(R.id.textview);
            image = view.findViewById(R.id.image);
            nextwallpaper = view.findViewById(R.id.nextwallpaper);
        }
    }


    public DailyFeedAdapter(Context context, List<JsonItems> horizontalList) {
        this.context = context;
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dailyfeeds_items, parent, false);
        return new MyView(itemView);
    }


    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        if ((list.size() - 1) == position) {
            holder.nextwallpaper.setVisibility(View.VISIBLE);
        } else {
            holder.nextwallpaper.setVisibility(View.GONE);
        }

        Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.nature).into(holder.image);
        holder.mainlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DailyFeedDisplayActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("imagelist", (Serializable) list);
                context.startActivity(intent);
            }
        });

        holder.nextwallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DailyFeedActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
