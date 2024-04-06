package com.example.demo.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MappingHelper {

    @Autowired
    private ModelMapper modelMapper;

    public <T, H> T map(H inputData, Class<T> clazz) {
        return modelMapper.map(inputData, clazz);
    }

    public<T, H> void copyProperties(T source, H destination) {
        modelMapper.map(source, destination);
    }

    // map list
    public <T, H> List<T> mapList(List<H> inputData, Class<T> clazz) {
        return inputData.stream().map(item -> map(item, clazz)).collect(java.util.stream.Collectors.toList());
    }

}