package com.cms.score.promotormanagement.repositoy;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cms.score.promotormanagement.model.BranchTargetPLanning;

@Repository
public interface PagBranchTargetPlanning extends PagingAndSortingRepository<BranchTargetPLanning, Long>, JpaSpecificationExecutor<BranchTargetPLanning> {
    
}
