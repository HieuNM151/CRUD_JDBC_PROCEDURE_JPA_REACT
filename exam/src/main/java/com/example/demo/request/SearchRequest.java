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
public class SearchRequest {
    private String name;
    private Date namsinh;
    private String sdt;
    private String gioitinh;
    private String trangthai;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String database;
    private String server;
    private int page;
    private int pageSize;

}
