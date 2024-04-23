package com.example.demo.sevice.impl;


import com.example.demo.entity.DiaChi;
import com.example.demo.entity.Luong;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.exceptions.NotFondException;
import com.example.demo.maper.NhanVienMaper;
import com.example.demo.repo.DiaChiRepo;
import com.example.demo.repo.LuongRepo;
import com.example.demo.repo.NhanVienRepo;
import com.example.demo.repo.TaiKhoanRepo;
import com.example.demo.request.CreateNhanVienRequest;
import com.example.demo.request.NhanVienDetailRequest;
import com.example.demo.request.NhanVienRequest;
import com.example.demo.request.NhanVienRequestSpecification;
import com.example.demo.response.LuongRepose;
import com.example.demo.response.MessageResponse;
import com.example.demo.response.NhanVienResponse;
import com.example.demo.response.QLNhanVienResponse;
import com.example.demo.sevice.NhanVienService;
import com.example.demo.utils.MappingHelper;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;

@Service
@RequiredArgsConstructor
public class NhanVienImpl implements NhanVienService {


    private final NhanVienRepo nhanVienRepository;

    private final MappingHelper mappingHelper;

    private final DiaChiRepo diaChiRepo;

    private final TaiKhoanRepo taiKhoanRepo;

    private final LuongRepo luongRepo;

    @Override
    public Page<QLNhanVienResponse> pagingBook(NhanVienRequestSpecification requestSpecification) {
        Specification<NhanVien> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (requestSpecification.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + requestSpecification.getName() + "%"));
            }
            if (requestSpecification.getNamsinh() != null) {
                predicates.add(criteriaBuilder.equal(root.get("namsinh"), requestSpecification.getNamsinh()));
            }
            if (requestSpecification.getSdt() != null) {
                predicates.add(criteriaBuilder.like(root.get("sdt"), "%" + requestSpecification.getSdt() + "%"));
            }
            if (requestSpecification.getGioitinh() != null) {
                predicates.add(criteriaBuilder.like(root.get("gioitinh"), "%" + requestSpecification.getGioitinh() + "%"));
            }
            if (requestSpecification.getNamsinhTu() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("namsinh"), requestSpecification.getNamsinhTu()));
            }
            if (requestSpecification.getNamsinhDen() != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("namsinh"), requestSpecification.getNamsinhDen()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(requestSpecification.getPageable(), requestSpecification.getPageSize());

        Page<NhanVien> page = nhanVienRepository.findAll(spec, pageable).map(nhanVien -> {
//            if (nhanVien.getDiachi() != null)
//                nhanVien.setDiachi(new DiaChi(nhanVien.getDiachi().getId(), nhanVien.getDiachi().getTinh(),
//                        nhanVien.getDiachi().getXa(), nhanVien.getDiachi().getHuyen()));
            return nhanVien;
        });
        return page.map(nhanVien -> {
            return mappingHelper.map(nhanVien, QLNhanVienResponse.class);
        });
    }

    @Override
    public List<NhanVienMaper> getAll(NhanVienRequest nhanVienRequest) {
        String name = nhanVienRequest.getName();
        Date namsinh = nhanVienRequest.getNamsinh();
        String sdt = nhanVienRequest.getSdt();
        Boolean gioitinh = nhanVienRequest.getGioitinh();
        Date ngayBatDau = nhanVienRequest.getNgayBatDau();
        Date ngayKetThuc = nhanVienRequest.getNgayKetThuc();
        int pageNumber = nhanVienRequest.getPageNumber();
        int pageSize = nhanVienRequest.getPageSize();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<NhanVienMaper> pageList = nhanVienRepository.getAll(name, namsinh, sdt, gioitinh, ngayBatDau, ngayKetThuc, pageable);
        return pageList;
    }

    @Override
    public List<NhanVienResponse> find(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<NhanVienResponse> pageList= nhanVienRepository.fillAll(pageable);
        return pageList.getContent();
    }

    @Override
    public MessageResponse createNhanVienDetail(UUID id, NhanVienDetailRequest nhanVienDetailRequest) {
        Optional<NhanVien> optionalNhanVien = nhanVienRepository.findById(id);

        if (optionalNhanVien.isPresent()) {
            NhanVien nhanVien = optionalNhanVien.get();

            Luong luong = new Luong();
            luong.setTheloai(nhanVienDetailRequest.getTheloai());
            luong.setMucluong(nhanVienDetailRequest.getMucluong());
            luong.setNgaybatdau(nhanVienDetailRequest.getNgaybatdau());
            luong.setNgayketthuc(nhanVienDetailRequest.getNgayketthuc());
            luong.setTrangthai(nhanVienDetailRequest.getTrangthai());
            luong.setNhanVien(nhanVien);
            luong.setIdnhanvien(nhanVien.getId());
            luongRepo.save(luong);

            return MessageResponse.builder().message("Tạo chi tiết nhân viên thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy nhân viên với id: " + id).build();
        }
    }




