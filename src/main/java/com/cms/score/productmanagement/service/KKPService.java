package com.cms.score.productmanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.cms.score.productmanagement.dto.KKPDto;
import com.cms.score.productmanagement.dto.ProductPlanDto;
import com.cms.score.productmanagement.dto.ProductTargetDto;
import com.cms.score.productmanagement.dto.ResponseTargetDto;
import com.cms.score.productmanagement.model.Product;
import com.cms.score.productmanagement.model.ProductPlan;
import com.cms.score.productmanagement.model.ProductTarget;
import com.cms.score.productmanagement.model.ProductTargetPlanning;
import com.cms.score.productmanagement.repository.PTPRepository;
import com.cms.score.productmanagement.repository.PagPTP;
import com.cms.score.productmanagement.repository.ProductPlanRepository;
import com.cms.score.productmanagement.repository.ProductRepository;
import com.cms.score.productmanagement.repository.ProductTargetRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class KKPService {

    @Autowired
    private PTPRepository repo;

    @Autowired
    private PagPTP pagRepo;

    @Autowired
    private ProductPlanRepository planRepo;

    @Autowired
    private ProductTargetRepository targetRepo;

    @Autowired
    private ProductRepository productRepo;

    public ResponseEntity<Object> getKKP(Long productId, Date start_date, Date end_date, int page, int size) {
        Specification<ProductTargetPlanning> spec = Specification
                .where(new Filter<ProductTargetPlanning>().isNotDeleted())
                .and(new Filter<ProductTargetPlanning>().orderByIdDesc());
        Page<ProductTargetPlanning> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getKKPReport(Date start_date, Date end_date, int page, int size) {
        Specification<ProductTargetPlanning> spec = Specification
                .where(new Filter<ProductTargetPlanning>().isNotDeleted())
                .and(new Filter<ProductTargetPlanning>().orderByIdDesc());

        List<ProductTargetPlanning> allResults = repo.findAll(spec);
        Map<String, GroupedProductTarget> groupedTargets = new HashMap<>();
        for (ProductTargetPlanning ptp : allResults) {
            ProductTarget target = ptp.getProductTarget();
            String key = target.getProduct().getName() + "|" + target.getTarget() + "|" + target.getAchievement();
            groupedTargets.computeIfAbsent(key, k -> new GroupedProductTarget(toProductTargetDto(target), new ArrayList<>()))
                    .getProductPlans().add(ptp.getProductPlan());
        }
        List<GroupedProductTarget> groupedResults = new ArrayList<>(groupedTargets.values());
        int start = Math.min((int) PageRequest.of(page, size).getOffset(), groupedResults.size());
        int end = Math.min((start + PageRequest.of(page, size).getPageSize()), groupedResults.size());
        List<GroupedProductTarget> paginatedResults = groupedResults.subList(start, end);
        Page<GroupedProductTarget> res = new PageImpl<>(paginatedResults, PageRequest.of(page, size),
                groupedResults.size());

        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getKKPById(Long id) {
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.findById(id), null), 1);
    }

    public ResponseEntity<Object> createKKP(@Valid KKPDto dto) {
        List<String> details = new ArrayList<>();
        ProductTarget target = createTarget(dto.getProductTarget());
        if (target == null) {
            details.add("Data gagal disimpan, target produk harus dilengkapi semua");
        } else {
            int i = 0;
            for (ProductPlanDto plan : dto.getProductPlan()) {
                ProductTargetPlanning kkp = new ProductTargetPlanning();
                kkp.setProductTarget(target);
                if (plan.getTarget() == null || plan.getDate() == null) {
                    details.add("Rencana dengan index : {" + i + "} gagal disimpan, target dan tanggal harus diisi");
                } else {
                    kkp.setProductPlan(createPlan(plan));
                    repo.save(kkp);
                }
                i = i + 1;
            }
        }
        if (details.size() > 0) {
            return Response.buildResponse(new GlobalDto(Message.SUCCESS_FAILED_DEFAULT.getStatusCode(), null,
                    Message.SUCCESS_FAILED_DEFAULT.getMessage(), null, null, details), 1);
        }
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 0);
    }

    private ProductPlan createPlan(@Valid ProductPlanDto dto) {
        ProductPlan plan = new ProductPlan();
        plan.setTarget(dto.getTarget());
        plan.setDate(dto.getDate());
        return planRepo.save(plan);
    }

    private ProductTarget createTarget(@Valid ProductTargetDto dto) {
        ProductTarget target = new ProductTarget();
        if (dto.getProductId() == null || dto.getProductId() == 0 || dto.getTarget() == null
                || dto.getAchievement() == null) {
            return null;
        }
        target.setTarget(dto.getTarget());
        target.setProduct(getProduct(dto.getProductId()));
        target.setAchievement(dto.getAchievement());
        return targetRepo.save(target);
    }

    private Product getProduct(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private ResponseTargetDto toProductTargetDto(ProductTarget target) {
        ResponseTargetDto dto = new ResponseTargetDto();
        dto.setIsDeleted(target.getIsDeleted());
        dto.setCreatedDate(target.getCreatedDate());
        dto.setCreatedBy(target.getCreatedBy());
        dto.setUpdatedDate(target.getUpdatedDate());
        dto.setUpdatedBy(target.getUpdatedBy());
        dto.setProduct(target.getProduct());
        dto.setTarget(target.getTarget());
        dto.setAchievement(target.getAchievement());
        return dto;
    }

    private static class GroupedProductTarget {
        private ResponseTargetDto productTarget;
        private List<ProductPlan> productPlans;

        public GroupedProductTarget(ResponseTargetDto productTarget, List<ProductPlan> productPlans) {
            this.productTarget = productTarget;
            this.productPlans = productPlans;
        }

        public ResponseTargetDto getProductTarget() {
            return productTarget;
        }

        public void setProductTarget(ResponseTargetDto productTarget) {
            this.productTarget = productTarget;
        }

        public List<ProductPlan> getProductPlans() {
            return productPlans;
        }

        public void setProductPlans(List<ProductPlan> productPlans) {
            this.productPlans = productPlans;
        }
    }

}
