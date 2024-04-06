package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "nhan_vien")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "namsinh")
    private String namsinh;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "gioitinh")
    private boolean gioitinh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diachi")
    private DiaChi diachi;
}
