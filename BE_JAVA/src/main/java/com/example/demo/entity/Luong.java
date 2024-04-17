package com.example.demo.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "luong")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Luong {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "mucluong")
    private Long mucluong;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngaybatdau", columnDefinition = "DATE_FORMAT('YYYY/MM/DD')")
    private Date ngaybatdau;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngayketthuc", columnDefinition = "DATE_FORMAT('YYYY/MM/DD')")
    private Date ngayketthuc;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @Column(name = "theloai")
    private String theloai;

    @ManyToOne
    @JoinColumn(name = "idnhanvien")
    @JsonProperty
    private NhanVien nhanVien;
}
