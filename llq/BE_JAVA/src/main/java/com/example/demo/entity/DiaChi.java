package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dia_chi")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "tinh")
    private String tinh;

    @Column(name = "xa")
    private String xa;

    @Column(name = "huyen")
    private String huyen;

    @OneToMany(mappedBy = "diachi", fetch = FetchType.LAZY)
    private List<NhanVien> listNhanVien;

    public DiaChi(UUID id, String tinh, String xa, String huyen) {
        this.id = id;
        this.tinh = tinh;
        this.xa = xa;
        this.huyen = huyen;
        this.listNhanVien = listNhanVien;
    }
}