//    @Override
//    public List<NhanVienMaper> search(String name, String sdt, String huyen, String namsinh) {
//        return nhanVienRepository.search(name, sdt, huyen, namsinh);
//    }

    @Override
    public QLNhanVienResponse create(CreateNhanVienRequest nhanVienRequest) {
        NhanVien nv = mappingHelper.map(nhanVienRequest, NhanVien.class);

        // Tạo tài khoản mới
        TaiKhoan taiKhoan = new TaiKhoan();
        // Chuyển đổi tên nhân viên thành chuỗi viết thường và không có dấu
        String taikhoan = removeDiacriticsAndSpaces(nhanVienRequest.getName().toLowerCase());
        taiKhoan.setTaikhoan(taikhoan);
        taiKhoan.setMatkhau("123456");

        Optional<DiaChi> diaChiOptional = diaChiRepo.findById(nhanVienRequest.getDiachi());
        if (diaChiOptional.isPresent()) {
            nv.setDiachi(diaChiOptional.get());
        } else {
            throw new NotFondException("Địa chỉ gửi lên không tồn tại!");
        }

        // Lưu thông tin nhân viên vào database và nhận ID của nhân viên mới tạo
        nv = nhanVienRepository.save(nv);

        // Gán ID của nhân viên cho trường nhanvienId của tài khoản
        taiKhoan.setNhanVien(nv);

        taiKhoanRepo.save(taiKhoan);

        // Tạo một đối tượng QLNhanVienResponse từ đối tượng NhanVien đã lưu
        QLNhanVienResponse nvResponse = mappingHelper.map(nv, QLNhanVienResponse.class);

        // Trả về đối tượng QLNhanVienResponse
        return nvResponse;
    }


    private String removeDiacriticsAndSpaces(String input) {
        String nfdNormalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString)
                .replaceAll("")
                .replaceAll("\\s", "");
    }

    @Override
    public QLNhanVienResponse details(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID không được null");
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID không hợp lệ: " + id);
        }

        NhanVien nhanVien = this.nhanVienRepository.findById(uuid)
                .orElseThrow(() -> new NotFondException("Không tìm thấy nhân viên với id: " + id));

        // Load danh sách lương của nhân viên
        List<Luong> luongList = nhanVien.getLuongList();

        // Tính tổng lương của nhân viên từ các mục có trạng thái là true
        long tongLuong = luongList.stream()
                .filter(Luong::getTrangthai) // Lọc các mục có trạng thái là true
                .mapToLong(Luong::getMucluong)
                .sum();

        // Tạo danh sách lương response
        List<LuongRepose> luongResponses = new ArrayList<>();
        for (Luong luong : luongList) {
            LuongRepose luongResponse = mappingHelper.map(luong, LuongRepose.class);
            luongResponses.add(luongResponse);
        }

        // Tạo đối tượng QLNhanVienResponse và đặt thông tin vào đó
        QLNhanVienResponse response = mappingHelper.map(nhanVien, QLNhanVienResponse.class);
        response.setLuongList(luongResponses);
        response.setTongLuong(tongLuong); // Đặt tổng lương vào đối tượng response

        return response;
    }




    @Override
    public MessageResponse delete(UUID id) {
        Optional<NhanVien> optionalNhanVien = nhanVienRepository.findById(id);
        if (optionalNhanVien.isPresent()) {
            NhanVien nhanVien = optionalNhanVien.get();
            // Lấy tài khoản của nhân viên
            TaiKhoan taiKhoan = taiKhoanRepo.findByNhanVien(nhanVien);
            // Kiểm tra xem có tài khoản không
            if (taiKhoan != null) {
                // Xóa tài khoản của nhân viên
                taiKhoanRepo.delete(taiKhoan);
            }

            // Xóa nhân viên
            nhanVienRepository.delete(nhanVien);

            return MessageResponse.builder().message("Xóa thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy nhân viên với id: " + id).build();
        }
    }


    @Override
    public MessageResponse updateNhanVien(UUID nhanVienid, CreateNhanVienRequest createNhanVienRequest) {
        Optional<NhanVien> optionalNhanVien = nhanVienRepository.findById(nhanVienid);
        if (optionalNhanVien.isPresent()) {
            NhanVien nhanVien = optionalNhanVien.get();
            nhanVien.setName(createNhanVienRequest.getName());
            nhanVien.setSdt(createNhanVienRequest.getSdt());
            nhanVien.setGioitinh(createNhanVienRequest.getGioitinh());
            nhanVien.setNamsinh(createNhanVienRequest.getNamsinh());

            if (createNhanVienRequest.getDiachi() != null) {
                Optional<DiaChi> diaChiOptional = diaChiRepo.findById(createNhanVienRequest.getDiachi());
                if (diaChiOptional.isPresent()) {
                    DiaChi diaChi = diaChiOptional.get();
                    nhanVien.setDiachi(diaChi);
                } else {
                    throw new NotFondException("Không tìm thấy địa chỉ với ID đã cung cấp: ");
                }
            }

            nhanVienRepository.save(nhanVien);

            return MessageResponse.builder().message("Cập nhật nhân viên thành công").build();
        } else {
            throw new NotFondException("Không tìm thấy nhân viên với ID: ");
        }
    }

