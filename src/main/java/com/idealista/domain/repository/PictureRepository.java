package com.idealista.domain.repository;

import com.idealista.domain.picture.PictureVO;

import java.util.List;

public interface PictureRepository {
    List<PictureVO> findAll();
    PictureVO findById(Integer id);
}
