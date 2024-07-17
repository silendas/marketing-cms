package com.cms.score.collectormanagement.controller;

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

import com.cms.score.collectormanagement.dto.CollectorsDto;
import com.cms.score.collectormanagement.service.CollectorService;
import com.cms.score.common.path.BasePath;

@RestController
@RequestMapping(value = BasePath.BASE_COLLECTORS)
public class CollectorsController {

    @Autowired
    private CollectorService service;

    @GetMapping
    public ResponseEntity<Object> getCollectors(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        return service.getCollectors(page.orElse(0), size.orElse(10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCollectorById(@PathVariable("id") Long id) {
        return service.getCollectorById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addCollector(@RequestBody CollectorsDto dto) {
        return service.addCollector(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCollector(@PathVariable("id") Long id, @RequestBody CollectorsDto dto) {
        return service.updateCollector(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCollector(@PathVariable("id") Long id) {
        return service.deleteCollector(id);
    }

}
