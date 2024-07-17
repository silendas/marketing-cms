package com.cms.score.collectormanagement.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cms.score.collectormanagement.model.CollectorsTarget;

@Repository
public interface PagKKK extends PagingAndSortingRepository<CollectorsTarget, Long>, JpaSpecificationExecutor<CollectorsTarget> {
    
}
