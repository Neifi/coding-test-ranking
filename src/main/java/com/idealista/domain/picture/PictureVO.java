package com.idealista.domain.picture;

import lombok.Builder;
import lombok.Getter;


public class PictureVO {

    private Integer id;
    private UrlVO url;
    private QualityVO quality;


    public PictureVO(Integer id, UrlVO url, QualityVO quality) {
        this.id = id;
        this.url = url;
        this.quality = quality;
    }

    public Integer getId() {
        return id;
    }

    public UrlVO getUrl() {
        return url;
    }

    public QualityVO getQuality() {
        return quality;
    }
}
