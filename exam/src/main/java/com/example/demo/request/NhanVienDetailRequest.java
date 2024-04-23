package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanVienDetailRequest {
    private String theloai;
    private Long mucluong;
    private Date ngaybatdau;
    private Date ngayketthuc;
    private Boolean trangthai;
    private UUID idnhanvien;
}
