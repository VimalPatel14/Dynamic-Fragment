/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.vimal.dailyfeed.R;
import com.vimal.dailyfeed.model.JsonItems;

import java.util.ArrayList;

public class ViewPagerWallpaperDisplayAdapter extends PagerAdapter {

    Context context;
    ArrayList<JsonItems> mList = null;

    public ViewPagerWallpaperDisplayAdapter(Context context, ArrayList<JsonItems> list) {
        this.context = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_pager_adapter, null);

        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(context).load(mList.get(position).getImage()).placeholder(R.drawable.nature).into(imageView);

        container.addView(view);
        return view;
    }

}
