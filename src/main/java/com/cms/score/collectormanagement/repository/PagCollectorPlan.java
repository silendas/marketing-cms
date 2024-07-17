package com.cms.score.collectormanagement.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cms.score.collectormanagement.model.CollectorsPlanning;

@Repository
public interface PagCollectorPlan extends PagingAndSortingRepository<CollectorsPlanning, Long>, JpaSpecificationExecutor<CollectorsPlanning> {
    
}
