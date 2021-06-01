package com.idealista.application;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.ad.IrrelevantSinceVO;

import java.util.Date;

public class SetRelevance {

    int irrelevanceThreshold = 40;

    public void set(AdVO ad) {
        if(ad.getScore().value() <= irrelevanceThreshold){
            ad.setIrrelevantSince(new IrrelevantSinceVO(new Date()));
        }
    }
}
