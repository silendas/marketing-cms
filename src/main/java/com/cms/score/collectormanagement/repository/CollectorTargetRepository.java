package com.cms.score.collectormanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cms.score.collectormanagement.model.CollectorsTargetCategory;

@Repository
public interface CollectorTargetRepository extends JpaRepository<CollectorsTargetCategory, Long>, JpaSpecificationExecutor<CollectorsTargetCategory> {
    
}
