package com.cms.score.productmanagement.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.score.common.path.BasePath;
import com.cms.score.productmanagement.dto.KKPDto;
import com.cms.score.productmanagement.service.KKPService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = BasePath.BASE_KKP)
public class KKPController {

    @Autowired
    private KKPService service;

    @GetMapping
    public ResponseEntity<Object> getKKP(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> start_date,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> end_date,
            @RequestParam("productId") Optional<Long> productId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        return service.getKKP(productId.orElse(null) ,start_date.orElse(null), end_date.orElse(null) ,page.orElse(0), size.orElse(10));
    }

    @GetMapping("/report")
    public ResponseEntity<Object> getKKPReport(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> start_date,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> end_date,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        return service.getKKPReport(start_date.orElse(null), end_date.orElse(null) ,page.orElse(0), size.orElse(10));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getKKPById(@PathVariable("id") Long id) {
        return service.getKKPById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createKKP(@Valid @RequestBody KKPDto dto) {
        return service.createKKP(dto);
    }

}
