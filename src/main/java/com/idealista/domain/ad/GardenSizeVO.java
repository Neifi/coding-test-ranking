package com.idealista.domain.ad;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class GardenSizeVO {

    private int size = 0;

    public GardenSizeVO(Integer size) {
        if (size==null) return;
        if(size < 0){
            throw new RuntimeException("Garden size cannot be less than zero");
        }
        this.size = size;
    }

    public Integer value() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
}
