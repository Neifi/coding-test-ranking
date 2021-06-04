package com.idealista.application;

import com.idealista.domain.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetAdRanking {

    @Autowired
    private AdRepository adRepository;

}
