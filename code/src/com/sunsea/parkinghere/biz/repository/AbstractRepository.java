package com.sunsea.parkinghere.biz.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sunsea.parkinghere.biz.repository.custom.AbstractRepositoryCustom;

@Repository
public interface AbstractRepository<T> extends
                                       PagingAndSortingRepository<T, String>,
                                       AbstractRepositoryCustom {
    
}
