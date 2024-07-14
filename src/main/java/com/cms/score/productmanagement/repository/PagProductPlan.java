package com.cms.score.productmanagement.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cms.score.productmanagement.model.ProductPlan;

@Repository
public interface PagProductPlan extends PagingAndSortingRepository<ProductPlan, Long>, JpaSpecificationExecutor<ProductPlan> {
    
}
