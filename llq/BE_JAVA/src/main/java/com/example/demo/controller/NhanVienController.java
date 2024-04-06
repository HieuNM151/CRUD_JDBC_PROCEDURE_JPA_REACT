package com.example.demo.controller;

import com.example.demo.entity.NhanVien;
import com.example.demo.maper.NhanVienMaper;
import com.example.demo.service.NhanVienDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nhan-vien")
public class NhanVienController {
    private NhanVienDAO nhanVienDAO;

    @Autowired
    public NhanVienController(NhanVienDAO nhanVienDAO){
        this.nhanVienDAO = nhanVienDAO;
    }

    @GetMapping("/search")
    public ResponseEntity<List<NhanVienMaper>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sdt", required = false) String sdt,
            @RequestParam(value = "huyen", required = false) String huyen,
            @RequestParam(value = "namsinh", required = false) String namsinh
    ){
        List<NhanVienMaper> result = nhanVienDAO.search(name, sdt, huyen, namsinh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
