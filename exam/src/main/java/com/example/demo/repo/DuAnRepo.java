package com.example.demo.repo;

import com.example.demo.entity.DuAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DuAnRepo extends JpaRepository<DuAn, UUID>, JpaSpecificationExecutor<DuAn> {
}
