package com.example.demo.sevice.impl;

import com.example.demo.entity.DiaChi;
import com.example.demo.repo.DiaChiRepo;
import com.example.demo.response.DiaChiResponse;
import com.example.demo.sevice.DiaChiService;
import com.example.demo.utils.MappingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DiaChiImpl implements DiaChiService {

    @Autowired
    private DiaChiRepo diaChiRepo;

    @Autowired
    MappingHelper mappingHelper;

    @Override
    public List<DiaChiResponse> getAllDiaChi() {
        List<DiaChi> diaChiList = diaChiRepo.findAll();
        return mappingHelper.mapList(diaChiList, DiaChiResponse.class);
    }

}
