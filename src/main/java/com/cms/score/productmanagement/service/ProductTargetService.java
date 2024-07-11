package com.cms.score.productmanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cms.score.common.response.Message;
import com.cms.score.common.response.Response;
import com.cms.score.common.response.dto.GlobalDto;
import com.cms.score.common.reuse.Filter;
import com.cms.score.common.reuse.PageConvert;
import com.cms.score.productmanagement.model.ProductTarget;
import com.cms.score.productmanagement.repository.PagProductTarget;
import com.cms.score.productmanagement.repository.ProductTargetRepository;

@Service
public class ProductTargetService {

    @Autowired
    private ProductTargetRepository repo;

    @Autowired
    private PagProductTarget pagRepo;

    public ResponseEntity<Object> getProducts(int page, int size) {
        Specification<ProductTarget> spec = Specification
                .where(new Filter<ProductTarget>().isNotDeleted())
                .and(new Filter<ProductTarget>().orderByIdDesc());
        Page<ProductTarget> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getProductTargetById(Long id) {
        Optional<ProductTarget> productTarget = getProductTarget(id);
        if (!productTarget.isPresent()) {
            return Response.buildResponse(new GlobalDto(Message.NOT_FOUND_DEFAULT.getStatusCode(), null,
                    Message.NOT_FOUND_DEFAULT.getMessage(), null, productTarget, null), 1);
        }
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, productTarget, null), 1);
    }

    public ResponseEntity<Object> createProductTarget(ProductTarget productTarget) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(productTarget), null), 1);
    }

    public ResponseEntity<Object> updateProductTarget(Long id, ProductTarget productTarget) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, getProductTarget(id), null), 1);
    }

    public ResponseEntity<Object> deleteProductTarget(Long id) {
        Optional<ProductTarget> productTarget = getProductTarget(id);
        if (!productTarget.isPresent()) {
            return Response.buildResponse(new GlobalDto(Message.NOT_FOUND_DEFAULT.getStatusCode(), null,
                    Message.NOT_FOUND_DEFAULT.getMessage(), null, null, null), 0);
        }
        productTarget.get().setIsDeleted(1);
        repo.save(productTarget.get());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 0);
    }

    public Optional<ProductTarget> getProductTarget(Long id) {
        return repo.findById(id);
    }

}
