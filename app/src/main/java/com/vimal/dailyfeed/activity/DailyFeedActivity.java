/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.vimal.dailyfeed.R;
import com.vimal.dailyfeed.adapter.ViewPagerDailyfeedAdapter;

import java.util.ArrayList;
import java.util.List;

public class DailyFeedActivity extends AppCompatActivity {

    ViewPager viewpager;
    private TabLayout tabLayout;
    ViewPagerDailyfeedAdapter adapter;
    View Previous_view = null;
    public int selectedpos = 0;
    List<String> tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_feed);

        findViewById();
        setAdapter();
    }

    public void findViewById() {

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        viewpager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
    }

    public void setAdapter() {

        tabs = new ArrayList<>();
        tabs.add("Trending");
        tabs.add("Life");
        tabs.add("Thought");
        tabs.add("Wishes");

        adapter = new ViewPagerDailyfeedAdapter(getSupportFragmentManager(), tabs);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager); // set title to tabs
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(0);
        setupTabIcons();

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("vml", " onTabSelected");
                setOnSelectedView(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupTabIcons() {

        Log.e("vml", tabLayout.getTabCount() + " tabLayout_size");

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            Log.e("vml", i + " tabLayout");

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) tab.setCustomView(getTabView(i));
        }
    }

    public View getTabView(int position) {

        Log.e("vml", " getTabView");

        View v = LayoutInflater.from(DailyFeedActivity.this).inflate(R.layout.custom_tabnew, null);
        TextView textView = v.findViewById(R.id.textview);
        textView.setText(tabs.get(position));
        ImageView imageView = v.findViewById(R.id.image);
        if (position == selectedpos) {
            Previous_view = v;
            textView.setTextColor(getResources().getColor(R.color.white));
        } else {
            textView.setTextColor(getResources().getColor(R.color.text_unselect));
        }
        String url = "";
        if (position == 1) {
            url = "https://homepages.cae.wisc.edu/~ece533/images/pool.png";
        } else {
            url = "http://www.behindlogicinc.com/upload/videoly1/FX202107149298/sample.webp";
        }

        Glide.with(DailyFeedActivity.this).load(url).placeholder(R.mipmap.ic_launcher).into(imageView);

        return v;
    }

    public void setOnSelectedView(TabLayout.Tab tab) {

        Log.e("vml", " setOnSelectedView");

        if (tab != null) {
            setunSelectedView(Previous_view);
            View selected = tab.getCustomView();
            Previous_view = selected;
            TextView num = selected.findViewById(R.id.textview);
//            RelativeLayout selctionlay = selected.findViewById(R.id.selctionlay);
//            selctionlay.setBackground(getResources().getDrawable(R.drawable.category_press));
            num.setTextColor(getResources().getColor(R.color.white));
            tab.select();
        }
    }

    public void setunSelectedView(View selected) {

        Log.e("vml", " setunSelectedView");

        if (selected != null) {
            TextView num = selected.findViewById(R.id.textview);
//            RelativeLayout selctionlay = selected.findViewById(R.id.selctionlay);
//            selctionlay.setBackground(getResources().getDrawable(R.drawable.category_unpress));
            num.setTextColor(getResources().getColor(R.color.text_unselect));
        }
    }
}