package com.cms.score.collectormanagement.model;

import com.cms.score.common.model.BaseEntity;
import com.cms.score.promotormanagement.model.Branch;

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
@Table(name = "collectors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CollectorsEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(name = "total_collectors")
    private Long totalCollectors;
    
}
