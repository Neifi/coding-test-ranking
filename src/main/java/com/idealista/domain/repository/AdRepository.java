package com.idealista.domain.repository;


import com.idealista.domain.ad.Ad;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository {
    List<Ad> findAll();

    void saveAll(List<Ad> ads);
}
