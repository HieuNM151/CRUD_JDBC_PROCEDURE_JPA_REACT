package com.example.demo.controller;

import com.example.demo.entity.NhanVienDuAn;
import com.example.demo.request.NhanVienDuAnRequest;
import com.example.demo.response.MessageResponse;
import com.example.demo.sevice.NhanVienDuAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/nhan-vien-du-an")
public class NhanVienDuAnController {

    @Autowired
    private NhanVienDuAnService nhanVienDuAnService;

    @PostMapping("/create")
    public ResponseEntity<NhanVienDuAn> addNhanVienDuAn(@RequestBody NhanVienDuAnRequest nhanVienDuAnRequest) {
            NhanVienDuAn nhanVienDuAn = nhanVienDuAnService.addNhanVienDuAn(nhanVienDuAnRequest);
            return ResponseEntity.ok().body(nhanVienDuAn);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") UUID id) {
        nhanVienDuAnService.delete(id);
        return ResponseEntity.ok().body(MessageResponse.builder().message("Xóa thành công").build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable("id") UUID id) {
        nhanVienDuAnService.update(id);
        return ResponseEntity.ok().body(MessageResponse.builder().message("sửa thành công").build());
    }
}
