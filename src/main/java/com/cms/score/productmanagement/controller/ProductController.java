package com.cms.score.productmanagement.controller;

import java.util.Optional;

import javax.validation.Valid;

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
import com.cms.score.productmanagement.dto.ProductDto;
import com.cms.score.productmanagement.service.ProductService;

@RestController
@RequestMapping(value = BasePath.BASE_PRODUCT)
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Object> getProducts(
        @RequestParam("page") Optional<Integer> page, 
        @RequestParam("size") Optional<Integer> size) {
        return service.getProducts(page.orElse(0), size.orElse(10));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id) {
        return service.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductDto dto) {
        return service.createProduct(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductDto dto) {
        return service.updateProduct(id, dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchProduct(@PathVariable("id") Long id, @RequestBody ProductDto dto) {
        return service.patchProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        return service.deleteProduct(id);
    }
    
}
