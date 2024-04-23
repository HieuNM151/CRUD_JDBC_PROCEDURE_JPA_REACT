package com.example.demo.response;

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
public class LuongRepose {
    private UUID id;
    private String theloai;
    private Long mucluong;
    private Date ngaybatdau;
    private Date ngayketthuc;
    private Boolean trangthai;
}
