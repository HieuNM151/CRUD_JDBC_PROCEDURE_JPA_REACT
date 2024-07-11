package com.example.demo.sevice.security;

import com.example.demo.dto.ReqRes;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.repo.TaiKhoanRepo;
import com.example.demo.sevice.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private TaiKhoanRepo taiKhoanRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signUp(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            // Kiểm tra xem email đã tồn tại hay chưa
            if (taiKhoanRepo.findByEmail(registrationRequest.getEmail()).isPresent()) {
                resp.setStatusCode(400);
                resp.setMessage("Email already exists");
                return resp;
            }

            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setEmail(registrationRequest.getEmail());
            taiKhoan.setMatkhau(passwordEncoder.encode(registrationRequest.getMatkhau()));
            taiKhoan.setRole(registrationRequest.getRole());

            TaiKhoan taiKhoanResult = taiKhoanRepo.save(taiKhoan);
            if (taiKhoanResult != null && taiKhoanResult.getId() != null) {
                resp.setTaiKhoans(taiKhoanResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(ReqRes signInRequest) {
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getMatkhau()));
            TaiKhoan user = taiKhoanRepo.findByEmail(signInRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Sign In");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        TaiKhoan user = taiKhoanRepo.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24hr");
            response.setMessage("Successfully Refresh Token");
        } else {
            response.setStatusCode(500);
            response.setMessage("Token is not valid");
        }
        return response;
    }
}
