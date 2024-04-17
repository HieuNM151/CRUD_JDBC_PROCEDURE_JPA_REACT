package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienRequest {
    private String name;
    private Date namsinh;
    private String sdt;
    private Boolean gioitinh;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Integer pageNumber = 0;
    private Integer pageSize = 3;
}
