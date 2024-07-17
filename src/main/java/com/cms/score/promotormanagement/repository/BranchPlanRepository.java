package com.cms.score.promotormanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cms.score.promotormanagement.model.BranchPlan;

@Repository
public interface BranchPlanRepository
        extends JpaRepository<BranchPlan, Long>, JpaSpecificationExecutor<BranchPlan> {
    @Query("SELECT p FROM BranchPlan p WHERE EXTRACT(MONTH FROM p.date) = EXTRACT(MONTH FROM CAST(:date AS timestamp)) AND EXTRACT(YEAR FROM p.date) = EXTRACT(YEAR FROM CAST(:date AS timestamp))")
    List<BranchPlan> findByMonth(@Param("date") Date date);
}
