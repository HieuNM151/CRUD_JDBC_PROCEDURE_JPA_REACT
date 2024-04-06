package com.example.demo.service.impl;

import com.example.demo.entity.NhanVien;
import com.example.demo.maper.NhanVienMaper;
import com.example.demo.service.NhanVienDAO;
import com.example.demo.utils.MappingHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NhanVienDAOImpl implements NhanVienDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<NhanVienMaper> search(String name, String sdt, String huyen, String namsinh) {
        List<NhanVienMaper> result = new ArrayList<>();
        String sql = "{call search(?, ?, ?, ?)}";
        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, sdt);
            stmt.setString(3, huyen);
            stmt.setString(4, namsinh);
            boolean hasResult = stmt.execute();
            if (hasResult) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        NhanVienMaper nhanVienMaper = new NhanVienMaper();
                        nhanVienMaper.setName(rs.getString("name"));
                        nhanVienMaper.setNamsinh(rs.getString("namsinh"));
                        nhanVienMaper.setSdt(rs.getString("sdt"));
                        nhanVienMaper.setGioitinh(rs.getBoolean("gioitinh"));
                        nhanVienMaper.setTinh(rs.getString("tinh"));
                        nhanVienMaper.setXa(rs.getString("xa"));
                        nhanVienMaper.setHuyen(rs.getString("huyen"));
                        result.add(nhanVienMaper);
                    }
                }
            }
        } catch (SQLException e) {
        }
        return result;
    }


}
