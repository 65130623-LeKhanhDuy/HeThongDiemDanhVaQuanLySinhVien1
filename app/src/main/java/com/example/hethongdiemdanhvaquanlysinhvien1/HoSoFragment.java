package com.example.hethongdiemdanhvaquanlysinhvien1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;

public class HoSoFragment extends Fragment {

    private TextView tvTenGV, tvMaGV, tvEmailGV, tvHocViGV, tvKhoaGV;
    private DatabaseReference referenceGiangVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ho_so, container, false);

        tvTenGV = view.findViewById(R.id.tvTenGVHoSo);
        tvMaGV = view.findViewById(R.id.tvMaGVHoSo);
        tvEmailGV = view.findViewById(R.id.tvEmailGVHoSo);
        tvHocViGV = view.findViewById(R.id.tvHocViGVHoSo);
        tvKhoaGV = view.findViewById(R.id.tvKhoaGVHoSo);

        String maGiangVienHienTai = "GV01";
        referenceGiangVien = FirebaseDatabase.getInstance().getReference("GiangVien").child(maGiangVienHienTai);

        referenceGiangVien.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String hoTen = snapshot.child("hoTen").getValue(String.class);
                    String maGV = snapshot.child("maGV").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String hocVi = snapshot.child("hocVi").getValue(String.class);
                    String khoa = snapshot.child("khoa").getValue(String.class);

                    tvTenGV.setText(hoTen);
                    tvMaGV.setText("Mã GV: " + maGV);
                    tvEmailGV.setText("Email: " + (email != null ? email : "Chưa cập nhật"));
                    tvHocViGV.setText("Học vị: " + (hocVi != null ? hocVi : "Chưa cập nhật"));
                    tvKhoaGV.setText("Khoa: " + (khoa != null ? khoa : "Chưa cập nhật"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Không thể kết nối hồ sơ giảng viên!", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.btnDangXuat).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đã đăng xuất hệ thống quản trị!", Toast.LENGTH_SHORT).show();

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getActivity(), LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        });

        return view;
    }
}