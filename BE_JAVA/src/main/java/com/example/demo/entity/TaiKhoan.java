package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tai_khoan")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "taikhoan")
    private String taikhoan;

    @Column(name = "matkhau")
    private String matkhau;

    @Transient // Đánh dấu trường này không ánh xạ với cột trong database
    private UUID nhanvienId; // Trường này sẽ lưu ID của nhân viên

    @OneToOne
    @JoinColumn(name = "idnhanvien")
    private NhanVien nhanVien;
}
