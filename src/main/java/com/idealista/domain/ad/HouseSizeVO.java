package com.idealista.domain.ad;

public class HouseSizeVO {

    private Long size;

    public HouseSizeVO(Long size) {
        if(size <= 0){
            throw new RuntimeException("House size cannot be less than zero");
        }
        this.size = size;
    }

}