//    private final NhanVienRepo nhanVienRepo;
//
//    private final DiaChiRepo diaChiRepo;
//
//    private final MappingHelper mappingHelper;
//
//    @Override
//    public List<QLNhanVienResponse> getAll(String name, String sdt, Boolean gioitinh, Integer pageNumber, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<QLNhanVienResponse> pageList = nhanVienRepo.getALL(name, sdt, gioitinh, pageable);
//        return pageList.getContent();
//        return  null;
//    }
//
//    @Override
//    public MessageResponse createNhanVien(CreateNhanVienRequest createNhanVienRequest) {
//        if (createNhanVienRequest.getIdDiaChi() == null) throw new NotFondException("ID địa chỉ là bắt buộc");
//        DiaChi diaChi = null;
//        Optional<DiaChi> diaChiOptional = diaChiRepo.findById(createNhanVienRequest.getIdDiaChi());
//        if (diaChiOptional.isPresent()) diaChi = diaChiOptional.get();
//        else throw new NotFondException("Địa chỉ không tồn tại trên DB");
//        NhanVien nhanVien = new NhanVien();
//        nhanVien = mappingHelper.map(createNhanVienRequest, NhanVien.class);
//        nhanVien.setId(UUID.randomUUID());
//
//        nhanVien.setDiachi(diaChi);
//        nhanVienRepo.save(nhanVien);
//        return MessageResponse.builder().message("Thêm thành công").build();
//    }
//
//    @Override
//    public QLNhanVienResponse detailNhanVien(UUID id) {
//        return nhanVienRepo.detailNhanVien(id);
//        return null;
//    }

//
//
//    @Override
//    public MessageResponse delete(UUID id) {
//        if (nhanVienRepo.existsById(id)) {
//            nhanVienRepo.deleteById(id);
//            return MessageResponse.builder().message("Xóa thành công").build();
//        } else {
//            return MessageResponse.builder().message("Không tìm thấy nhân viên với id: " + id).build();
//        }
//    }


}
