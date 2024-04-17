package com.example.demo.response;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienResponse {
    private UUID id;

    private String name;

    private Date namsinh;

    private String sdt;

    private Boolean gioitinh;

    private String huyen;

    private String xa;

    private String tinh;


}
