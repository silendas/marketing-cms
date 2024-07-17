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
import com.cms.score.common.reuse.ConvertDate;
import com.cms.score.common.reuse.Filter;
import com.cms.score.common.reuse.PageConvert;
import com.cms.score.productmanagement.dto.KKPDto;
import com.cms.score.productmanagement.dto.ProductPlanDto;
import com.cms.score.productmanagement.dto.ResponseTargetDto;
import com.cms.score.productmanagement.model.Product;
import com.cms.score.productmanagement.model.ProductPlan;
import com.cms.score.productmanagement.model.ProductTarget;
import com.cms.score.productmanagement.repository.PagProductTarget;
import com.cms.score.productmanagement.repository.ProductPlanRepository;
import com.cms.score.productmanagement.repository.ProductRepository;
import com.cms.score.productmanagement.repository.ProductTargetRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class KKPService {

    @Autowired
    private ProductPlanRepository planRepo;

    @Autowired
    private ProductTargetRepository repo;

    @Autowired
    private PagProductTarget pagRepo;

    @Autowired
    private ProductRepository productRepo;

    public ResponseEntity<Object> getKKP(Long productId, Date start_date, Date end_date, int page, int size) {
        Specification<ProductTarget> spec = Specification
                .where(new Filter<ProductTarget>().isNotDeleted())
                .and(new Filter<ProductTarget>().orderByIdDesc());
        Page<ProductTarget> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getKKPReport(Date start_date, Date end_date, int page, int size) {
        Specification<ProductTarget> spec = Specification
                .where(new Filter<ProductTarget>().isNotDeleted())
                .and(new Filter<ProductTarget>().orderByIdDesc());

        List<ProductTarget> allResults = repo.findAll(spec);
        Map<String, GroupedProductTarget> groupedTargets = new HashMap<>();
        for (ProductTarget ptp : allResults) {
            String key = ptp.getProduct().getName() + "|" + ptp.getTarget() + "|" + ptp.getAchievement();
            groupedTargets
                    .computeIfAbsent(key, k -> new GroupedProductTarget(toProductTargetDto(ptp), new ArrayList<>()))
                    .getProductPlans().add(ptp.getPlanning());
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
        int i = 0;
        for (ProductPlanDto plan : dto.getProductPlan()) {
            ProductTarget kkp = new ProductTarget();
            kkp.setProduct(getProduct(dto.getProductId()));
            kkp.setTarget(dto.getTarget());
            kkp.setAchievement(dto.getAchievement());
            if (plan.getTarget() == null || plan.getDate() == null) {
                details.add("Rencana dengan index : {" + i + "} gagal disimpan, target dan tanggal harus diisi");
            } else {
                ProductPlan createdPlan = createPlan(plan);
                if (createdPlan != null) {
                    kkp.setPlanning(createdPlan);
                    repo.save(kkp);
                } else {
                    details.add("Rencana dengan index : {" + i + "} gagal disimpan, rencana degan bulan "
                            + ConvertDate.indonesianFormat(plan.getDate()) + " sudah ada");
                }
            }
            i = i + 1;
        }
        if (details.size() > 0) {
            return Response.buildResponse(new GlobalDto(Message.SUCCESS_FAILED_DEFAULT.getStatusCode(), null,
                    Message.SUCCESS_FAILED_DEFAULT.getMessage(), null, null, details), 1);
        }
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 0);
    }

    private ProductPlan createPlan(@Valid ProductPlanDto dto) {
        List<ProductPlan> existingPlans = planRepo.findByMonth(dto.getDate());
        if (!existingPlans.isEmpty()) {
            return null;
        }
        ProductPlan plan = new ProductPlan();
        plan.setTarget(dto.getTarget());
        plan.setDate(dto.getDate());
        return planRepo.save(plan);
    }

    private Product getProduct(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private ResponseTargetDto toProductTargetDto(ProductTarget target) {
        ResponseTargetDto dto = new ResponseTargetDto();
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
