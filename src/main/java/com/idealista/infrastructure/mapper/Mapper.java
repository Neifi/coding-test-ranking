package com.idealista.infrastructure.mapper;

import org.springframework.stereotype.Component;

public interface Mapper<D,S> {

    D mapToDestination(S source);
    S mapToSource(D source);

}
