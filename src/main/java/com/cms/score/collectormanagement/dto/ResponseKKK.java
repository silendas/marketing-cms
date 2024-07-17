package com.cms.score.collectormanagement.dto;

import com.cms.score.collectormanagement.model.CollectorsEntity;
import com.cms.score.collectormanagement.model.CollectorsPlanning;
import com.cms.score.collectormanagement.model.CollectorsTargetCategory;

import lombok.Data;

@Data
public class ResponseKKK {

    private CollectorsEntity collectors;

    private CollectorsTargetCategory collectorsTargets;

    private CollectorsPlanning planning;

}
