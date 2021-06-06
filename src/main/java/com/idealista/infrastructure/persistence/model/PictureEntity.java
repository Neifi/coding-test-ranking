package com.idealista.infrastructure.persistence.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureEntity {
    private int id;
    private String url;
    private String quality;

    public PictureEntity(int id, String url, String quality) {
        this.id = id;
        this.url = url;
        this.quality = quality;
    }
}
