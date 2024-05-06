package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "namsinh", columnDefinition = "DATE_FORMAT('YYYY/MM/DD')")
    private Date namsinh;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "gioitinh")
    private Boolean gioitinh;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diachi")
    @JsonIgnore
    private DiaChi diachi;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Luong> luongList;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<NhanVienDuAn> nhanVienDuAnList;

}
