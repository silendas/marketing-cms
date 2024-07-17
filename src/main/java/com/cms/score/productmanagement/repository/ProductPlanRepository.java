package com.cms.score.productmanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cms.score.productmanagement.model.ProductPlan;

@Repository
public interface ProductPlanRepository extends JpaRepository<ProductPlan, Long>, JpaSpecificationExecutor<ProductPlan> {
    @Query("SELECT p FROM ProductPlan p WHERE EXTRACT(MONTH FROM p.date) = EXTRACT(MONTH FROM CAST(:date AS timestamp)) AND EXTRACT(YEAR FROM p.date) = EXTRACT(YEAR FROM CAST(:date AS timestamp))")
    List<ProductPlan> findByMonth(@Param("date") Date date);
}
