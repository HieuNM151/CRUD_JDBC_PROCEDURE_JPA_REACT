package com.example.demo.sevice;

import com.example.demo.entity.NhanVien;
import com.example.demo.maper.NhanVienMaper;
import com.example.demo.request.CreateNhanVienRequest;
import com.example.demo.request.NhanVienRequest;
import com.example.demo.request.NhanVienRequestSpecification;
import com.example.demo.response.MessageResponse;
import com.example.demo.response.NhanVienResponse;
import com.example.demo.response.QLNhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface NhanVienService {


    Page<QLNhanVienResponse> pagingBook(NhanVienRequestSpecification nhanVienRequestSpecification);

//    List<NhanVienMaper> search(String name, String sdt, String huyen, String namsinh);


    QLNhanVienResponse create(CreateNhanVienRequest nhanVien);

    QLNhanVienResponse details(String id);

    MessageResponse delete(UUID id);

    MessageResponse updateNhanVien(UUID nhanVienid, CreateNhanVienRequest createNhanVienRequest);

    List<NhanVienMaper> getAll(NhanVienRequest nhanVienRequest);

    List<NhanVienResponse> find(Integer pageNumber, Integer pageSize);

//
//    MessageResponse createNhanVien(CreateNhanVienRequest createNhanVienRequest);
//
//    QLNhanVienResponse detailNhanVien(UUID id);
//
//
//    MessageResponse delete(UUID id);
}
