package com.example.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanVienRequestSpecification {
        private String name;
        private Date namsinh;
        private Date namsinhTu;
        private Date namsinhDen;
        private String sdt;
        private Boolean gioitinh;
        private Integer pageable;
        private Integer pageSize;
}
