/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.vimal.dailyfeed.R;
import com.vimal.dailyfeed.adapter.ViewPagerDailyFeedDisplayAdapter;
import com.vimal.dailyfeed.model.JsonItems;
import com.vimal.dailyfeed.utils.ShareHelper;

import java.io.File;
import java.util.ArrayList;

import miaoyongjun.pagetransformer.MagicTransformer;
import miaoyongjun.pagetransformer.TransitionEffect;

public class DailyFeedDisplayActivity extends AppCompatActivity {

    public static TransitionEffect transitionEffect = TransitionEffect.Zoom;
    ArrayList<JsonItems> imageUrls = new ArrayList<JsonItems>();
    LinearLayout insta, whatsapp, share;
    ViewPager viewPager;
    ViewPagerDailyFeedDisplayAdapter adapter;
    private int current_position = 0;
    private String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_feed_display);



        Intent i = getIntent();
        current_position = i.getIntExtra("position", 0);
        imageUrls = (ArrayList<JsonItems>) i.getSerializableExtra("imagelist");
        Log.e("vml", imageUrls.size() + " questions_size");
        Log.e("vml", current_position + " current_position");

        Toast.makeText(DailyFeedDisplayActivity.this, imageUrls.size() + " length", Toast.LENGTH_SHORT).show();

        findViewById();
        setAdapter();
        OnClickListener();
    }

    public void findViewById() {

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        viewPager = findViewById(R.id.viewPager);
        insta = findViewById(R.id.insta);
        whatsapp = findViewById(R.id.whatsapp);
        share = findViewById(R.id.share);
    }

    public void setAdapter() {
        if (imageUrls.size() > 0) {
            adapter = new ViewPagerDailyFeedDisplayAdapter(DailyFeedDisplayActivity.this, imageUrls);
            viewPager.setAdapter(adapter);
            viewPager.setPageTransformer(true, MagicTransformer.getPageTransformer(transitionEffect));
            viewPager.setOffscreenPageLimit(imageUrls.size());
            viewPager.setCurrentItem(current_position);
        }
    }

    public void OnClickListener() {

        File vml = new File(Environment.getExternalStorageDirectory() + "/Pictures/my favourite/20190120185144__MG_0210.JPG");
        path = vml.getAbsolutePath();

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareHelper.shareVideo(DailyFeedDisplayActivity.this, path, ShareHelper.INSTAGRAM);
//                Toast.makeText(DailyFeedDisplayActivity.this, "Instagram Share", Toast.LENGTH_SHORT).show();
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareHelper.shareVideo(DailyFeedDisplayActivity.this, path, ShareHelper.WHATSAPP);
//                Toast.makeText(DailyFeedDisplayActivity.this, "WhatsApp Share", Toast.LENGTH_SHORT).show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jpg");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_image)));
//                Toast.makeText(DailyFeedDisplayActivity.this, "Share", Toast.LENGTH_SHORT).show();
            }
        });
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