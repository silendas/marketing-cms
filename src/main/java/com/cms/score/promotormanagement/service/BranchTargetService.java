package com.cms.score.promotormanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cms.score.common.exception.ResourceNotFoundException;
import com.cms.score.common.response.Message;
import com.cms.score.common.response.Response;
import com.cms.score.common.response.dto.GlobalDto;
import com.cms.score.common.reuse.Filter;
import com.cms.score.common.reuse.PageConvert;
import com.cms.score.promotormanagement.dto.BranchTargetDto;
import com.cms.score.promotormanagement.model.BranchTarget;
import com.cms.score.promotormanagement.repository.BranchTargetRepository;
import com.cms.score.promotormanagement.repository.PagBranchTarget;


@Service
public class BranchTargetService {

    @Autowired
    private BranchTargetRepository repo;

    @Autowired
    private PagBranchTarget pagRepo;

    public ResponseEntity<Object> getBranchTarget(int page, int size) {
        Specification<BranchTarget> spec = Specification
                .where(new Filter<BranchTarget>().isNotDeleted())
                .and(new Filter<BranchTarget>().orderByIdDesc());
        Page<BranchTarget> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getBranchTargetById(Long id) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, getBranchTargetObj(id), null), 1);
    }

    public ResponseEntity<Object> addBranchTarget(BranchTargetDto branchTarget) {
        BranchTarget newBranchTarget = new BranchTarget();
        newBranchTarget.setTarget(branchTarget.getTarget());
        newBranchTarget.setDate(branchTarget.getDate());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(newBranchTarget), null), 0);
    }

    public ResponseEntity<Object> updateBranchTarget(Long id, BranchTargetDto branchTarget) {
        BranchTarget newBranchTarget = getBranchTargetObj(id);
        newBranchTarget.setTarget(branchTarget.getTarget());
        newBranchTarget.setDate(branchTarget.getDate());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(newBranchTarget), null), 0);
    }

    public ResponseEntity<Object> deleteBranchTarget(Long id) {
        BranchTarget newBranchTarget = getBranchTargetObj(id);
        newBranchTarget.setIsDeleted(1);
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(newBranchTarget), null), 0);
    }

    public BranchTarget getBranchTargetObj(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch target is not found"));
    }
    
}
