package com.example.demo.maper;

import com.example.demo.entity.DiaChi;

import java.util.UUID;

public interface NhanVienMaper {
    UUID getId();
    String getName();
    String getNamSinh();
    String getSdt();
    Boolean getGioiTinh();
    UUID getIdDiaChi();
    String getTinh();
    String getXa();
    String getHuyen();
}
