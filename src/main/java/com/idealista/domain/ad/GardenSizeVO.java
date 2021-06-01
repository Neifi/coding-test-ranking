package com.idealista.domain.ad;

public class GardenSizeVO {

    private Long size;

    public GardenSizeVO(Long size) {
        if(size < 0){
            throw new RuntimeException("Garden size cannot be less than zero");
        }
        this.size = size;
    }

    public Long value() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
}
