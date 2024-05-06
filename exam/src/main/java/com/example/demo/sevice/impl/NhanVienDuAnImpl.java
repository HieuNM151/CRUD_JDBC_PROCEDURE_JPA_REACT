package com.example.demo.sevice.impl;

import com.example.demo.entity.DuAn;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.NhanVienDuAn;
import com.example.demo.exceptions.NotFondException;
import com.example.demo.repo.DuAnRepo;
import com.example.demo.repo.NhanVienDuAnRepo;
import com.example.demo.repo.NhanVienRepo;
import com.example.demo.request.NhanVienDuAnRequest;
import com.example.demo.response.MessageResponse;
import com.example.demo.sevice.NhanVienDuAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class NhanVienDuAnImpl implements NhanVienDuAnService {

    @Autowired
    private NhanVienRepo nhanVienRepository;

    @Autowired
    private DuAnRepo duAnRepository;

    @Autowired
    private NhanVienDuAnRepo nhanVienDuAnRepository;


    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    public NhanVienDuAn addNhanVienDuAn(NhanVienDuAnRequest nhanVienDuAnRequest) {
        Optional<NhanVien> optionalNhanVien = nhanVienRepository.findById(nhanVienDuAnRequest.getNhanVienId());
        Optional<DuAn> optionalDuAn = duAnRepository.findById(nhanVienDuAnRequest.getDuAnId());

        if (optionalNhanVien.isPresent() && optionalDuAn.isPresent()) {
            NhanVien nhanVien = optionalNhanVien.get();
            DuAn duAn = optionalDuAn.get();

            // Kiểm tra xem nhân viên đã tham gia dự án trước đó chưa
            Optional<NhanVienDuAn> existingRecord = nhanVienDuAnRepository.findByNhanVienAndDuAn(nhanVien, duAn);
            if (existingRecord.isPresent()) {
                throw new NotFondException("Nhân viên đã tham gia dự án trước đó.");
            }

            NhanVienDuAn nhanVienDuAn = new NhanVienDuAn();
            nhanVienDuAn.setNhanVien(nhanVien);
            nhanVienDuAn.setDuAn(duAn);
            nhanVienDuAn.setNgaythamgia(timestamp);
            nhanVienDuAn.setTrangthai(true);

            return nhanVienDuAnRepository.save(nhanVienDuAn);
        } else {
            throw new NotFondException("Không tìm thấy NhanVien hoặc DuAn");
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<NhanVienDuAn> optional = nhanVienDuAnRepository.findById(id);
        if (optional.isPresent()) {
            NhanVienDuAn nhanVienDuAn = optional.get();
            nhanVienDuAn.setNgayketthuc(timestamp);
            nhanVienDuAn.setTrangthai(false);
            nhanVienDuAnRepository.save(nhanVienDuAn);
            return MessageResponse.builder().message("Xóa thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy đối tượng với id: " + id).build();
        }
    }

    @Override
    public MessageResponse update(UUID id) {
        Optional<NhanVienDuAn> optional = nhanVienDuAnRepository.findById(id);
        if (optional.isPresent()) {
            NhanVienDuAn nhanVienDuAn = optional.get();
            nhanVienDuAn.setTrangthai(true);
            nhanVienDuAnRepository.save(nhanVienDuAn);
            return MessageResponse.builder().message("sửa thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy đối tượng với id: " + id).build();
        }
    }


}
