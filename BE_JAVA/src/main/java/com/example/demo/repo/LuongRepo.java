package com.example.demo.repo;

import com.example.demo.entity.Luong;
import com.example.demo.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LuongRepo extends JpaRepository<Luong, UUID>, JpaSpecificationExecutor<Luong> {
}
