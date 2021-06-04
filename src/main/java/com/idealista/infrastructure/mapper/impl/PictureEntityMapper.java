package com.idealista.infrastructure.mapper.impl;

import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.picture.QualityVO;
import com.idealista.domain.picture.UrlVO;
import com.idealista.infrastructure.mapper.Mapper;
import com.idealista.infrastructure.persistence.model.Picture;

public class PictureEntityMapper implements Mapper<Picture, PictureVO> {
    @Override
    public Picture mapToDestination(PictureVO source) {
        return null;
    }

    @Override
    public PictureVO mapToSource(Picture source) {
        return new PictureVO(
                source.getId(),
                new UrlVO(source.getUrl()),
                QualityVO.valueOf(source.getQuality())
        );
    }
}
