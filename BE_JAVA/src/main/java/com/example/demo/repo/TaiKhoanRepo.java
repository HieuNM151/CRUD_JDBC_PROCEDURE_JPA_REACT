package com.example.demo.repo;

import com.example.demo.entity.NhanVien;
import com.example.demo.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaiKhoanRepo extends JpaRepository<TaiKhoan, UUID> {
    TaiKhoan findByNhanVien(NhanVien nhanVien);
}
