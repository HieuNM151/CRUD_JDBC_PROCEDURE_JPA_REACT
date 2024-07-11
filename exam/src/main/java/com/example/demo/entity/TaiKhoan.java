package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "tai_khoan")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaiKhoan implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "taikhoan")
    private String taikhoan;

    @Column(name = "matkhau")
    private String matkhau;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Transient // Đánh dấu trường này không ánh xạ với cột trong database
    private UUID nhanvienId;

    @OneToOne
    @JoinColumn(name = "idnhanvien")
    private NhanVien nhanVien;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Nếu bạn có các quyền cụ thể, hãy trả về chúng ở đây
        return null;
    }

    @Override
    public String getPassword() {
        return matkhau;
    }

    @Override
    public String getUsername() {
        return taikhoan;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Trả về true nếu tài khoản không bị hết hạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Trả về true nếu tài khoản không bị khóa
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Trả về true nếu thông tin xác thực không bị hết hạn
    }

    @Override
    public boolean isEnabled() {
        return true; // Trả về true nếu tài khoản được kích hoạt
    }
}
