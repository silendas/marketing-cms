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
import com.cms.score.productmanagement.dto.ProductTargetDto;
import com.cms.score.productmanagement.model.Product;
import com.cms.score.productmanagement.model.ProductTarget;
import com.cms.score.productmanagement.repository.PagProductTarget;
import com.cms.score.productmanagement.repository.ProductRepository;
import com.cms.score.productmanagement.repository.ProductTargetRepository;

@Service
public class ProductTargetService {

    @Autowired
    private ProductTargetRepository repo;

    @Autowired
    private PagProductTarget pagRepo;

    @Autowired
    private ProductRepository productRepo;

    public ResponseEntity<Object> getProductTarget(int page, int size) {
        Specification<ProductTarget> spec = Specification
                .where(new Filter<ProductTarget>().isNotDeleted())
                .and(new Filter<ProductTarget>().orderByIdDesc());
        Page<ProductTarget> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getProductTargetById(Long id) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.findById(id), null), 1);
    }

    public ResponseEntity<Object> createProductTarget(ProductTargetDto dto) {
        ProductTarget productTarget = new ProductTarget();
        productTarget.setProduct(getProduct(dto.getProductId()));
        productTarget.setTarget(dto.getTarget());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(productTarget), null), 1);
    }

    public ResponseEntity<Object> updateProductTarget(Long id, ProductTargetDto dto) {
        ProductTarget productTarget = getProductTarget(id);
        productTarget.setProduct(getProduct(dto.getProductId()));
        productTarget.setTarget(dto.getTarget());
        repo.save(productTarget);
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null,null, null), 0);
    }

    public ResponseEntity<Object> deleteProductTarget(Long id) {
        ProductTarget productTarget = getProductTarget(id);
        productTarget.setIsDeleted(1);
        repo.save(productTarget);
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 0);
    }

    public ProductTarget getProductTarget(Long id) {
        return repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product Target not found"));
    }

    private Product getProduct(Long id) {
        return productRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }

}
