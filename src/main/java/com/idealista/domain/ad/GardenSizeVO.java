package com.idealista.domain.ad;

public class GardenSizeVO {
    private Long size;

    public GardenSizeVO(Long size) {
        if(size <= 0){
            throw new RuntimeException("House size cannot be less than zero");
        }
        this.size = size;
    }
}
