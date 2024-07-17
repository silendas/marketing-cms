package com.cms.score.collectormanagement.model;

import java.util.Date;

import com.cms.score.common.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "collectors_planning")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CollectorsPlanning extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "target")
    private Double target;

    @Column(name = "date")
    private Date date;
    
}
