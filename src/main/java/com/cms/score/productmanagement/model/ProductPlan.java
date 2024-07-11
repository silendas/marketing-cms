package com.cms.score.productmanagement.model;

import java.util.Date;

import com.cms.score.common.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "product_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductPlan extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "target")
    private Double target;

    @Column(name = "date")
    private Date date;
    
}
