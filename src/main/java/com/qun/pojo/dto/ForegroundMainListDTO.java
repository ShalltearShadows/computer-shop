package com.qun.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ForegroundMainListDTO implements Serializable {
    private long id;
    private String url;
    private String brand;
    private String cpu;
    private String gpu;
}
