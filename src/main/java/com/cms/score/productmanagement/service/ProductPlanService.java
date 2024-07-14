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
import com.cms.score.productmanagement.dto.ProductPlanDto;
import com.cms.score.productmanagement.model.ProductPlan;
import com.cms.score.productmanagement.repository.PagProductPlan;
import com.cms.score.productmanagement.repository.ProductPlanRepository;

@Service
public class ProductPlanService {

    @Autowired
    private ProductPlanRepository repo;

    @Autowired
    private PagProductPlan pagRepo;

    public ResponseEntity<Object> getProductPlan(int page, int size) {
        Specification<ProductPlan> spec = Specification
                .where(new Filter<ProductPlan>().isNotDeleted())
                .and(new Filter<ProductPlan>().orderByIdDesc());
        Page<ProductPlan> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getProductPlanById(Long id) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.findById(id), null), 1);
    }

    public ResponseEntity<Object> createProductPlan(ProductPlanDto dto) {
        ProductPlan productPlan = new ProductPlan();
        productPlan.setTarget(dto.getTarget());
        productPlan.setDate(dto.getDate());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 0);
    }

    public ResponseEntity<Object> updateProductPlan(ProductPlanDto dto, Long id) {
        ProductPlan productPlan = getProductPlan(id);
        productPlan.setTarget(dto.getTarget());
        productPlan.setDate(dto.getDate());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 0);
    }

    public ResponseEntity<Object> deleteProductPlan(Long id) {
        ProductPlan productPlan = getProductPlan(id);
        productPlan.setIsDeleted(1);
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 0);
    }

    public ProductPlan getProductPlan(Long id) {
        return repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product Plan not found"));
    }

}
