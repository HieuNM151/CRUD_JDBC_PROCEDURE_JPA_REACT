package com.example.demo.service;

import com.example.demo.entity.NhanVien;
import com.example.demo.maper.NhanVienMaper;

import java.util.List;

public interface NhanVienDAO {
    List<NhanVienMaper> search(String name, String sdt, String huyen, String namsinh);
}
