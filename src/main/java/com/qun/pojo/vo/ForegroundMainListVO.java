package com.qun.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ForegroundMainListVO implements Serializable {
    private long id;
    private String url;
    private String brand;
    private String cpu;
    private String gpu;
}
