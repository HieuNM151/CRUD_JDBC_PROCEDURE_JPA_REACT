package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "duAn", fetch = FetchType.LAZY)
    private List<NhanVienDuAn> nhanVienDuAnList;


}
