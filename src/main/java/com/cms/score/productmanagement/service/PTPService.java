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
import com.cms.score.productmanagement.dto.PTPDto;
import com.cms.score.productmanagement.model.ProductPlan;
import com.cms.score.productmanagement.model.ProductTarget;
import com.cms.score.productmanagement.model.ProductTargetPlanning;
import com.cms.score.productmanagement.repository.PTPRepository;
import com.cms.score.productmanagement.repository.PagPTP;
import com.cms.score.productmanagement.repository.ProductPlanRepository;
import com.cms.score.productmanagement.repository.ProductTargetRepository;

@Service
public class PTPService {

    @Autowired
    private PTPRepository repo;

    @Autowired
    private ProductPlanRepository planRepository;

    @Autowired
    private PagPTP pagRepo;

    @Autowired
    private ProductTargetRepository targetRepo;

    public ResponseEntity<Object> getProductTarget(int page, int size) {
        Specification<ProductTargetPlanning> spec = Specification
                .where(new Filter<ProductTargetPlanning>().isNotDeleted())
                .and(new Filter<ProductTargetPlanning>().orderByIdDesc());
        Page<ProductTargetPlanning> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getProductTargetById(Long id) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.findById(id), null), 1);
    }

    public ResponseEntity<Object> createProductTarget(PTPDto dto) {
        ProductTargetPlanning productTarget = new ProductTargetPlanning();
        productTarget.setProductPlan(createProductPlan(dto.getProductPlanId()));
        productTarget.setProductTarget(createProductTarget(dto.getProductTargetId()));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(productTarget), null), 1);
    }

    public ResponseEntity<Object> updateProductTarget(Long id, PTPDto dto) {
        ProductTargetPlanning productTarget = getProductTargetExist(id);
        productTarget.setProductPlan(createProductPlan(dto.getProductPlanId()));
        productTarget.setProductTarget(createProductTarget(dto.getProductTargetId()));
        repo.save(productTarget);
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null,null, null), 0);
    }

    public ResponseEntity<Object> deleteProductTarget(Long id) {
        ProductTargetPlanning productTarget = getProductTargetExist(id);
        productTarget.setIsDeleted(1);
        repo.save(productTarget);
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null,null, null), 0);
    }

    public ProductTargetPlanning getProductTargetExist(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Target Planning not found"));
    }

    public ProductTarget createProductTarget(Long dto) {
        return targetRepo.findById(dto)
                .orElseThrow(() -> new ResourceNotFoundException("Product Target Planning not found"));
    }

    public ProductPlan createProductPlan(Long dto) {
        return planRepository.findById(dto).orElseThrow(() -> new ResourceNotFoundException("Product Plan not found"));
    }

}
