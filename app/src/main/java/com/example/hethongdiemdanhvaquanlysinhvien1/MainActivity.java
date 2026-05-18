package com.example.hethongdiemdanhvaquanlysinhvien1;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView thanhDieuHuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thanhDieuHuong = findViewById(R.id.thanhDieuHuong);

        // Mặc định hiển thị màn hình Trang Chủ khi vừa mở ứng dụng
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.khungChuaFragment, new TrangChuFragment())
                    .commit();
        }

        // Bắt sự kiện người dùng bấm chuyển các tab dưới đáy màn hình
        thanhDieuHuong.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment phanManhDuocChon = null;
                int idNut = item.getItemId(); // Chữ I đã được viết hoa chuẩn xác

                if (idNut == R.id.mnuTrangChu) {
                    phanManhDuocChon = new TrangChuFragment();
                } else if (idNut == R.id.mnuDiemDanh) {
                    phanManhDuocChon = new DiemDanhFragment();
                } else if (idNut == R.id.mnuHoSo) {
                    phanManhDuocChon = new HoSoFragment();
                }

                if (phanManhDuocChon != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.khungChuaFragment, phanManhDuocChon)
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }
}