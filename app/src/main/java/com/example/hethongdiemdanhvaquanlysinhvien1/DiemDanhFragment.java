package com.example.hethongdiemdanhvaquanlysinhvien1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiemDanhFragment extends Fragment {

    private RecyclerView rvDanhSachSinhVien;
    private SinhVienAdapter adapter;
    private List<SinhVien> danhSach;
    private Button btnXacNhanDiemDanh;
    private EditText edtTimKiem;

    private DatabaseReference referenceSinhVien;
    private DatabaseReference referenceDiemDanh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diem_danh, container, false);

        rvDanhSachSinhVien = view.findViewById(R.id.rvDanhSachSinhVien);
        btnXacNhanDiemDanh = view.findViewById(R.id.btnXacNhanDiemDanh);
        edtTimKiem = view.findViewById(R.id.edtTimKiem); // Ánh xạ thanh tìm kiếm

        danhSach = new ArrayList<>();
        adapter = new SinhVienAdapter(danhSach);
        rvDanhSachSinhVien.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDanhSachSinhVien.setAdapter(adapter);

        referenceSinhVien = FirebaseDatabase.getInstance().getReference("SinhVien");
        referenceDiemDanh = FirebaseDatabase.getInstance().getReference("DiemDanh");


        referenceSinhVien.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhSach.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String maSV = data.child("maSV").getValue(String.class);
                    String hoTen = data.child("hoTen").getValue(String.class);
                    Boolean coMat = data.child("coMat").getValue(Boolean.class);

                    if (coMat == null) coMat = false;

                    SinhVien sv = new SinhVien(maSV, hoTen, coMat);
                    danhSach.add(sv);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        });


        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Mỗi khi gõ 1 chữ, gọi hàm lọc danh sách
                filter(s.toString());
            }
        });


        btnXacNhanDiemDanh.setOnClickListener(v -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault());
            String ngayHienTai = sdf.format(new Date());

            for (SinhVien sv : danhSach) {
                String trangThai = sv.isCoMat() ? "CoMat" : "VangMat";
                referenceDiemDanh.child(ngayHienTai).child(sv.getMaSV()).setValue(trangThai);
            }

            Toast.makeText(getContext(), "Đã chốt sổ ngày " + ngayHienTai.replace("_", "/"), Toast.LENGTH_SHORT).show();
        });

        return view;
    }


    private void filter(String text) {
        List<SinhVien> filteredList = new ArrayList<>();

        for (SinhVien item : danhSach) {
            if (item.getHoTen().toLowerCase().contains(text.toLowerCase()) ||
                    item.getMaSV().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item); // Nếu có thì bốc người đó bỏ vào rổ kết quả
            }
        }
        adapter.filterList(filteredList);
    }
}