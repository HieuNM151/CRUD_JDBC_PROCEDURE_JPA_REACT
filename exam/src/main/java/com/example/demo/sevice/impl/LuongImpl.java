package com.example.demo.sevice.impl;

import com.example.demo.entity.Luong;
import com.example.demo.entity.NhanVien;
import com.example.demo.exceptions.NotFondException;
import com.example.demo.repo.LuongRepo;
import com.example.demo.repo.NhanVienRepo;
import com.example.demo.request.CreateLuongRequest;
import com.example.demo.request.LuongRequest;
import com.example.demo.response.MessageResponse;
import com.example.demo.sevice.LuongService;
import com.example.demo.utils.MappingHelper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LuongImpl implements LuongService {
    @Autowired
    private LuongRepo luongRepo;

    @Autowired
    private NhanVienRepo nhanVienRepo;

    @Autowired
    private MappingHelper mappingHelper;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    public Page<Luong> getAllLuong(LuongRequest luongRequest) {
        Specification<Luong> luongSpecification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (luongRequest.getTheloai() != null) {
                predicates.add(criteriaBuilder.like(root.get("theloai"), "%" + luongRequest.getTheloai() + "%"));
            }
            if (luongRequest.getTrangthai() != null) {
                predicates.add(criteriaBuilder.like(root.get("trangthai"), "%" + luongRequest.getTrangthai() + "%"));
            }
            if (luongRequest.getMucluong() != null) {
                predicates.add(criteriaBuilder.like(root.get("mucluong"), "%" + luongRequest.getMucluong() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable = PageRequest.of(luongRequest.getPageable(), luongRequest.getPageSize());

        return luongRepo.findAll(luongSpecification, pageable);
    }

    @Override
    public Luong create(CreateLuongRequest createLuongRequest) {
        Luong luong = mappingHelper.map(createLuongRequest, Luong.class);
        return luongRepo.save(luong);
    }

    @Override
    public Luong details(UUID id) {
        Luong luong = luongRepo.findById(id)
                .orElseThrow(() -> new NotFondException("Không tìm thấy bản ghi lương với id: " + id));

        return luong;
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<Luong> optionalLuong = luongRepo.findById(id);
        if (optionalLuong.isPresent()) {
            Luong luong = optionalLuong.get();
            luong.setTrangthai(false);
            luong.setNgayketthuc(timestamp);
            luongRepo.save(luong);
            return MessageResponse.builder().message("Xóa thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm với id: " + id).build();
        }
    }

    @Override
    public MessageResponse update(UUID id, CreateLuongRequest createLuongRequest) {
        Optional<Luong> optionalLuong = luongRepo.findById(id);
        if (optionalLuong.isPresent()) {
            Luong luong = optionalLuong.get();
            luong.setTheloai(createLuongRequest.getTheloai());
            luong.setMucluong(createLuongRequest.getMucluong());
            luong.setNgaybatdau(createLuongRequest.getNgaybatdau());
            luong.setNgayketthuc(createLuongRequest.getNgayketthuc());
            luong.setTrangthai(createLuongRequest.getTrangthai());
            luongRepo.save(luong);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy bản ghi với id: " + id).build();
        }
    }

}
