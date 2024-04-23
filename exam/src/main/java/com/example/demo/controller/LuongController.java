package com.example.demo.controller;

import com.example.demo.entity.Luong;
import com.example.demo.exceptions.NotFondException;
import com.example.demo.request.CreateLuongRequest;
import com.example.demo.request.LuongRequest;
import com.example.demo.request.NhanVienDetailRequest;
import com.example.demo.response.MessageResponse;
import com.example.demo.sevice.LuongService;
import com.example.demo.sevice.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/luong")
public class LuongController {
    @Autowired
    private LuongService luongService;

    @Autowired
    NhanVienService nhanVienService;


    @GetMapping("/get-all")
    public Page<Luong> getAllLuong(
            @RequestParam(required = false) String theloai,
            @RequestParam(required = false) Long mucluong,
            @RequestParam(required = false) Boolean trangthai,
            @RequestParam(name = "pageNumber") int page,
            @RequestParam(name = "pageSize") int size) {
        LuongRequest luongRequest = new LuongRequest(theloai, mucluong, trangthai, page, size);
        return luongService.getAllLuong(luongRequest);
    }

    @PostMapping("/create")
    public ResponseEntity<Luong> createLuong(@Valid @RequestBody CreateLuongRequest luongRequest) {
        Luong luong = this.luongService.create(luongRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(luong);
    }

    @PostMapping("/create-luong-nv/{id}")
    public ResponseEntity<MessageResponse> createidnv(
            @PathVariable(name = "id") UUID id,
            @RequestBody NhanVienDetailRequest nhanVienDetailRequest
    ) {
        return new ResponseEntity<>(nhanVienService.createNhanVienDetail(id, nhanVienDetailRequest), HttpStatus.CREATED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") UUID id) {
        luongService.delete(id);
        return ResponseEntity.ok().body(MessageResponse.builder().message("Xóa thành công").build());
    }

    @GetMapping("/details")
    public ResponseEntity<Luong> detailNhanVien(@RequestParam(name = "id") UUID id) {
        try {
            Luong response = luongService.details(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFondException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> update(
            @RequestParam("id") UUID id,
            @RequestBody CreateLuongRequest createLuongRequest) {
        MessageResponse response = luongService.update(id, createLuongRequest);
        return ResponseEntity.ok().body(response);
    }

}
