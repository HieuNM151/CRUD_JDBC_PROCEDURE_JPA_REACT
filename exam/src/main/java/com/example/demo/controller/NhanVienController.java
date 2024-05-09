package com.example.demo.controller;


import com.example.demo.entity.DiaChi;
import com.example.demo.entity.NhanVien;
import com.example.demo.maper.NhanVienMaper;
import com.example.demo.request.*;
import com.example.demo.response.*;
import com.example.demo.sevice.DiaChiService;
import com.example.demo.sevice.NhanVienDAO;
import com.example.demo.sevice.NhanVienService;
import com.example.demo.sevice.impl.NhanVienImpl;
import com.example.demo.utils.ExcelGenerator;
import com.example.demo.utils.ExcelUtility;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/nhan-vien")
public class NhanVienController {
    @Autowired
    NhanVienService nhanVienService;

    @Autowired
    DiaChiService diaChiService;

    private NhanVienDAO nhanVienDAO;

    @Autowired
    public NhanVienController(NhanVienDAO nhanVienDAO) {
        this.nhanVienDAO = nhanVienDAO;
    }

    @PostMapping("/search-native")
    public SearchResponse searchNhanVien(@RequestBody SearchRequest request) {
        return nhanVienDAO.listdbbysearch(request);
    }

    @PostMapping("/search-Specification")
    public ResponseEntity<Page<QLNhanVienResponse>> getBook(@RequestBody NhanVienRequestSpecification request) {
        return new ResponseEntity<>(nhanVienService.pagingBook(request), HttpStatus.OK);
    }



    @PostMapping("/search-procedure")
    public ResponseEntity<List<QLNhanVienJDBC>> search(@RequestBody String jsonArray) {
        List<QLNhanVienJDBC> result = nhanVienDAO.search(jsonArray);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/get-all")
    public ResponseEntity<List<NhanVienResponse>> findAll(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(nhanVienService.find(pageNumber, pageSize), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<QLNhanVienResponse> createBook(@Valid @RequestBody CreateNhanVienRequest nhanVien) {
        QLNhanVienResponse createdNhanVien = this.nhanVienService.create(nhanVien, true);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNhanVien);
    }

    @GetMapping("/details")
    public ResponseEntity<QLNhanVienResponse> detailNhanVien(@RequestParam(name = "id") String id) {
        return new ResponseEntity<>(nhanVienService.details(id), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") UUID id) {
        nhanVienService.delete(id);
        return ResponseEntity.ok().body(MessageResponse.builder().message("Xóa thành công").build());
    }

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> updateKhachHang(
            @RequestParam("nhanVienId") UUID nhanVienId,
            @RequestBody CreateNhanVienRequest createNhanVienRequest) {
        MessageResponse response = nhanVienService.updateNhanVien(nhanVienId, createNhanVienRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-dia-chi")
    public List<DiaChiResponse> getAllDiaChi() {
        return diaChiService.getAllDiaChi();
    }

    @GetMapping("/export-to-excel")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=NhanVien" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<NhanVien> nhanVienList = nhanVienService.findAll();
        ExcelGenerator generator = new ExcelGenerator(nhanVienList);
        generator.generateExcelFile(response);
    }

    @PostMapping("/excel/upload")
    public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file) {
        String message = "";
        try {
            nhanVienService.save(file, true);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not upload the file: " + e.getMessage()+ "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
}
