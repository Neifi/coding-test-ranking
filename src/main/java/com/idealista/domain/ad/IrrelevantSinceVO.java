package com.idealista.domain.ad;

import lombok.EqualsAndHashCode;

import java.util.Date;
@EqualsAndHashCode
public class IrrelevantSinceVO {

    private Date date = new Date(0);

    public IrrelevantSinceVO() {
    }

    public IrrelevantSinceVO(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }
}
