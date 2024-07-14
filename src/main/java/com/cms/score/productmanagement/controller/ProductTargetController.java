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
import com.cms.score.productmanagement.dto.ProductTargetDto;
import com.cms.score.productmanagement.service.ProductTargetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = BasePath.BASE_PRODUCT_TARGET)
public class ProductTargetController {

    @Autowired
    private ProductTargetService service;

    @GetMapping
    public ResponseEntity<Object> getProductTarget(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        return service.getProductTarget(page.orElse(0), size.orElse(10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductTargetById(@PathVariable("id") Long id) {
        return service.getProductTargetById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createProductTarget(@Valid @RequestBody ProductTargetDto dto) {
        return service.createProductTarget(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductTarget(@PathVariable("id") Long id, @Valid @RequestBody ProductTargetDto dto) {
        return service.updateProductTarget(id, dto);
    }

    // @PatchMapping("/{id}")
    // public ResponseEntity<Object> patchProductTarget(@PathVariable("id") Long id, @RequestBody ProductTargetDto dto) {
    //     return service.patchProductTarget(id, dto);
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductTarget(@PathVariable("id") Long id) {
        return service.deleteProductTarget(id);
    }

}
