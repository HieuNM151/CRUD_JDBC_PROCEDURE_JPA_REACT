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
public class QLNhanVienNative {
    private UUID id;
    private String name;
    private Date namsinh;
    private String sdt;
    private Boolean gioitinh;
    private String tinh;
    private String xa;
    private String huyen;
}
