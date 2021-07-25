/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vimal.dailyfeed.R;
import com.vimal.dailyfeed.adapter.DailyFeedAdapter;
import com.vimal.dailyfeed.adapter.RingToneAdapter;
import com.vimal.dailyfeed.adapter.WallPaperAdapter;
import com.vimal.dailyfeed.model.JsonItems;

import java.util.ArrayList;
import java.util.Collections;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerviewringtone, recyclerviewwallpaper, recyclerviewdailyfeed;
    ArrayList<JsonItems> ringtonejson;
    ArrayList<JsonItems> wallpaperjson;
    ArrayList<JsonItems> dailyfeedjson;
    RingToneAdapter ringtoneadapter;
    WallPaperAdapter wallPaperAdapter;
    DailyFeedAdapter dailyFeedAdapter;
    TextView ringseeall, wallpaperseeall, dailyfeedseeall;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int REQUEST_CODE = 2296;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermission()) {
            requestPermission();
        }

        findViewById();
        AddItemsToRecyclerViewArrayList();
        setAdapter();

    }

    public void findViewById() {

        recyclerviewringtone = findViewById(R.id.recyclerviewringtone);
        recyclerviewwallpaper = findViewById(R.id.recyclerviewwallpaper);
        recyclerviewdailyfeed = findViewById(R.id.recyclerviewdailyfeed);

        ringseeall = findViewById(R.id.ringseeall);
        wallpaperseeall = findViewById(R.id.wallpaperseeall);
        dailyfeedseeall = findViewById(R.id.dailyfeedseeall);

        ringseeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RingTonesActivity.class);
                startActivity(intent);

//                File vml = new File(Environment.getExternalStorageDirectory() + "/delete.jpg");
//                if (vml.exists()) {
//                    Log.e("vml", Environment.getExternalStorageDirectory() + "/delete.jpg");
//                    Toast.makeText(MainActivity.this, "File Found", Toast.LENGTH_SHORT).show();
////                    FileHelper.delete(vml.getAbsolutePath());
//                } else {
//                    Toast.makeText(MainActivity.this, "File Not Found", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        wallpaperseeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WallPaperActivity.class);
                startActivity(intent);
            }
        });

        dailyfeedseeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DailyFeedActivity.class);
                startActivity(intent);
            }
        });


//        nextringtone = findViewById(R.id.nextringtone);
//        nextwallpaper = findViewById(R.id.nextwallpaper);
//        nextdailyfeed = findViewById(R.id.nextdailyfeed);
//
//        nextringtone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, RingTonesActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        nextwallpaper.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, WallPaperActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        nextdailyfeed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, DailyFeedActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void AddItemsToRecyclerViewArrayList() {

        ringtonejson = new ArrayList<JsonItems>();
        ringtonejson.add(new JsonItems("1", R.drawable.nature_1));
        ringtonejson.add(new JsonItems("2", R.drawable.nature_2));
        ringtonejson.add(new JsonItems("3", R.drawable.nature_3));
        ringtonejson.add(new JsonItems("4", R.drawable.nature_4));
        ringtonejson.add(new JsonItems("5", R.drawable.nature_5));
        ringtonejson.add(new JsonItems("6", R.drawable.nature_6));
        ringtonejson.add(new JsonItems("7", R.drawable.nature_7));
        ringtonejson.add(new JsonItems("8", R.drawable.nature_8));
        ringtonejson.add(new JsonItems("9", R.drawable.nature_9));
//        ringtonejson.add(new JsonItems("10", R.drawable.nature_10));
        ringtonejson.add(new JsonItems("11", R.drawable.nature_11));

        wallpaperjson = new ArrayList<JsonItems>();
        wallpaperjson.add(new JsonItems("1", R.drawable.nature_1));
        wallpaperjson.add(new JsonItems("2", R.drawable.nature_2));
        wallpaperjson.add(new JsonItems("3", R.drawable.nature_3));
        wallpaperjson.add(new JsonItems("4", R.drawable.nature_4));
        wallpaperjson.add(new JsonItems("5", R.drawable.nature_5));
        wallpaperjson.add(new JsonItems("6", R.drawable.nature_6));
        wallpaperjson.add(new JsonItems("7", R.drawable.nature_7));
        wallpaperjson.add(new JsonItems("8", R.drawable.nature_8));
        wallpaperjson.add(new JsonItems("9", R.drawable.nature_9));
//        wallpaperjson.add(new JsonItems("10", R.drawable.nature_10));
        wallpaperjson.add(new JsonItems("11", R.drawable.nature_11));

        dailyfeedjson = new ArrayList<JsonItems>();
        dailyfeedjson.add(new JsonItems("1", R.drawable.nature_1));
        dailyfeedjson.add(new JsonItems("2", R.drawable.nature_2));
        dailyfeedjson.add(new JsonItems("3", R.drawable.nature_3));
        dailyfeedjson.add(new JsonItems("4", R.drawable.nature_4));
        dailyfeedjson.add(new JsonItems("5", R.drawable.nature_5));
        dailyfeedjson.add(new JsonItems("6", R.drawable.nature_6));
        dailyfeedjson.add(new JsonItems("7", R.drawable.nature_7));
        dailyfeedjson.add(new JsonItems("8", R.drawable.nature_8));
        dailyfeedjson.add(new JsonItems("9", R.drawable.nature_9));
//        dailyfeedjson.add(new JsonItems("10", R.drawable.nature_10));
        dailyfeedjson.add(new JsonItems("11", R.drawable.nature_11));

    }

    public void setAdapter() {

        if (ringtonejson.size() > 0) {
            ringtoneadapter = new RingToneAdapter(MainActivity.this, ringtonejson);
            LinearLayoutManager HorizontalLayoutring = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerviewringtone.setLayoutManager(HorizontalLayoutring);
            recyclerviewringtone.setAdapter(ringtoneadapter);
        }

        if (wallpaperjson.size() > 0) {
//            Collections.shuffle(wallpaperjson);
            wallPaperAdapter = new WallPaperAdapter(MainActivity.this, wallpaperjson);
            LinearLayoutManager HorizontalLayoutwall = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerviewwallpaper.setLayoutManager(HorizontalLayoutwall);
            recyclerviewwallpaper.setAdapter(wallPaperAdapter);
        }

        if (dailyfeedjson.size() > 0) {
            Collections.shuffle(dailyfeedjson);
            dailyFeedAdapter = new DailyFeedAdapter(MainActivity.this, dailyfeedjson);
            LinearLayoutManager HorizontalLayoutdaily = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerviewdailyfeed.setLayoutManager(HorizontalLayoutdaily);
            recyclerviewdailyfeed.setAdapter(dailyFeedAdapter);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(MainActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(MainActivity.this, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, REQUEST_CODE);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, REQUEST_CODE);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
//                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}