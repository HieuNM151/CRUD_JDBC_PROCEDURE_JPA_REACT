package com.example.demo.sevice.impl;

import com.example.demo.entity.DuAn;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.NhanVienDuAn;
import com.example.demo.exceptions.NotFondException;
import com.example.demo.repo.DuAnRepo;
import com.example.demo.repo.NhanVienRepo;
import com.example.demo.request.DuAnCreateRequest;
import com.example.demo.request.DuAnRequestSpecification;
import com.example.demo.response.DuAnResponse;
import com.example.demo.response.MessageResponse;
import com.example.demo.response.NhanVienDuAnResponse;
import com.example.demo.sevice.DuAnService;
import com.example.demo.utils.MappingHelper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DuAnImpl implements DuAnService {

    @Autowired
    DuAnRepo duAnRepo;

    @Autowired
    NhanVienRepo nhanVienRepo;

    @Autowired
    private MappingHelper mappingHelper;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    public Page<DuAn> getAll(DuAnRequestSpecification duAnRequestSpecification) {
        Specification<DuAn> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (duAnRequestSpecification.getTenduan() != null){
                predicates.add(criteriaBuilder.like(root.get("tenduan"), "%" + duAnRequestSpecification.getTenduan() + "%"));
            }
            if (duAnRequestSpecification.getNgaybatdau() != null && duAnRequestSpecification.getNgayketthuc() != null){
                // Tìm kiếm dự án có ngày bắt đầu nằm trong khoảng từ ngày bắt đầu đến ngày kết thúc
                predicates.add(criteriaBuilder.between(root.get("ngaybatdau"), duAnRequestSpecification.getNgaybatdau(), duAnRequestSpecification.getNgayketthuc()));
            } else if (duAnRequestSpecification.getNgaybatdau() != null) {
                // Nếu chỉ có ngày bắt đầu được cung cấp, tìm kiếm dự án có ngày bắt đầu lớn hơn hoặc bằng ngày bắt đầu được cung cấp
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ngaybatdau"), duAnRequestSpecification.getNgaybatdau()));
            } else if (duAnRequestSpecification.getNgayketthuc() != null) {
                // Nếu chỉ có ngày kết thúc được cung cấp, tìm kiếm dự án có ngày kết thúc nhỏ hơn hoặc bằng ngày kết thúc được cung cấp
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("ngayketthuc"), duAnRequestSpecification.getNgayketthuc()));
            }
            if (duAnRequestSpecification.getTrangthai() != null) {
                predicates.add(criteriaBuilder.equal(root.get("trangthai"), duAnRequestSpecification.getTrangthai()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(duAnRequestSpecification.getPageable(), duAnRequestSpecification.getPageSize());

        return duAnRepo.findAll(specification, pageable);
    }


    @Override
    public DuAn createDuAn(DuAnCreateRequest duAnCreateRequest) {
        DuAn duAn = mappingHelper.map(duAnCreateRequest, DuAn.class);
        return duAnRepo.save(duAn);
    }

    @Override
    public DuAnResponse delatis(UUID id) {
        DuAn duAn = duAnRepo.findById(id)
                .orElseThrow(() -> new NotFondException("Không tìm thấy thông tin dự án với id: " + id));

        DuAnResponse duAnResponse = mappingHelper.map(duAn, DuAnResponse.class);

        List<NhanVienDuAnResponse> listNVDA = duAn.getNhanVienDuAnList().stream()
                .map(nhanVienDuAn -> {
                    NhanVienDuAnResponse nhanVienDuAnResponse = new NhanVienDuAnResponse();
                    nhanVienDuAnResponse.setId(nhanVienDuAn.getId());
                    nhanVienDuAnResponse.setNgaythamgia(nhanVienDuAn.getNgaythamgia());
                    nhanVienDuAnResponse.setNgayketthuc(nhanVienDuAn.getNgayketthuc());
                    nhanVienDuAnResponse.setTrangthai(nhanVienDuAn.getTrangthai());
                    // Lấy thông tin nhân viên từ mối quan hệ
                    NhanVien nhanVien = nhanVienDuAn.getNhanVien();
                    nhanVienDuAnResponse.setName(nhanVien.getName());
                    nhanVienDuAnResponse.setNamsinh(nhanVien.getNamsinh());
                    return nhanVienDuAnResponse;
                })
                .collect(Collectors.toList());

        duAnResponse.setListNVDA(listNVDA);

        return duAnResponse;
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<DuAn> optional = duAnRepo.findById(id);
        if (optional.isPresent()){
            DuAn duAn = optional.get();
            duAn.setNgayketthuc(timestamp);
            duAn.setTrangthai(false);
            duAnRepo.save(duAn);
            return MessageResponse.builder().message("Xóa thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy với id: " + id).build();
        }
    }

    @Override
    public MessageResponse update(UUID id, DuAnCreateRequest duAnCreateRequest) {
        Optional<DuAn> optional = duAnRepo.findById(id);
        if (optional.isPresent()) {
            DuAn duAn = optional.get();
            duAn.setTenduan(duAnCreateRequest.getTenduan());
            duAn.setNgaybatdau(duAnCreateRequest.getNgaybatdau());
            duAn.setNgayketthuc(duAnCreateRequest.getNgayketthuc());
            duAn.setTrangthai(duAnCreateRequest.getTrangthai());

            duAnRepo.save(duAn);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy bản ghi với id: " + id).build();
        }
    }
}
