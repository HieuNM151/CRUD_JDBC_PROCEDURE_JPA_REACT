package com.example.demo.sevice;

import com.example.demo.entity.Luong;
import com.example.demo.request.CreateLuongRequest;
import com.example.demo.request.LuongRequest;
import com.example.demo.response.MessageResponse;
import com.example.demo.response.QLNhanVienResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface LuongService {

    Page<Luong> getAllLuong(LuongRequest luongRequest);

    Luong create(CreateLuongRequest createLuongRequest);

    Luong details(UUID id);

    MessageResponse delete(UUID id);
}
