package com.example.demo.maper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanVienMaper {
    private String name;
    private String namsinh;
    private String sdt;
    private boolean gioitinh;
    private String tinh;
    private String xa;
    private String huyen;
}
