package com.example.appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Model.SanPhamMoi;
import com.example.appbanhang.R;

import java.text.DecimalFormat;
import java.util.List;

public class DienThoaiAdapter extends RecyclerView.Adapter<DienThoaiAdapter.DienThoaiViewHolder> {

    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    Context context;
    List<SanPhamMoi> array;

    public DienThoaiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public DienThoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai,parent,false);
        return new DienThoaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DienThoaiViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = new SanPhamMoi();
        holder.ten.setText(sanPhamMoi.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.gia.setText("price"+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp())));
        holder.mota.setText(sanPhamMoi.getMota());
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.hinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public class DienThoaiViewHolder extends RecyclerView.ViewHolder {

        TextView ten,gia,mota;
        ImageView hinhanh;

        public DienThoaiViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.name_dt);
            gia = itemView.findViewById(R.id.gia_dt);
            mota = itemView.findViewById(R.id.mota_dt);
            hinhanh = itemView.findViewById(R.id.anh_dt);
        }
    }
}
