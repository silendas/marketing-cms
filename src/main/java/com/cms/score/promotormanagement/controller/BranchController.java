package com.cms.score.promotormanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.score.common.path.BasePath;
import com.cms.score.promotormanagement.dto.BranchDto;
import com.cms.score.promotormanagement.service.BranchService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = BasePath.BASE_BRANCH)
public class BranchController {

    @Autowired
    private BranchService service;

    @GetMapping
    public ResponseEntity<Object> getBranches(
            @RequestParam("branchId") Optional<Long> branchId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        return service.getBranch(branchId.orElse(null), page.orElse(0), size.orElse(10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBranchById(@PathVariable("id") Long id) {
        return service.getBranchById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addBranch(@Valid @RequestBody BranchDto dto) {
        return service.addBranch(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBranch(@PathVariable("id") Long id, @Valid @RequestBody BranchDto dto) {
        return service.updateBranch(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBranch(@PathVariable("id") Long id) {
        return service.deleteBranch(id);
    }

}
