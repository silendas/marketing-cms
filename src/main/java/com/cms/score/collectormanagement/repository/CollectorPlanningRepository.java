package com.cms.score.collectormanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cms.score.collectormanagement.model.CollectorsPlanning;

@Repository
public interface CollectorPlanningRepository
        extends JpaRepository<CollectorsPlanning, Long>, JpaSpecificationExecutor<CollectorsPlanning> {
    @Query("SELECT p FROM CollectorsPlanning p WHERE EXTRACT(MONTH FROM p.date) = EXTRACT(MONTH FROM CAST(:date AS timestamp)) AND EXTRACT(YEAR FROM p.date) = EXTRACT(YEAR FROM CAST(:date AS timestamp))")
    List<CollectorsPlanning> findByMonth(@Param("date") Date date);
}
