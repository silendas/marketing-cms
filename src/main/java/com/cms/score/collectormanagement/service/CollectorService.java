package com.cms.score.collectormanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cms.score.collectormanagement.dto.CollectorsDto;
import com.cms.score.collectormanagement.model.CollectorsEntity;
import com.cms.score.collectormanagement.repository.CollectorRepository;
import com.cms.score.collectormanagement.repository.PagCollectors;
import com.cms.score.common.exception.ResourceNotFoundException;
import com.cms.score.common.response.Message;
import com.cms.score.common.response.Response;
import com.cms.score.common.response.dto.GlobalDto;
import com.cms.score.common.reuse.Filter;
import com.cms.score.common.reuse.PageConvert;
import com.cms.score.promotormanagement.model.Branch;
import com.cms.score.promotormanagement.repository.BranchRepository;

@Service
public class CollectorService {

    @Autowired
    private CollectorRepository repo;

    @Autowired
    private PagCollectors pagRepo;

    @Autowired
    private BranchRepository branchRepo;

    public ResponseEntity<Object> getCollectors(int page, int size) {
        Specification<CollectorsEntity> spec = Specification
                .where(new Filter<CollectorsEntity>().isNotDeleted())
                .and(new Filter<CollectorsEntity>().orderByIdDesc());
        Page<CollectorsEntity> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getCollectorById(Long id) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, getCollector(id), null), 1);
    }

    public ResponseEntity<Object> addCollector(CollectorsDto dto) {
        CollectorsEntity newCollector = new CollectorsEntity();
        newCollector.setBranch(getBranch(dto.getBranchId()));
        newCollector.setTotalCollectors(dto.getTotalCollectors());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(newCollector), null), 0);
    }

    public ResponseEntity<Object> updateCollector(Long id, CollectorsDto dto) {
        CollectorsEntity newCollector = getCollector(id);
        newCollector.setBranch(getBranch(dto.getBranchId()));
        newCollector.setTotalCollectors(dto.getTotalCollectors());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(newCollector), null), 0);
    }

    public ResponseEntity<Object> deleteCollector(Long id) {
        CollectorsEntity newCollector = getCollector(id);
        newCollector.setIsDeleted(1);
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(newCollector), null), 0);
    }

    public Branch getBranch(Long id) {
        return branchRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }

    public CollectorsEntity getCollector(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Collector not found"));
    }

}
