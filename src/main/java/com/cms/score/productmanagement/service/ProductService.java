package com.cms.score.productmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cms.score.common.exception.ResourceNotFoundException;
import com.cms.score.common.response.Message;
import com.cms.score.common.response.Response;
import com.cms.score.common.response.dto.GlobalDto;
import com.cms.score.common.reuse.Filter;
import com.cms.score.common.reuse.PageConvert;
import com.cms.score.productmanagement.dto.ProductDto;
import com.cms.score.productmanagement.model.Product;
import com.cms.score.productmanagement.model.ProductTypes;
import com.cms.score.productmanagement.repository.PagProduct;
import com.cms.score.productmanagement.repository.ProductRepository;
import com.cms.score.productmanagement.repository.ProductTypeRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private PagProduct pagRepo;

    @Autowired
    private ProductTypeRepository typeRepo;

    public ResponseEntity<Object> getProducts(int page, int size) {
        Specification<Product> spec = Specification
                .where(new Filter<Product>().isNotDeleted())
                .and(new Filter<Product>().orderByIdDesc());
        Page<Product> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getProductById(Long id) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.findById(id), null), 1);
    }

    public ResponseEntity<Object> createProduct(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setProductType(getProductType(dto.getProductTypeId()));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(product), null), 0);
    }

    public ResponseEntity<Object> updateProduct(Long id, ProductDto dto) {
        Product product = getProduct(id);
        product.setName(dto.getName());
        product.setProductType(getProductType(dto.getProductTypeId()));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(product), null), 0);
    }

    public ResponseEntity<Object> patchProduct(Long id, ProductDto dto) {
        Product product = getProduct(id);
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            product.setName(dto.getName());
        } else if(dto.getProductTypeId() != null && dto.getProductTypeId() != 0) {
            product.setProductType(getProductType(dto.getProductTypeId()));
        }
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(product), null), 0);
    }

    public ResponseEntity<Object> deleteProduct(Long id) {
        Product product = getProduct(id);
        product.setIsDeleted(1);
        repo.save(product);
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 0);
    }

    public Product getProduct(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public ProductTypes getProductType(Long id) {
        return typeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Type not found"));
    }

}
