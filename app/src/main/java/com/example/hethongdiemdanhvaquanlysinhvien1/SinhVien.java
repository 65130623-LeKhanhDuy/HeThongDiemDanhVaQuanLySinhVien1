package com.example.hethongdiemdanhvaquanlysinhvien1;

public class SinhVien {
    private String maSV;
    private String hoTen;
    private boolean isCoMat;

    public SinhVien(String maSV, String hoTen, boolean isCoMat) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.isCoMat = isCoMat;
    }

    public String getMaSV() { return maSV; }
    public void setMaSV(String maSV) { this.maSV = maSV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public boolean isCoMat() { return isCoMat; }
    public void setCoMat(boolean coMat) { isCoMat = coMat; }
}