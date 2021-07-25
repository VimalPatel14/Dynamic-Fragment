/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vimal.dailyfeed.R;
import com.vimal.dailyfeed.adapter.RingtoneDataPagination;
import com.vimal.dailyfeed.model.JsonMusic;

import java.util.ArrayList;


public class RingtoneCategoryFragment extends Fragment {

    private RecyclerView recyclerViewFiles;
    private ArrayList<JsonMusic> music_data = new ArrayList<>();
    int position;
    RingtoneDataPagination filesAdapter;
    int pageno = 0;

    public RingtoneCategoryFragment() {
        // Required empty public constructor
    }

    @Override

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && filesAdapter != null)
            filesAdapter.notifyDataChanged();

    }

    public static RingtoneCategoryFragment newInstance(int position) {
        RingtoneCategoryFragment fragment = new RingtoneCategoryFragment();
        fragment.position = position;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ringtonecategory, container, false);


        findViewById(view);
        SetAdapter();
        CallApi(pageno, 10);

        return view;
    }

    public void findViewById(View view) {
        recyclerViewFiles = view.findViewById(R.id.recyclerViewFiles);
        recyclerViewFiles.setHasFixedSize(true);
        recyclerViewFiles.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }

    public void SetAdapter() {
        filesAdapter = new RingtoneDataPagination(getActivity(), music_data);

        filesAdapter.setLoadMoreListener(new RingtoneDataPagination.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerViewFiles.post(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("vml", "loadmore");
                        pageno = pageno + 1;
                        LoadMore(pageno, 10);
                    }
                });
            }
        });

        recyclerViewFiles.setAdapter(filesAdapter);
    }


    public void CallApi(int pageno, int items) {
        music_data.clear();
        for (int i = 0; i < items; i++) {
            if ((i % 2) == 0) {
                music_data.add(new JsonMusic(position + " custom ViewPager id",
                        "A202112543553691.mp3"));
            } else {
                music_data.add(new JsonMusic(position + " custom ViewPager id",
                        "A20211253588103.mp3"));

            }

        }
        if (music_data.size() > 0) {
            recyclerViewFiles.setAdapter(filesAdapter);
            filesAdapter.notifyDataChanged();
            filesAdapter.setMoreDataAvailable(true);
        }

    }

    public void LoadMore(int pageno, int items) {
        for (int i = 0; i < items; i++) {
            music_data.add(new JsonMusic(position + " " + pageno + " Load More " + i,
                    position + " " + pageno + " Load More " + i));
        }
        if (music_data.size() > 0) {
            filesAdapter.notifyDataChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        filesAdapter.notifyDataChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}