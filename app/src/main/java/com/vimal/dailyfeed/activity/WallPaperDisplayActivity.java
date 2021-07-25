/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.activity;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vimal.dailyfeed.R;
import com.vimal.dailyfeed.adapter.ShopAdapter;
import com.vimal.dailyfeed.discretescrollview.DiscreteScrollViewOptions;
import com.vimal.dailyfeed.model.JsonItems;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import miaoyongjun.pagetransformer.TransitionEffect;

public class WallPaperDisplayActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener<ShopAdapter.ViewHolder>{

    public static TransitionEffect transitionEffect = TransitionEffect.Zoom;
    ArrayList<JsonItems> imageUrls = new ArrayList<JsonItems>();
//    ViewPagerWallpaperDisplayAdapter adaptervideo;
    LinearLayout download, share, setwallpaper;
    private int current_position = 0;
    private String path = "";
    private DiscreteScrollView itemPicker;
    ShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper_display);

        Intent i = getIntent();
        current_position = i.getIntExtra("position", 0);
        imageUrls = (ArrayList<JsonItems>) i.getSerializableExtra("imagelist");
        Log.e("vml", imageUrls.size() + " questions_size");
        Log.e("vml", current_position + " current_position");


        Toast.makeText(WallPaperDisplayActivity.this, imageUrls.size() + " length", Toast.LENGTH_SHORT).show();

        findViewById();
        setAdapter();
        setOnClickListener();

    }


    public void findViewById() {

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        itemPicker = findViewById(R.id.item_picker);
        download = findViewById(R.id.download);
        share = findViewById(R.id.share);
        setwallpaper = findViewById(R.id.setwallpaper);
    }

    public void setAdapter() {
        if (imageUrls.size() > 0) {

            itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
            itemPicker.addOnItemChangedListener(this);
            adapter = new ShopAdapter(imageUrls);
            itemPicker.setAdapter(adapter);
            itemPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
            itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());
            itemPicker.scrollToPosition(current_position);
//            itemPicker.setOverScrollEnabled(false);
        }
    }

    @Override
    public void onCurrentItemChanged(@Nullable ShopAdapter.ViewHolder viewHolder, int adapterPosition) {
//        int positionInDataSet = adapter.getRealPosition(adapterPosition);
//        onItemChanged(data.get(positionInDataSet));
    }

    public void setOnClickListener() {

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WallPaperDisplayActivity.this, "Download", Toast.LENGTH_SHORT).show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File vml = new File(Environment.getExternalStorageDirectory() + "/Pictures/my favourite/20190120185144__MG_0210.JPG");
                if (vml.exists()) {
                    path = vml.getAbsolutePath();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("image/jpg");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_image)));
                } else {
                    Toast.makeText(WallPaperDisplayActivity.this, "File Not Found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        setwallpaper.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {


                File vml = new File(Environment.getExternalStorageDirectory() + "/Pictures/my favourite/20190120185144__MG_0210.JPG");
//                if (vml.exists()) {

//                Uri photoURI = FileProvider.getUriForFile(WallPaperDisplayActivity.this, getApplicationContext().getPackageName() + ".provider", vml);
//
//                Intent intent = new Intent("android.intent.action.SET_WALLPAPER");
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                //can't use normal URI, because it requires the Uri from file
//                intent.setDataAndType(photoURI,"image/*");
//                intent.putExtra("mimeType","image/*");
//                startActivity(Intent.createChooser(intent,"Set Image"));


                        Bitmap myLogo = ((BitmapDrawable) getResources().getDrawable(R.drawable.nature_1)).getBitmap();
                        myLogo = bitmapResize(myLogo);
                        onWallpaperChanged(myLogo, true, true);

//                    new BottomDialog_WallPaper.Builder(WallPaperDisplayActivity.this)
//                            .onHomeScreen(new BottomDialog_WallPaper.ButtonCallback() {
//                                @Override
//                                public void onClick(@NonNull BottomDialog_WallPaper dialog) {
//                                    Toast.makeText(WallPaperDisplayActivity.this, "Please Wait....Set WallPaper Home Screen", Toast.LENGTH_SHORT).show();
////                                    Bitmap myBitmap = BitmapFactory.decodeFile(vml.getAbsolutePath());
//                                    Bitmap myLogo = ((BitmapDrawable) getResources().getDrawable(R.drawable.nature_1)).getBitmap();
//                                    myLogo = bitmapResize(myLogo);
////                                  myLogo = getResizedBitmap(myLogo, MyApplication.bitmap_width, MyApplication.bitmap_height);
//                                    onWallpaperChanged(myLogo, true, false);
//                                }
//                            })
//                            .onLockScreen(new BottomDialog_WallPaper.ButtonCallback() {
//                                @Override
//                                public void onClick(@NonNull BottomDialog_WallPaper dialog) {
//                                    Toast.makeText(WallPaperDisplayActivity.this, "Please Wait....Set WallPaper Lock Screen", Toast.LENGTH_SHORT).show();
////                                    Bitmap myBitmap = BitmapFactory.decodeFile(vml.getAbsolutePath());
//                                    Bitmap myLogo = ((BitmapDrawable) getResources().getDrawable(R.drawable.nature_2)).getBitmap();
//                                    myLogo = bitmapResize(myLogo);
////                                  myLogo = getResizedBitmap(myLogo, MyApplication.bitmap_width, MyApplication.bitmap_height);
//                                    onWallpaperChanged(myLogo, false, true);
//                                }
//                            })
//                            .onBothScreen(new BottomDialog_WallPaper.ButtonCallback() {
//                                @Override
//                                public void onClick(@NonNull BottomDialog_WallPaper dialog) {
//                                    Toast.makeText(WallPaperDisplayActivity.this, "Please Wait....Set WallPaper Both", Toast.LENGTH_SHORT).show();
////                                    Bitmap myBitmap = BitmapFactory.decodeFile(vml.getAbsolutePath());
//                                    Bitmap myLogo = ((BitmapDrawable) getResources().getDrawable(R.drawable.nature_3)).getBitmap();
//                                    myLogo = bitmapResize(myLogo);
////                                  myLogo = getResizedBitmap(myLogo, MyApplication.bitmap_width, MyApplication.bitmap_height);
//                                    onWallpaperChanged(myLogo, true, true);
//
////                                    Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
////                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
////                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//add this if your targetVersion is more than Android 7.0+
////                                    intent.setDataAndType(Uri.parse(vml.getAbsolutePath()), "image/jpeg");
////                                    intent.putExtra("mimeType", "image/jpeg");
////                                    startActivity(Intent.createChooser(intent, "Set as:"));
//
////                                    Uri a = Uri.parse(vml.getAbsolutePath());
////                                    if (a != null) {
////                                        Intent intent = new Intent(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER);
////                                        intent.setDataAndType(a, "image/*");
////                                        intent.putExtra("mimeType", "image/*");
////                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
////                                        startActivity(Intent.createChooser(intent, "Set as:"));
////                                    } else {
////                                        Toast.makeText(WallPaperDisplayActivity.this, "Null Uri", Toast.LENGTH_SHORT).show();
////                                    }
//
//
//                                }
//                            }).show();

//
//                } else {
//                    Toast.makeText(WallPaperDisplayActivity.this, "Null Wallpaper", Toast.LENGTH_SHORT).show();
//                }


            }
        });
    }

    public Bitmap bitmapResize(Bitmap imageBitmap) {

        Bitmap bitmap = imageBitmap;
        float heightbmp = bitmap.getHeight();
        float widthbmp = bitmap.getWidth();

        // Get Screen width
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float height = displaymetrics.heightPixels / 3;
        float width = displaymetrics.widthPixels / 3;

        int convertHeight = (int) height, convertWidth = (int) width;

        // higher
        if (heightbmp > height) {
            convertHeight = (int) height - 20;
            bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth,
                    convertHeight, true);
        }

        // wider
        if (widthbmp > width) {
            convertWidth = (int) width - 20;
            bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth,
                    convertHeight, true);
        }

        return bitmap;
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

