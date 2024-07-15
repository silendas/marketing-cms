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
import com.cms.score.promotormanagement.dto.BranchDto;
import com.cms.score.promotormanagement.model.Branch;
import com.cms.score.promotormanagement.repository.BranchRepository;
import com.cms.score.promotormanagement.repository.PagBranch;

@Service
public class BranchService {

    @Autowired
    private BranchRepository repo;

    @Autowired
    private PagBranch pagRepo;

    public ResponseEntity<Object> getBranch(Long branchId, int page, int size) {
        Specification<Branch> spec = Specification
                .where(new Filter<Branch>().isNotDeleted())
                .and(new Filter<Branch>().orderByIdDesc());
        if(branchId != null) {
            spec = spec.and(new Filter<Branch>().byId(branchId));
        }
        Page<Branch> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getBranchById(Long id) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, setBranchById(id), null), 1);
    }

    public ResponseEntity<Object> addBranch(BranchDto branch) {
        Branch newBranch = new Branch();
        newBranch.setName(branch.getName());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(newBranch), null), 0);
    }

    public ResponseEntity<Object> updateBranch(Long id, BranchDto branch) {
        Branch newBranch = setBranchById(id);
        newBranch.setName(branch.getName());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(newBranch), null), 0);
    }

    public ResponseEntity<Object> deleteBranch(Long id) {
        Branch newBranch = setBranchById(id);
        newBranch.setIsDeleted(1);
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(newBranch), null), 0);
    }

    public Branch setBranchById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }

}
