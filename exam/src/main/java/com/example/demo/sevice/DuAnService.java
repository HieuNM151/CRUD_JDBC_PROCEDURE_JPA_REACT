package com.example.demo.sevice;

import com.example.demo.entity.DuAn;
import com.example.demo.request.DuAnCreateRequest;
import com.example.demo.request.DuAnRequestSpecification;
import com.example.demo.response.DuAnResponse;
import com.example.demo.response.MessageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface DuAnService {
    Page<DuAn> getAll(DuAnRequestSpecification duAnRequestSpecification);

    DuAn createDuAn(DuAnCreateRequest duAnCreateRequest);

    DuAnResponse delatis(UUID id);

    MessageResponse delete(UUID id);

    MessageResponse update(UUID id, DuAnCreateRequest duAnCreateRequest);
}
