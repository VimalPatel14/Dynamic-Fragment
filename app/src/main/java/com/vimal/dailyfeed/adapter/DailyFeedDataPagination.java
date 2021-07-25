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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vimal.dailyfeed.R;
import com.vimal.dailyfeed.activity.DailyFeedDisplayActivity;
import com.vimal.dailyfeed.model.JsonItems;

import java.util.ArrayList;


public class DailyFeedDataPagination extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_MOVIE = 0;
    static Context context;
    ArrayList<JsonItems> dataSet;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    int selectpos = -1;

    public DailyFeedDataPagination(Context context, ArrayList<JsonItems> data) {
        this.context = context;
        this.dataSet = data;
        selectpos = -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_MOVIE) {
            return new MovieHolder(inflater.inflate(R.layout.dailyfeed_data_adapter, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }
        if (getItemViewType(position) == TYPE_MOVIE) {

            Glide.with(context).load(dataSet.get(position).getImage()).placeholder(R.drawable.nature).into(((MovieHolder) holder).image);

            if (selectpos == position) {
                try {

                } catch (IllegalStateException e) {

                }
            } else {

            }

            ((MovieHolder) holder).maillay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DailyFeedDisplayActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("imagelist", dataSet);
                    context.startActivity(intent);
                }
            });


        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_MOVIE;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class MovieHolder extends RecyclerView.ViewHolder {

        public TextView textview;
        LinearLayout maillay;
        ImageView image;

        public MovieHolder(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.textview);
            maillay = itemView.findViewById(R.id.maillay);
            image = itemView.findViewById(R.id.image);
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }


}
