package com.qun.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ForegroundMainListDTO implements Serializable {
    private long id;
    private String url="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg";
    private String brand;
    private String cpu;
    private String gpu;
}
