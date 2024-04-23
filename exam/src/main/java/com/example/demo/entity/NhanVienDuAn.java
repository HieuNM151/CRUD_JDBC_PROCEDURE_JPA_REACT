package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    @Column(name = "ngaythamgia")
    private Date ngaythamgia;

    @Column(name = "ngayketthuc")
    private Date ngayketthuc;

    @ManyToOne
    @JoinColumn(name = "idnhanvien")
    NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "idduan")
    DuAn duAn;


}
