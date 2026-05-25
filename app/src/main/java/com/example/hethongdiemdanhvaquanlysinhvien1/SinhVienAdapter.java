package com.example.hethongdiemdanhvaquanlysinhvien1;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.FirebaseDatabase;
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


        holder.itemView.setOnLongClickListener(v -> {
            // Khởi tạo hộp thoại xác nhận bằng Context của chính dòng vừa ấn
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setTitle("Xóa Sinh Viên Khỏi Lớp");
            builder.setMessage("Thầy có chắc chắn muốn xóa sinh viên " + sv.getHoTen() + " (Mã SV: " + sv.getMaSV() + ") khỏi danh sách lớp học không?");


            builder.setPositiveButton("XÓA NGAY", (dialog, which) -> {

                FirebaseDatabase.getInstance()
                        .getReference("SinhVien")
                        .child(sv.getMaSV())
                        .removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(holder.itemView.getContext(), "Đã xóa thành công sinh viên " + sv.getHoTen(), Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(holder.itemView.getContext(), "Lỗi hệ thống mạng, chưa thể xóa!", Toast.LENGTH_SHORT).show();
                        });
            });


            builder.setNegativeButton("HỦY BỎ", (dialog, which) -> dialog.dismiss());


            builder.show();

            return true;
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