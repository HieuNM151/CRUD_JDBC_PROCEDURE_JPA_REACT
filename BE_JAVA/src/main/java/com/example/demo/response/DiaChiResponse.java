package com.example.demo.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
@Getter
@Setter
public class DiaChiResponse {
    private UUID id;
    private String huyen;
    private String xa;
    private String tinh;


}
