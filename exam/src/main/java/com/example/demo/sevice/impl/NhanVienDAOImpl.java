package com.example.demo.sevice.impl;

import com.example.demo.maper.NhanVienMaper;
import com.example.demo.request.NhanVienRequesJDBC;
import com.example.demo.request.SearchRequest;
import com.example.demo.response.QLNhanVienJDBC;
import com.example.demo.response.QLNhanVienNative;
import com.example.demo.response.SearchResponse;
import com.example.demo.sevice.NhanVienDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NhanVienDAOImpl implements NhanVienDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<QLNhanVienJDBC> search(String jsonArray) {
        List<QLNhanVienJDBC> result = new ArrayList<>();

        String sql = "{call search(?)}";

        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setNString(1, jsonArray);

            boolean hasResult = stmt.execute();

            while (true) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs != null) {
                        while (rs.next()) {
                            QLNhanVienJDBC qlNhanVienJDBC = new QLNhanVienJDBC();
                            qlNhanVienJDBC.setName(rs.getString("name"));
                            qlNhanVienJDBC.setNamsinh(rs.getDate("namsinh"));
                            qlNhanVienJDBC.setSdt(rs.getString("sdt"));
                            qlNhanVienJDBC.setGioitinh(rs.getBoolean("gioitinh"));
                            qlNhanVienJDBC.setTinh(rs.getString("tinh"));
                            qlNhanVienJDBC.setXa(rs.getString("xa"));
                            qlNhanVienJDBC.setHuyen(rs.getString("huyen"));
                            result.add(qlNhanVienJDBC);
                        }
                    }
                }

                if (!stmt.getMoreResults()) {
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return result;
    }



