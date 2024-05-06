package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DuAnResponse {

    private UUID id;
    private String tenduan;
    private Date ngaybatdau;
    private Date ngayketthuc;
    private Boolean trangthai;
    private List<NhanVienDuAnResponse> listNVDA;

}
