package com.idealista.domain.ad;

import java.util.Date;

public class IrrelevantSinceVO {

    private final Date date;

    public IrrelevantSinceVO(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }
}