//    public List<QLNhanVienJDBC> listdbbysearch(SearchRequest searchRequest) {
//        List<QLNhanVienJDBC> dbinvs = new ArrayList<>();
//        StringBuilder sqlstmt = new StringBuilder("SELECT nv.id, nv.name, nv.namsinh, nv.sdt, nv.gioitinh, dc.tinh, dc.xa, dc.huyen ");
//        sqlstmt.append("FROM ").append(searchRequest.getDatabase()).append(" nv ");
//        sqlstmt.append("JOIN dia_chi dc ON nv.diachi = dc.id ");
//        sqlstmt.append("WHERE 1=1 ");
//
//        if (searchRequest.getName() != null && !searchRequest.getName().isEmpty()) {
//            sqlstmt.append("AND nv.name LIKE ? ");
//        }
//
//        if (searchRequest.getNgayBatDau() != null) {
//            sqlstmt.append("AND nv.namsinh >= ? ");
//        }
//
//        if (searchRequest.getNgayKetThuc() != null) {
//            sqlstmt.append("AND nv.namsinh <= ? ");
//        }
//
//        if (searchRequest.getSdt() != null && !searchRequest.getSdt().isEmpty()) {
//            sqlstmt.append("AND nv.sdt LIKE ? ");
//        }
//
//        if (searchRequest.getGioitinh() != null && !searchRequest.getGioitinh().isEmpty()) {
//            sqlstmt.append("AND nv.gioitinh = ? ");
//        }
//
//        // Phân trang
//        sqlstmt.append("ORDER BY nv.name OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
//
//        try {
//            PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(sqlstmt.toString());
//
//            int index = 1;
//
//            if (searchRequest.getName() != null && !searchRequest.getName().isEmpty()) {
//                preparedStatement.setString(index++, "%" + searchRequest.getName() + "%");
//            }
//
//            if (searchRequest.getNgayBatDau() != null) {
//                preparedStatement.setDate(index++, new java.sql.Date(searchRequest.getNgayBatDau().getTime()));
//            }
//            if (searchRequest.getNgayKetThuc() != null) {
//                preparedStatement.setDate(index++, new java.sql.Date(searchRequest.getNgayKetThuc().getTime()));
//            }
//
//            if (searchRequest.getSdt() != null && !searchRequest.getSdt().isEmpty()) {
//                preparedStatement.setString(index++, "%" + searchRequest.getSdt() + "%");
//            }
//
//            if (searchRequest.getGioitinh() != null && !searchRequest.getGioitinh().isEmpty()) {
//                preparedStatement.setString(index++, searchRequest.getGioitinh());
//            }
//
//            // Tính vị trí bắt đầu của bản ghi
//            int offset = (searchRequest.getPage()) * searchRequest.getPageSize();
//            preparedStatement.setInt(index++, offset);
//
//            // Số lượng bản ghi trên mỗi trang
//            preparedStatement.setInt(index++, searchRequest.getPageSize());
//
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                QLNhanVienJDBC nv = new QLNhanVienJDBC();
//                nv.setId(UUID.fromString(rs.getString("id")));
//                nv.setName(rs.getString("name"));
//                nv.setNamsinh(rs.getDate("namsinh"));
//                nv.setSdt(rs.getString("sdt"));
//                nv.setGioitinh(rs.getBoolean("gioitinh"));
//                nv.setTinh(rs.getString("tinh"));
//                nv.setXa(rs.getString("xa"));
//                nv.setHuyen(rs.getString("huyen"));
//                dbinvs.add(nv);
//            }
//
//            rs.close();
//            preparedStatement.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return dbinvs;
//    }

    @Override
    public SearchResponse listdbbysearch(SearchRequest searchRequest) {
        List<QLNhanVienNative> dbinvs = new ArrayList<>();
        StringBuilder sqlstmt = new StringBuilder("SELECT nv.id, nv.name, nv.namsinh, nv.sdt, nv.gioitinh, nv.trangthai, dc.tinh, dc.xa, dc.huyen ");
        sqlstmt.append("FROM ").append(searchRequest.getDatabase()).append(" nv ");
        sqlstmt.append("JOIN dia_chi dc ON nv.diachi = dc.id ");
        sqlstmt.append("WHERE 1=1 ");

        String nameParam = null;

        if (searchRequest.getName() != null && !searchRequest.getName().isEmpty()) {
            nameParam = "%" + searchRequest.getName() + "%";
            sqlstmt.append("AND (nv.name LIKE ? OR nv.sdt LIKE ?) ");
        }

        if (searchRequest.getNgayBatDau() != null) {
            sqlstmt.append("AND nv.namsinh >= ? ");
        }

        if (searchRequest.getNgayKetThuc() != null) {
            sqlstmt.append("AND nv.namsinh <= ? ");
        }

        if (searchRequest.getGioitinh() != null && !searchRequest.getGioitinh().isEmpty()) {
            sqlstmt.append("AND nv.gioitinh = ? ");
        }

        if (searchRequest.getTrangthai() != null && !searchRequest.getTrangthai().isEmpty()) {
            sqlstmt.append("AND nv.trangthai = ? ");
        }

        // Phân trang
        sqlstmt.append("ORDER BY nv.namsinh DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        int totalElements = 0;
        int totalPages = 0;

        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlstmt.toString())) {

                int index = 1;

                if (nameParam != null) {
                    preparedStatement.setString(index++, nameParam);
                    preparedStatement.setString(index++, nameParam);
                }

                if (searchRequest.getNgayBatDau() != null) {
                    preparedStatement.setDate(index++, new java.sql.Date(searchRequest.getNgayBatDau().getTime()));
                }
                if (searchRequest.getNgayKetThuc() != null) {
                    preparedStatement.setDate(index++, new java.sql.Date(searchRequest.getNgayKetThuc().getTime()));
                }

                if (searchRequest.getGioitinh() != null && !searchRequest.getGioitinh().isEmpty()) {
                    preparedStatement.setString(index++, searchRequest.getGioitinh());
                }

                if (searchRequest.getTrangthai() != null && !searchRequest.getTrangthai().isEmpty()) {
                    preparedStatement.setString(index++, searchRequest.getTrangthai());
                }

                // Tính vị trí bắt đầu của bản ghi
                int offset = (searchRequest.getPage()) * searchRequest.getPageSize();
                preparedStatement.setInt(index++, offset);

                // Số lượng bản ghi trên mỗi trang
                preparedStatement.setInt(index++, searchRequest.getPageSize());

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        QLNhanVienNative nv = new QLNhanVienNative();
                        nv.setId(UUID.fromString(rs.getString("id")));
                        nv.setName(rs.getString("name"));
                        nv.setNamsinh(rs.getDate("namsinh"));
                        nv.setSdt(rs.getString("sdt"));
                        nv.setGioitinh(rs.getBoolean("gioitinh"));
                        nv.setTrangthai(rs.getBoolean("trangthai"));
                        nv.setTinh(rs.getString("tinh"));
                        nv.setXa(rs.getString("xa"));
                        nv.setHuyen(rs.getString("huyen"));
                        dbinvs.add(nv);
                    }

                    // Lấy tổng số bản ghi
                    String countQuery = "SELECT COUNT(*) AS count FROM " + searchRequest.getDatabase();
                    try (Statement countStatement = conn.createStatement();
                         ResultSet countRs = countStatement.executeQuery(countQuery)) {
                        if (countRs.next()) {
                            totalElements = countRs.getInt("count");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tính tổng số trang
        totalPages = (int) Math.ceil((double) totalElements / searchRequest.getPageSize());

        return new SearchResponse(dbinvs, totalPages, totalElements);
    }


}


