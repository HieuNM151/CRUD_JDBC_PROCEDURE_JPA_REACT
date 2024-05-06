package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "du_an")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DuAn {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tenduan")
    private String tenduan;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngaybatdau", columnDefinition = "DATE_FORMAT('YYYY/MM/DD')")
    private Date ngaybatdau;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngayketthuc", columnDefinition = "DATE_FORMAT('YYYY/MM/DD')")
    private Date ngayketthuc;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @OneToMany(mappedBy = "duAn", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("duAn")
    private List<NhanVienDuAn> nhanVienDuAnList;


}
