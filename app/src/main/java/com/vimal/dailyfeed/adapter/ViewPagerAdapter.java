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

import com.vimal.dailyfeed.R;

import java.util.ArrayList;


public class ViewPagerAdapter extends PagerAdapter {

    ArrayList<Integer> mList = null;
    Context context;
    String url = "";
    ImageView image;

    public ViewPagerAdapter(Context context, ArrayList<Integer> list) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.dailyfeed_pager_adapter, null);

        image = container.findViewById(R.id.image);

        container.addView(view);
        return view;
    }


}