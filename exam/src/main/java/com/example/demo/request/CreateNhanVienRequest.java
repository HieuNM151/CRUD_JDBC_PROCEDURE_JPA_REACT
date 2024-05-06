package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateNhanVienRequest {
    private String name;
    private Date namsinh;
    private String sdt;
    private Boolean gioitinh;
    private Boolean trangthai;
    private UUID diachi;
    private String taikhoan;
    private String matkhau;
    private String email;

}
