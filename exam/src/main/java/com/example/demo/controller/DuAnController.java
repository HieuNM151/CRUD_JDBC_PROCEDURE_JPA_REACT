package com.example.demo.controller;

import com.example.demo.entity.DuAn;
import com.example.demo.exceptions.NotFondException;
import com.example.demo.request.DuAnCreateRequest;
import com.example.demo.request.DuAnRequestSpecification;
import com.example.demo.response.DuAnResponse;
import com.example.demo.response.MessageResponse;
import com.example.demo.sevice.DuAnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/du-an")
public class DuAnController {

    @Autowired
    private DuAnService duAnService;

    @GetMapping("/get-all")
    public Page<DuAn> getAllDuAn(
            @RequestParam(required = false) String tenduan,
            @RequestParam(required = false) Date ngaybatdau,
            @RequestParam(required = false) Date ngayketthuc,
            @RequestParam(required = false) Boolean trangthai,
            @RequestParam(name = "pageNumber") int page,
            @RequestParam(name = "pageSize") int size) {
        DuAnRequestSpecification duAnRequestSpecification = new DuAnRequestSpecification (tenduan, ngaybatdau, ngayketthuc, trangthai, page, size);
        return duAnService.getAll(duAnRequestSpecification);
    }

    @GetMapping("/details")
    public ResponseEntity<DuAnResponse> details(@RequestParam(name = "id")UUID id){
            DuAnResponse duAnResponse = duAnService.delatis(id);
            return new ResponseEntity<>(duAnResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete (@PathVariable("id") UUID id) {
        duAnService.delete(id);
        return ResponseEntity.ok().body(MessageResponse.builder().message("Xóa thành công").build());
    }

    @PostMapping("/create")
    public ResponseEntity<DuAn> create(@Valid @RequestBody DuAnCreateRequest duAnCreateRequest) {
        DuAn duAn = this.duAnService.createDuAn(duAnCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(duAn);
    }

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> update(
            @RequestParam("id") UUID id,
            @RequestBody DuAnCreateRequest duAnCreateRequest
    ) {
        MessageResponse response = duAnService.update(id, duAnCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
