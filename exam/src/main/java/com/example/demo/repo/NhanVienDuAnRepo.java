package com.example.demo.repo;

import com.example.demo.entity.DuAn;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.NhanVienDuAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NhanVienDuAnRepo extends JpaRepository<NhanVienDuAn, UUID> {
    Optional<NhanVienDuAn> findByNhanVienAndDuAn(NhanVien nhanVien, DuAn duAn);
}
