package com.cms.score.promotormanagement.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cms.score.promotormanagement.model.Branch;

@Repository
public interface PagBranch extends PagingAndSortingRepository<Branch, Long>, JpaSpecificationExecutor<Branch> {

}
