/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.vimal.dailyfeed.R;
import com.vimal.dailyfeed.adapter.ViewPagerRingtoneAdapter;

import java.io.File;
import java.util.ArrayList;

public class RingTonesActivity extends AppCompatActivity {

    ViewPager viewpager;
    private TabLayout tabLayout;
    ViewPagerRingtoneAdapter adapter;
    ArrayList<String> tabs;
    public int selectedpos = 0;
    View Previous_view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_tones);

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
        tabs.add("Ringtone 2021");
        tabs.add("Popular");
        tabs.add("Sound Effect");
        tabs.add("Bollywood");

        adapter = new ViewPagerRingtoneAdapter(getSupportFragmentManager(), tabs);
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

        View v = LayoutInflater.from(RingTonesActivity.this).inflate(R.layout.custom_tabnew, null);
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

        Glide.with(RingTonesActivity.this).load(url).placeholder(R.mipmap.ic_launcher).into(imageView);

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


    private void setRingtone(Context context, String path) {
        if (path == null) {
            return;
        }
        File file = new File(path);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
        String filterName = path.substring(path.lastIndexOf("/") + 1);
        contentValues.put(MediaStore.MediaColumns.TITLE, filterName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        contentValues.put(MediaStore.MediaColumns.SIZE, file.length());
        contentValues.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(path);
        Cursor cursor = context.getContentResolver().query(uri, null, MediaStore.MediaColumns.DATA + "=?", new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            String id = cursor.getString(0);
            contentValues.put(MediaStore.Audio.Media.IS_RINGTONE, true);
            context.getContentResolver().update(uri, contentValues, MediaStore.MediaColumns.DATA + "=?", new String[]{path});
            Uri newuri = ContentUris.withAppendedId(uri, Long.valueOf(id));
            try {
                RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newuri);
                Toast.makeText(context, "Set as Ringtone Successfully.", Toast.LENGTH_SHORT).show();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            cursor.close();
        }
    }
}