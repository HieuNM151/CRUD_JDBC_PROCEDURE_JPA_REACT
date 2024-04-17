package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LuongRequest {
    private String theloai;
    private Long mucluong;
    private Boolean trangthai;
    private Integer pageable;
    private Integer pageSize;

}
