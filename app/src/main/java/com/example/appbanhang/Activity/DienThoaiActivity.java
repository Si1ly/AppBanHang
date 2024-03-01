package com.example.appbanhang.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.Adapter.DienThoaiAdapter;
import com.example.appbanhang.Model.SanPhamMoi;
import com.example.appbanhang.R;

import java.util.List;

public class DienThoaiActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    int page = 1;
    int loai;
    DienThoaiAdapter dienThoaiAdapter;
    List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);
        link();
        ActionBar();
        getData();
        addEventload();

    }

    private void addEventload() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoading == false){
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition()==sanPhamMoiList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                sanPhamMoiList.add(null);
                dienThoaiAdapter.notifyItemInserted(sanPhamMoiList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sanPhamMoiList.remove(sanPhamMoiList.size()-1);
                dienThoaiAdapter.notifyItemRemoved(sanPhamMoiList.size());
                page = page +1;
            }
        },2000);
    }

    private void getData() {
    }

    private void ActionBar() {
        // getSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void link(){
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleView_dt);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
    }
}