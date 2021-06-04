package com.idealista.domain.repository;

import com.idealista.domain.picture.PictureVO;

import java.util.List;

public interface PictureRepository {
    List<PictureVO> findAll();
    List<PictureVO> findAll(List<Integer> ids);
    PictureVO findById(Integer id);
}
