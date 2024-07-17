package com.cms.score.collectormanagement.controller;

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

import com.cms.score.collectormanagement.dto.KKKDto;
import com.cms.score.collectormanagement.service.KKKService;
import com.cms.score.common.path.BasePath;

@RestController
@RequestMapping(value = BasePath.BASE_COLLECTOR_KKK)
public class KKKController {

    @Autowired
    private KKKService service;

    @GetMapping
    public ResponseEntity<Object> getKKK(
            @RequestParam("collectorId") Optional<Long> collectorId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        return service.getKKKAll(collectorId.orElse(null), page.orElse(0), size.orElse(10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getKKKById(@PathVariable("id") Long id) {
        return service.getKKKById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addKKK(@RequestBody KKKDto dto) { 
        return service.createKKK(dto);
    }

}
