package com.idealista.domain.ad;

public class HouseSizeVO {

    private int size = 0;

    public HouseSizeVO(Integer size) {
        if(size == null) return;
        if (size < 0) {
            throw new RuntimeException("House size cannot be less than zero");
        }
        this.size = size;
    }

    public Integer value() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }
}
