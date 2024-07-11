package com.cms.score.productmanagement.model;

import org.hibernate.annotations.ManyToAny;

import com.cms.score.common.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "product_target_planning")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductTargetPlanning extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_target_id")
    private ProductTarget productTarget;

    @ManyToOne
    @JoinColumn(name = "product_plan_id")
    private ProductPlan productPlan;
    
}
