package com.example.hethongdiemdanhvaquanlysinhvien1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TrangChuFragment extends Fragment {

    private TextView tvTongSV, tvDaDiemDanh;
    private DatabaseReference refSinhVien, refDiemDanh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        tvTongSV = view.findViewById(R.id.tvTongSV);
        tvDaDiemDanh = view.findViewById(R.id.tvDaDiemDanh);

        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault());
        String ngayHienTai = sdf.format(new Date());

        refSinhVien = FirebaseDatabase.getInstance().getReference("SinhVien");
        refDiemDanh = FirebaseDatabase.getInstance().getReference("DiemDanh").child(ngayHienTai);

        refSinhVien.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long tongSo = snapshot.getChildrenCount();
                tvTongSV.setText(String.valueOf(tongSo));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        refDiemDanh.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int demCoMat = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    String trangThai = data.getValue(String.class);
                    if ("CoMat".equals(trangThai)) {
                        demCoMat++;
                    }
                }
                tvDaDiemDanh.setText(String.valueOf(demCoMat));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;
    }
}