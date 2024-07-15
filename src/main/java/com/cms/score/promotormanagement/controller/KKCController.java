package com.cms.score.promotormanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.score.common.path.BasePath;
import com.cms.score.promotormanagement.dto.KKCDto;
import com.cms.score.promotormanagement.service.KKCService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = BasePath.BASE_BRANCH_KKC)
public class KKCController {

    @Autowired
    private KKCService service;

    @GetMapping
    public ResponseEntity<Object> getKKCs(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        return service.getKKC(page.orElse(0), size.orElse(10));
    }

    @GetMapping("/report")
    public ResponseEntity<Object> getKKCReport(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        return service.getKKCReport(page.orElse(0), size.orElse(10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getKKCById(@PathVariable("id") Long id) {
        return service.getKKCById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createKKC(@Valid @RequestBody KKCDto dto) {
        return service.addKKC(dto);
    }

}
