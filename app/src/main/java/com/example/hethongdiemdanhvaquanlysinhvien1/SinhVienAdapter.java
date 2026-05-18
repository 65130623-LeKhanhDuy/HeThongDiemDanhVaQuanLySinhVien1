package com.example.hethongdiemdanhvaquanlysinhvien1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.SinhVienViewHolder> {


    private List<SinhVien> danhSachSinhVien;

    public SinhVienAdapter(List<SinhVien> danhSachSinhVien) {
        this.danhSachSinhVien = danhSachSinhVien;
    }

    @NonNull
    @Override
    public SinhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sinh_vien, parent, false);
        return new SinhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SinhVienViewHolder holder, int position) {
        SinhVien sv = danhSachSinhVien.get(position);
        if (sv == null) return;

        holder.tvHoTen.setText(sv.getHoTen());
        holder.tvMaSV.setText("Mã SV: " + sv.getMaSV());


        if (sv.isCoMat()) {
            holder.rbCoMat.setChecked(true);
        } else {
            holder.rbVangMat.setChecked(true);
        }


        holder.rgDiemDanh.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbCoMat) {
                sv.setCoMat(true);
            } else if (checkedId == R.id.rbVangMat) {
                sv.setCoMat(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (danhSachSinhVien != null) return danhSachSinhVien.size();
        return 0;
    }

    public class SinhVienViewHolder extends RecyclerView.ViewHolder {
        TextView tvHoTen, tvMaSV;
        RadioGroup rgDiemDanh;
        RadioButton rbCoMat, rbVangMat;

        public SinhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvMaSV = itemView.findViewById(R.id.tvMaSV);
            rgDiemDanh = itemView.findViewById(R.id.rgDiemDanh);
            rbCoMat = itemView.findViewById(R.id.rbCoMat);
            rbVangMat = itemView.findViewById(R.id.rbVangMat);
        }
    }


    public void filterList(List<SinhVien> filteredList) {

        this.danhSachSinhVien = filteredList;
        notifyDataSetChanged();
    }
}