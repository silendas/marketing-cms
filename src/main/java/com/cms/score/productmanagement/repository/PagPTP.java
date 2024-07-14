package com.cms.score.productmanagement.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cms.score.productmanagement.model.ProductTargetPlanning;

@Repository
public interface PagPTP extends PagingAndSortingRepository<ProductTargetPlanning, Long>, JpaSpecificationExecutor<ProductTargetPlanning> {
    
}
