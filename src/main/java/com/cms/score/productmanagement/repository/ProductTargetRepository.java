package com.cms.score.productmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cms.score.productmanagement.model.ProductTarget;

@Repository
public interface ProductTargetRepository extends JpaRepository<ProductTarget, Long>, JpaSpecificationExecutor<ProductTarget> {
    
}
