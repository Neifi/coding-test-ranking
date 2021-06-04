package com.idealista.infrastructure.mapper;

public interface Mapper<D,S> {

    D mapToDestination(S source);
    S mapToSource(D source);

}
