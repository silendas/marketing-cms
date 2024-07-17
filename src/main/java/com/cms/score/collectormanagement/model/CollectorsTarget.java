package com.cms.score.collectormanagement.model;

import com.cms.score.common.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "collectors_target")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CollectorsTarget extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "collector_id")
    private CollectorsEntity collectors;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CollectorsTargetCategory collectorTargets;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private CollectorsPlanning planning;

    
}
