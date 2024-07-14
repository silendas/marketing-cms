package com.cms.score.productmanagement.controller;

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
import com.cms.score.productmanagement.dto.ProductPlanDto;
import com.cms.score.productmanagement.service.ProductPlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = BasePath.BASE_PRODUCT_PLAN)
public class ProductPlanController {

    @Autowired
    private ProductPlanService service;

    @GetMapping
    public ResponseEntity<Object> getProductPlan(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        return service.getProductPlan(page.orElse(0), size.orElse(10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductPlanById(@PathVariable("id") Long id) {
        return service.getProductPlanById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createProductPlan(@Valid @RequestBody ProductPlanDto dto) {
        return service.createProductPlan(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductPlan(@PathVariable("id") Long id, @Valid @RequestBody ProductPlanDto dto) {
        return service.updateProductPlan(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductPlan(@PathVariable("id") Long id) {
        return service.deleteProductPlan(id);
    }

}
