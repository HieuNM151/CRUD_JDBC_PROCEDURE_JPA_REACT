package com.example.demo.sevice;

import com.example.demo.entity.NhanVienDuAn;
import com.example.demo.request.NhanVienDuAnRequest;
import com.example.demo.response.MessageResponse;

import java.util.UUID;

public interface NhanVienDuAnService {
    NhanVienDuAn addNhanVienDuAn(NhanVienDuAnRequest nhanVienDuAnRequest);

    MessageResponse delete(UUID id);

    MessageResponse update(UUID id);
}
