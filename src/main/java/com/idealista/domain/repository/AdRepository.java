package com.idealista.domain.repository;


import com.idealista.domain.ad.AdVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository {
    List<AdVO> findAll();

    void saveAll(List<AdVO> ads);
}
