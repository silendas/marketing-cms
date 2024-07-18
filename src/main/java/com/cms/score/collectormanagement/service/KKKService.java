package com.cms.score.collectormanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cms.score.collectormanagement.dto.CollectorPlanningDto;
import com.cms.score.collectormanagement.dto.CollectorCategoryDto;
import com.cms.score.collectormanagement.dto.KKKDto;
import com.cms.score.collectormanagement.dto.ResponseKKK;
import com.cms.score.collectormanagement.model.CollectorsEntity;
import com.cms.score.collectormanagement.model.CollectorsPlanning;
import com.cms.score.collectormanagement.model.CollectorsTarget;
import com.cms.score.collectormanagement.model.CollectorsTargetCategory;
import com.cms.score.collectormanagement.repository.CollectorPlanningRepository;
import com.cms.score.collectormanagement.repository.CollectorRepository;
import com.cms.score.collectormanagement.repository.CollectorTargetRepository;
import com.cms.score.collectormanagement.repository.KKKRepository;
import com.cms.score.collectormanagement.repository.PagKKK;
import com.cms.score.common.exception.ResourceNotFoundException;
import com.cms.score.common.response.Message;
import com.cms.score.common.response.Response;
import com.cms.score.common.response.dto.GlobalDto;
import com.cms.score.common.reuse.Filter;
import com.cms.score.common.reuse.PageConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KKKService {

    @Autowired
    private KKKRepository repo;

    @Autowired
    private PagKKK pagRepo;

    @Autowired
    private CollectorTargetRepository targetRepo;

    @Autowired
    private CollectorPlanningRepository planningRepo;

    @Autowired
    private CollectorRepository collectorRepo;

    public ResponseEntity<Object> getKKKAll(Long collectorId, int page, int size) {
        Specification<CollectorsTarget> spec = Specification
                .where(new Filter<CollectorsTarget>().isNotDeleted())
                .and(new Filter<CollectorsTarget>().orderByIdDesc());
        if (collectorId != null) {
            spec = spec.and(new Filter<CollectorsTarget>().byIdInside(collectorId, "collectors"));
        }
        Page<CollectorsTarget> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getKKKReport(int page, int size) {
        Specification<CollectorsTarget> spec = Specification
                .where(new Filter<CollectorsTarget>().isNotDeleted())
                .and(new Filter<CollectorsTarget>().orderByIdDesc());
                
        List<CollectorsTarget> allResults = repo.findAll(spec);
        Map<String, List<CollectorsTarget>> groupedByCollectorAndTarget = allResults.stream()
                .collect(Collectors.groupingBy(
                        target -> target.getCollectors().getId() + "-" + target.getCollectorTargets().getId()));
        List<List<CollectorsTarget>> groupedList = new ArrayList<>(groupedByCollectorAndTarget.values());
        int start = Math.min((int) PageRequest.of(page, size).getOffset(), groupedList.size());
        int end = Math.min((start + PageRequest.of(page, size).getPageSize()), groupedList.size());
        List<List<CollectorsTarget>> paginatedResults = groupedList.subList(start, end);
        List<List<ResponseKKK>> dtoResults = convertToDto(paginatedResults);
        Page<List<ResponseKKK>> res = new PageImpl<>(dtoResults, PageRequest.of(page, size),
                groupedList.size());

        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null, null,
                PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getKKKById(Long id) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, getKKK(id), null), 1);
    }

    public ResponseEntity<Object> createKKK(KKKDto kkk) {
        List<String> details = new ArrayList<>();
        CollectorsEntity collector = getCollector(kkk.getCollectorId());
        CollectorsTargetCategory target = createTarget(kkk.getCollectorCategory());
        if (target == null) {
            details.add("Target kolektor harus dilengkapi semua");
        } else {
            int i = 0;
            for (CollectorPlanningDto planningDto : kkk.getCollectorPlanning()) {
                CollectorsTargetCategory getTarget = getTarget(target.getId());
                CollectorsPlanning planning = createPlanning(planningDto);
                if (planning == null) {
                    details.add("Planning pada index : {" + i + "} harus dilengkapi semua");
                } else {
                    CollectorsTarget planningTarget = new CollectorsTarget();
                    planningTarget.setPlanning(planning);
                    planningTarget.setCollectorTargets(getTarget);
                    planningTarget.setCollectors(collector);
                    repo.save(planningTarget);
                }
                i = i + 1;
            }
        }
        if (details.size() > 0) {
            return Response.buildResponse(new GlobalDto(Message.FAILED_DEFAULT.getStatusCode(), null,
                    Message.FAILED_DEFAULT.getMessage(), null, null, details), 1);
        }
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 0);
    }

    public CollectorsTargetCategory createTarget(CollectorCategoryDto dto) {
        CollectorsTargetCategory target = new CollectorsTargetCategory();
        target.setMaster(dto.getMaster());
        target.setJunior(dto.getJunior());
        target.setSenior(dto.getSenior());
        target.setTraining(dto.getTraining());
        return targetRepo.save(target);
    }

    public CollectorsPlanning createPlanning(CollectorPlanningDto dto) {
        if (dto.getTarget() == null || dto.getDate() == null || !planningRepo.findByMonth(dto.getDate()).isEmpty()) {
            return null;
        }
        CollectorsPlanning planning = new CollectorsPlanning();
        planning.setTarget(dto.getTarget());
        planning.setDate(dto.getDate());
        return planningRepo.save(planning);
    }

    private CollectorsTargetCategory getTarget(Long id) {
        return targetRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Target not found"));
    }

    private CollectorsEntity getCollector(Long id) {
        return collectorRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Collector not found"));
    }

    private CollectorsTarget getKKK(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("KKK not found"));
    }

    private List<List<ResponseKKK>> convertToDto(List<List<CollectorsTarget>> paginatedResults) {
        return paginatedResults.stream()
            .map(group -> group.stream()
                .map(target -> {
                    ResponseKKK dto = new ResponseKKK();
                    dto.setCollectors(target.getCollectors());
                    dto.setCollectorsTargets(target.getCollectorTargets());
                    dto.setPlanning(target.getPlanning());
                    return dto;
                })
                .collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

}
