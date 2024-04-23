package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QLNhanVienResponse {
    private UUID id;
    private String name;
    private String namsinh;
    private String sdt;
    private Boolean gioitinh;
    private DiaChiResponse diachi;
    private List<LuongRepose> luongList;
    private long tongLuong;
}
