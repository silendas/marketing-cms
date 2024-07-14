package com.cms.score.productmanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.score.common.path.BasePath;
import com.cms.score.productmanagement.dto.ProductTypeDto;
import com.cms.score.productmanagement.service.ProductTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = BasePath.BASE_PRODUCT_TYPE)
public class ProductTypeController {

    @Autowired
    private ProductTypeService service;

    @GetMapping
    public ResponseEntity<Object> getProductTypes(
        @RequestParam("page") Optional<Integer> page,
        @RequestParam("size") Optional<Integer> size
    ) {
        return service.getProductTypes(page.orElse(0), size.orElse(10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductTypeById(@PathVariable("id") Long id) {
        return service.getProductTypeById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createProductType(@Valid @RequestBody ProductTypeDto dto) {
        return service.createProductType(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductType(@PathVariable("id") Long id, @Valid @RequestBody ProductTypeDto dto) {
        return service.updateProductType(id, dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchProductType(@PathVariable("id") Long id, @RequestBody ProductTypeDto dto) {
        return service.patchProductType(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductType(@PathVariable("id") Long id) {
        return service.deleteProductType(id);
    }
    
}