//    public void onWallpaperChanged(Bitmap bitmap, boolean onHomeScreen, boolean onLockScreen) {
//        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(WallPaperDisplayActivity.this);
//
//        Log.e("vml_wallpaper", onHomeScreen + " onHomeScreen");
//        Log.e("vml_wallpaper", onLockScreen + " onLockScreen");
//
//        try {
//            if (onHomeScreen) {
//                myWallpaperManager.setBitmap(bitmap);// For Home screen
//            }
//            if (onLockScreen) {
//                myWallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);//For Lock screen
//            }
//            Toast.makeText(WallPaperDisplayActivity.this, "Set Wallpaper", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(WallPaperDisplayActivity.this, "Wallpaper Not Set Some Ui Problem", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void onWallpaperChanged(Bitmap bitmap, boolean onHomeScreen, boolean onLockScreen) {
        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            if (onHomeScreen) {
//                myWallpaperManager.setBitmap(bitmap);// For Home screen
                myWallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
            }
            if (onLockScreen) {
                myWallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_LOCK);//For Lock screen
            }
            Toast.makeText(WallPaperDisplayActivity.this, "Set Wallpaper", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(WallPaperDisplayActivity.this, "Wallpaper Not Set Some Ui Problem", Toast.LENGTH_SHORT).show();
        }
    }

}