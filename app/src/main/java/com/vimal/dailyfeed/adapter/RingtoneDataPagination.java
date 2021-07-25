/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.adapter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vimal.dailyfeed.R;
import com.vimal.dailyfeed.bottomsheet.BottomDialog_Ringtones;
import com.vimal.dailyfeed.model.JsonMusic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RingtoneDataPagination extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_MOVIE = 0;
    static Context context;
    ArrayList<JsonMusic> dataSet;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    int selectpos = -1;
    MediaPlayer mediaPlayer;

    public RingtoneDataPagination(Context context, ArrayList<JsonMusic> data) {
        this.context = context;
        this.dataSet = data;
        selectpos = -1;
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_MOVIE) {
            return new MovieHolder(inflater.inflate(R.layout.ringtone_data_adapter, parent, false));
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


//            if (selectpos == position) {
//                try {
//
//                } catch (IllegalStateException e) {
//
//                }
//            } else {
//
//            }

            ((MovieHolder) holder).maillay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new BottomDialog_Ringtones.Builder(context)
                            .setTitle("Set As Ringtone!")
                            .setContent("Message")
                            .setPositiveText("Yes")
                            .onPlayPause(new BottomDialog_Ringtones.ButtonCallback() {
                                @Override
                                public void onClick(@NonNull BottomDialog_Ringtones dialog) {
                                    File vml = new File(Environment.getExternalStorageDirectory() + "/VimalAudio/audiovimal.mp3");
                                    if (vml.exists()) {
                                        playMusic(vml);
                                    } else {
                                        Toast.makeText(context, "File Not Found", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .onSetRingtone(new BottomDialog_Ringtones.ButtonCallback() {
                                @Override
                                public void onClick(@NonNull BottomDialog_Ringtones dialog) {
                                    File vml = new File(Environment.getExternalStorageDirectory() + "/VimalAudio/audiovimal.mp3");
                                    if (vml.exists()) {
                                        //Write Setting Permission For Set Ringtone
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if (Settings.System.canWrite(context)) {
                                                // Do stuff here
                                                setRingtone(context, vml.getAbsolutePath());
                                            } else {
                                                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                                intent.setData(Uri.parse("package:" + context.getPackageName()));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                context.startActivity(intent);
                                            }
                                        }else {
                                            setRingtone(context, vml.getAbsolutePath());
                                        }
                                    }

                                }
                            })
                            .onShare(new BottomDialog_Ringtones.ButtonCallback() {
                                @Override
                                public void onClick(@NonNull BottomDialog_Ringtones dialog) {
                                    Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show();
                                    File vml = new File(Environment.getExternalStorageDirectory() + "/VimalAudio/audiovimal.mp3");
                                    if (vml.exists()) {
                                        Uri vmlh = Uri.parse(vml.getAbsolutePath());
                                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                        shareIntent.setType("audio/*");
                                        shareIntent.putExtra(Intent.EXTRA_STREAM, vmlh);
                                        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_audio)));
                                    } else {
                                        Toast.makeText(context, "File Not Found", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .onPositive(new BottomDialog_Ringtones.ButtonCallback() {
                                @Override
                                public void onClick(BottomDialog_Ringtones dialog) {

                                }
                            }).show();
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

        public CardView maillay;

        public MovieHolder(View itemView) {
            super(itemView);

            maillay = itemView.findViewById(R.id.maillay);

        }
    }

    public void playMusic(File filechk) {
        if (filechk.exists()) {
            try {
                Uri vml = Uri.parse(filechk.getAbsolutePath());
                mediaPlayer = new MediaPlayer();
                if (vml != null) {
                    try {
                        mediaPlayer.setDataSource(context, vml);
                        mediaPlayer.prepare();
                        mediaPlayer.seekTo(0);
                        mediaPlayer.start();
                        mediaPlayer.setLooping(false);
                    } catch (IllegalStateException e) {
                        Toast.makeText(context, "Null URI", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Null URI", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                Toast.makeText(context, "Null File", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Null File", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Set as Ringtone catch.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
            cursor.close();
        }else {
            Toast.makeText(context, "Set as Ringtone else.", Toast.LENGTH_SHORT).show();
        }

    }

}
