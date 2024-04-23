package com.example.demo.repo;

import com.example.demo.entity.Luong;
import com.example.demo.entity.NhanVien;
import com.example.demo.maper.NhanVienMaper;
import com.example.demo.response.NhanVienResponse;
import com.example.demo.response.QLNhanVienResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface NhanVienRepo extends JpaRepository<NhanVien, UUID>, JpaSpecificationExecutor<NhanVien> {

//    NhanVien findAllByNhanVien(Luong luong);

//    @Query(value = "EXEC search :name, :sdt, :huyen, :namsinh", nativeQuery = true)
//    List<NhanVienMaper> search(@Param("name") String name, @Param("sdt") String sdt, @Param("huyen") String huyen, @Param("namsinh") String namsinh);


    @Query(value = "select nv.id, nv.name, nv.namsinh, nv.sdt, nv.gioitinh, dc.id, dc.tinh, dc.huyen, dc.xa " +
            "from nhan_vien nv join dia_chi dc on nv.diachi = dc.id " +
            "where (:name is null or nv.name like %:name%) and " +
            "(:namsinh is null or nv.namsinh like %:namsinh%) and " +
            "(:sdt is null or nv.sdt like %:sdt%) and " +
            "(:gioitinh is null or nv.gioitinh = :gioitinh) and " +
            "(:ngayBatDau is null or :ngayKetThuc is null or nv.namsinh between :ngayBatDau and :ngayKetThuc)",
            nativeQuery = true)
    List<NhanVienMaper> getAll(@Param("name") String name,
                               @Param("namsinh") Date namsinh,
                               @Param("sdt") String sdt,
                               @Param("gioitinh") Boolean gioitinh,
                               @Param("ngayBatDau") Date ngayBatDau,
                               @Param("ngayKetThuc") Date ngayKetThuc,
                               Pageable pageable);


    @Query("select new com.example.demo.response.NhanVienResponse(nv.id, nv.name, nv.namsinh, nv.sdt, nv.gioitinh, dc.huyen, dc.xa, dc.tinh)\n" +
            "from NhanVien nv \n" +
            "JOIN nv.diachi dc")
    Page<NhanVienResponse> fillAll(Pageable pageable);

//    @Query("select new com.example.demo.response.QLNhanVienResponse(nv.id, nv.name, nv.namsinh, nv.sdt, nv.gioitinh, dc.huyen, dc.xa, dc.tinh)" +
//            "from NhanVien nv join nv.diachi dc where nv.id = :id")
//    QLNhanVienResponse detailNhanVien(@Param("id") UUID id);


}
