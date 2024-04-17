package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "nhan_vien_du_an")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NhanVienDuAn {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idnhanvien")
    NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "idduan")
    DuAn duAn;


}
