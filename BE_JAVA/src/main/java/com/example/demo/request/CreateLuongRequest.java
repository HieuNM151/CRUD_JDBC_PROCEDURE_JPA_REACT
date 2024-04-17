package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateLuongRequest {
    private String theloai;
    private Long mucluong;
    private Boolean trangthai;
    private Date ngaybatdau;
    private Date ngayketthuc;
}
