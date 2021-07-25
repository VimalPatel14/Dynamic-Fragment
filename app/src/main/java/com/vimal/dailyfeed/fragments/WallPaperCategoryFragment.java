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
import com.vimal.dailyfeed.adapter.WallPaperDataPagination;
import com.vimal.dailyfeed.model.JsonItems;

import java.util.ArrayList;


public class WallPaperCategoryFragment extends Fragment {

    private RecyclerView recyclerViewFiles;
    private ArrayList<JsonItems> data = new ArrayList<>();
    int position;
    WallPaperDataPagination filesAdapter;
    int pageno = 0;

    public WallPaperCategoryFragment() {
        // Required empty public constructor
    }

    @Override

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && filesAdapter != null)
            filesAdapter.notifyDataChanged();

    }

    public static WallPaperCategoryFragment newInstance(int position) {
        WallPaperCategoryFragment fragment = new WallPaperCategoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_wallpapercategory, container, false);

        findViewById(view);
        setAdapter();
        CallApi(pageno, 10);

        return view;
    }

    public void findViewById(View view) {
        recyclerViewFiles = view.findViewById(R.id.recyclerViewFiles);
        recyclerViewFiles.setHasFixedSize(true);
        recyclerViewFiles.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    public void setAdapter() {
        filesAdapter = new WallPaperDataPagination(getActivity(), data);
        filesAdapter.setLoadMoreListener(new WallPaperDataPagination.OnLoadMoreListener() {
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
        data.clear();
        for (int i = 0; i < items; i++) {
            if (i == 0) {
                data.add(new JsonItems(position + " custom ViewPager id",
                        R.drawable.nature_1));
            } else if (i == 2) {
                data.add(new JsonItems(position + " custom ViewPager id",
                        R.drawable.nature_2));
            } else if (i == 3) {
                data.add(new JsonItems(position + " custom ViewPager id",
                        R.drawable.nature_3));
            } else if (i == 4) {
                data.add(new JsonItems(position + " custom ViewPager id",
                        R.drawable.nature_4));
            } else if (i == 5) {
                data.add(new JsonItems(position + " custom ViewPager id",
                        R.drawable.nature_5));
            } else if (i == 6) {
                data.add(new JsonItems(position + " custom ViewPager id",
                        R.drawable.nature_6));
            } else if (i == 7) {
                data.add(new JsonItems(position + " custom ViewPager id",
                        R.drawable.nature_7));
            } else if (i == 8) {
                data.add(new JsonItems(position + " custom ViewPager id",
                        R.drawable.nature_8));
            } else if (i == 9) {
                data.add(new JsonItems(position + " custom ViewPager id",
                        R.drawable.nature_9));
            } else {
                data.add(new JsonItems(position + " custom ViewPager id",
                        R.drawable.nature_10));
            }

        }
        if (data.size() > 0) {
            recyclerViewFiles.setAdapter(filesAdapter);
            filesAdapter.notifyDataChanged();
            filesAdapter.setMoreDataAvailable(true);
        }

    }

    public void LoadMore(int pageno, int items) {
        for (int i = 0; i < items; i++) {
            if (i == 0) {
                data.add(new JsonItems(position + " " + pageno + " Load More " + i,
                        R.drawable.nature_1));
            } else if (i == 2) {
                data.add(new JsonItems(position + " " + pageno + " Load More " + i,
                        R.drawable.nature_2));
            } else if (i == 3) {
                data.add(new JsonItems(position + " " + pageno + " Load More " + i,
                        R.drawable.nature_3));
            } else if (i == 4) {
                data.add(new JsonItems(position + " " + pageno + " Load More " + i,
                        R.drawable.nature_4));
            } else if (i == 5) {
                data.add(new JsonItems(position + " " + pageno + " Load More " + i,
                        R.drawable.nature_5));
            } else if (i == 6) {
                data.add(new JsonItems(position + " " + pageno + " Load More " + i,
                        R.drawable.nature_6));
            } else if (i == 7) {
                data.add(new JsonItems(position + " " + pageno + " Load More " + i,
                        R.drawable.nature_7));
            } else if (i == 8) {
                data.add(new JsonItems(position + " " + pageno + " Load More " + i,
                        R.drawable.nature_8));
            } else if (i == 9) {
                data.add(new JsonItems(position + " " + pageno + " Load More " + i,
                        R.drawable.nature_9));
            } else {
                data.add(new JsonItems(position + " " + pageno + " Load More " + i,
                        R.drawable.nature_10));
            }
        }
        if (data.size() > 0) {
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