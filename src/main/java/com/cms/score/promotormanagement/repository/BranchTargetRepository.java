package com.cms.score.promotormanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cms.score.promotormanagement.model.BranchTarget;

@Repository
public interface BranchTargetRepository extends JpaRepository<BranchTarget, Long>, JpaSpecificationExecutor<BranchTarget> {

}
