package com.idealista.domain.pictureEntity;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.util.StringUtils;

import static org.apache.logging.log4j.util.Strings.EMPTY;

public class UrlVO {

    String[] schemes = {"http","https"};
    UrlValidator urlValidator = new UrlValidator(schemes);
    private String url = "";

    public UrlVO(String url) {
        if(!this.urlValidator.isValid(url)) throw new RuntimeException("Invalid URL");
        this.url = url;
    }

    public String value() {
        return url;
    }
}
