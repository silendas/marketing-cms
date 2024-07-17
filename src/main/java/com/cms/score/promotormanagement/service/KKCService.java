package com.cms.score.promotormanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.cms.score.promotormanagement.dto.BranchTargetDto;
import com.cms.score.promotormanagement.dto.KKCDto;
import com.cms.score.promotormanagement.dto.ResponseKKCDto;
import com.cms.score.promotormanagement.dto.ResponseTargetDto;
import com.cms.score.promotormanagement.model.Branch;
import com.cms.score.promotormanagement.model.BranchPlan;
import com.cms.score.promotormanagement.model.BranchTarget;
import com.cms.score.promotormanagement.repository.BranchRepository;
import com.cms.score.promotormanagement.repository.BranchTargetRepository;
import com.cms.score.promotormanagement.repository.BranchPlanRepository;
import com.cms.score.promotormanagement.repository.PagBranchTargetPlanning;

@Service
public class KKCService {

    @Autowired
    private BranchTargetRepository repo;

    @Autowired
    private PagBranchTargetPlanning pagRepo;

    @Autowired
    private BranchRepository branchRepo;

    @Autowired
    private BranchPlanRepository targetRepo;

    public ResponseEntity<Object> getKKC(int page, int size) {
        Specification<BranchTarget> spec = Specification
                .where(new Filter<BranchTarget>().isNotDeleted())
                .and(new Filter<BranchTarget>().orderByIdDesc());
        Page<BranchTarget> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null, null,
                PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getKKCReport(int page, int size) {
        Specification<BranchTarget> spec = Specification
                .where(new Filter<BranchTarget>().isNotDeleted())
                .and(new Filter<BranchTarget>().orderByIdDesc());

        List<BranchTarget> allResults = repo.findAll(spec);
        Map<Long, ResponseKKCDto> groupedTargets = allResults.stream().collect(Collectors.groupingBy(
                ptp -> ptp.getBranch().getId(),
                Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Branch branch = list.get(0).getBranch();
                    List<ResponseTargetDto> branchTargets = list.stream()
                            .map(ptp -> new ResponseTargetDto(ptp.getBranchPlan().getTarget(),
                                    ptp.getBranchPlan().getDate()))
                            .collect(Collectors.toList());
                    ResponseKKCDto branchDto = new ResponseKKCDto();
                    branchDto.setId(branch.getId());
                    branchDto.setName(branch.getName());
                    branchDto.setBranchTargets(branchTargets);
                    return branchDto;
                })));
        List<ResponseKKCDto> groupedResults = new ArrayList<>(groupedTargets.values());
        int start = Math.min((int) PageRequest.of(page, size).getOffset(), groupedResults.size());
        int end = Math.min((start + PageRequest.of(page, size).getPageSize()), groupedResults.size());
        List<ResponseKKCDto> paginatedResults = groupedResults.subList(start, end);
        Page<ResponseKKCDto> res = new PageImpl<>(paginatedResults, PageRequest.of(page, size),
                groupedResults.size());

        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(),
                PageConvert.convert(res), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getKKCById(Long id) {
        return Response.buildResponse(
                new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null, null, null, getKKCObj(id), null), 1);
    }

    public ResponseEntity<Object> addKKC(KKCDto kkc) {
        List<String> details = new ArrayList<>();
        int i = 0;
        for (BranchTargetDto target : kkc.getBranchTarget()) {
            BranchTarget newKKC = new BranchTarget();
            newKKC.setBranch(getBranchObj(kkc.getBranchId()));
            newKKC.setCluster(kkc.getPercentage());
            if (target.getTarget() == null || target.getDate() == null) {
                details.add("Target dengan index : {" + i + "} gagal disimpan, target dan tanggal harus diisi");
            } else {
                BranchPlan createdTarget = createTarget(target);
                if (createdTarget != null) {
                    newKKC.setBranchPlan(createdTarget);
                    repo.save(newKKC);
                } else {
                    details.add("Rencana dengan index : {" + i + "} gagal disimpan, rencana degan bulan "
                            + ConvertDate.indonesianFormat(target.getDate()) + " sudah ada");
                }
            }
            i = i + 1;
        }
        if (details.size() > 0) {
            return Response.buildResponse(new GlobalDto(Message.SUCCESS_FAILED_DEFAULT.getStatusCode(), null,
                    Message.SUCCESS_FAILED_DEFAULT.getMessage(), null, null, details), 1);
        }
        return Response.buildResponse(
                new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                        Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null),
                0);
    }

    public Branch getBranchObj(Long id) {
        return branchRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch is not found"));
    }

    public BranchPlan getTargetObj(Long id) {
        return targetRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Target is not found"));
    }

    public BranchTarget getKKCObj(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("KKC is not found"));
    }

    public BranchPlan createTarget(BranchTargetDto target) {
        List<BranchPlan> existingPlans = targetRepo.findByMonth(target.getDate());
        if (!existingPlans.isEmpty()) {
            return null;
        }
        BranchPlan newTarget = new BranchPlan();
        newTarget.setTarget(target.getTarget());
        newTarget.setDate(target.getDate());
        return targetRepo.save(newTarget);
    }
}
