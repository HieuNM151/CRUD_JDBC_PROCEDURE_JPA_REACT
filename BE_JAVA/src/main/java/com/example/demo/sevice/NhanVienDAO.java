package com.example.demo.sevice;

import com.example.demo.maper.NhanVienMaper;
import com.example.demo.request.NhanVienRequesJDBC;
import com.example.demo.request.SearchRequest;
import com.example.demo.response.QLNhanVienJDBC;
import com.example.demo.response.QLNhanVienNative;
import com.example.demo.response.SearchResponse;

import java.util.Date;
import java.util.List;

public interface NhanVienDAO {
    List<QLNhanVienJDBC> search(String jsonArray);

    public SearchResponse listdbbysearch(SearchRequest searchRequest);
}
