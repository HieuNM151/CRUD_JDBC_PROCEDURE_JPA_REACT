package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "ngaythamgia", columnDefinition = "DATE_FORMAT('YYYY/MM/DD')")
    private Date ngaythamgia;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngayketthuc", columnDefinition = "DATE_FORMAT('YYYY/MM/DD')")
    private Date ngayketthuc;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @Transient
    private UUID nhanvienId;

    @Transient
    private UUID duAnId;

    @ManyToOne
    @JoinColumn(name = "idnhanvien")
    @JsonIgnore
    NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "idduan")
    @JsonIgnore
    DuAn duAn;


}
