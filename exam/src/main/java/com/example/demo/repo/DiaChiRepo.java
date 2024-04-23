package com.example.demo.repo;

import com.example.demo.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiaChiRepo extends JpaRepository<DiaChi, UUID> {


//    Optional<DiaChi> findById(UUID uuid);
}
