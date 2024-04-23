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
public class NhanVienRequesJDBC {
    private String name;
    private String sdt;
    private String huyen;
    private Date namsinh;
    private Date namsinhTu;
    private Date namsinhDen;
    private Integer pageNumber;
    private Integer pageSize;
}
