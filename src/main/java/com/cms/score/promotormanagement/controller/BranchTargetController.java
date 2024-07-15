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
import com.cms.score.promotormanagement.dto.BranchTargetDto;
import com.cms.score.promotormanagement.service.BranchTargetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = BasePath.BASE_BRANCH_TARGET)
public class BranchTargetController {

    @Autowired
    private BranchTargetService service;

    @GetMapping
    public ResponseEntity<Object> getBranchTargets(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        return service.getBranchTarget(page.orElse(0), size.orElse(10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBranchTargetById(@PathVariable("id") Long id) {
        return service.getBranchTargetById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addBranchTarget(@Valid @RequestBody BranchTargetDto dto) {
        return service.addBranchTarget(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBranchTarget(@PathVariable("id") Long id, @Valid @RequestBody BranchTargetDto dto) {
        return service.updateBranchTarget(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBranchTarget(@PathVariable("id") Long id) {
        return service.deleteBranchTarget(id);
    }

}
