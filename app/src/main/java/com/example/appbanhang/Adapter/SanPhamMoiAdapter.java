package com.example.appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Model.SanPhamMoi;
import com.example.appbanhang.R;

import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.SanPhamMoiViewHolder> {

    Context context;
    List<SanPhamMoi> list;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SanPhamMoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_moi,parent,false);
        return new SanPhamMoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamMoiViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = list.get(position);
        holder.tv_ten.setText(sanPhamMoi.getTensp());
        holder.tv_gia.setText(sanPhamMoi.getGiasp());
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.hinhanh);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SanPhamMoiViewHolder extends RecyclerView.ViewHolder{

        TextView tv_gia, tv_ten;
        ImageView hinhanh;

        public SanPhamMoiViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_gia = itemView.findViewById(R.id.item_sp_gia);
            tv_ten = itemView.findViewById(R.id.item_sp_ten);
            hinhanh = itemView.findViewById(R.id.item_image);
        }
    }
}
