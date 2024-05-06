package com.example.demo.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanVienDuAnResponse {
    private UUID id;
    private Date ngaythamgia;
    private Date ngayketthuc;
    private Boolean trangthai;
    private String name;
    private Date namsinh;
}
