package com.idealista.domain.repository;

import com.idealista.domain.ad.AdVO;

import java.util.List;

public interface AdRepository {
    List<AdVO> findAll();
    AdVO findById(Integer id);
}
