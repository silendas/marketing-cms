package com.cms.score.productmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cms.score.productmanagement.model.ProductTargetPlanning;

@Repository
public interface ProductWorkingPaperRepository extends JpaRepository<ProductTargetPlanning, Long>, JpaSpecificationExecutor<ProductTargetPlanning> {
    
}
