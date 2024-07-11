package com.cms.score.productmanagement.service;

import java.util.ArrayList;
import java.util.List;
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
import com.cms.score.productmanagement.dto.ProductTypeDto;
import com.cms.score.productmanagement.model.ProductTypes;
import com.cms.score.productmanagement.repository.PagProductType;
import com.cms.score.productmanagement.repository.ProductTypeRepository;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository repo;

    @Autowired
    private PagProductType pagRepo;

    public ResponseEntity<Object> getProductTypes(int page, int size) {
        Specification<ProductTypes> spec = Specification
                .where(new Filter<ProductTypes>().isNotDeleted())
                .and(new Filter<ProductTypes>().orderByIdDesc());
        Page<ProductTypes> res = pagRepo.findAll(spec, PageRequest.of(page, size));
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), res.getPageable(), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getProductTypeById(Long id) {
        Optional<ProductTypes> productType = getProductType(id);
        if (!productType.isPresent()) {
            return Response.buildResponse(new GlobalDto(Message.NOT_FOUND_DEFAULT.getStatusCode(), null,
                    Message.NOT_FOUND_DEFAULT.getMessage(), null, productType, null), 1);
        }
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, productType, null), 1);
    }

    public ResponseEntity<Object> createProductType(ProductTypeDto dto) {
        List<String> details = new ArrayList<>();
        ProductTypes productType = new ProductTypes();
        if (dto.getName() == null || dto.getName().isEmpty()) {
            details.add("Name tidak boleh kosong");
            return Response.buildResponse(new GlobalDto(Message.FAILED_DEFAULT.getStatusCode(), null,
                    Message.FAILED_DEFAULT.getMessage(), null, null, details), 1);
        }
        productType.setName(dto.getName());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(productType), null), 0);
    }

    public ResponseEntity<Object> updateProductType(Long id, ProductTypeDto dto) {
        List<String> details = new ArrayList<>();
        ProductTypes productType = getProductType(id).get();
        if (dto.getName() == null || dto.getName().isEmpty()) {
            details.add("Name tidak boleh kosong");
            return Response.buildResponse(new GlobalDto(Message.FAILED_DEFAULT.getStatusCode(), null,
                    Message.FAILED_DEFAULT.getMessage(), null, null, details), 1);
        }
        productType.setName(dto.getName());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(productType), null), 0);
    }

    public ResponseEntity<Object> patchProductType(Long id, ProductTypeDto dto) {
        ProductTypes productType = getProductType(id).get();

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            productType.setName(dto.getName());
        }

        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(productType), null), 0);
    }

    public ResponseEntity<Object> deleteProductType(Long id) {
        ProductTypes productType = getProductType(id).get();
        productType.setIsDeleted(1);
        repo.save(productType);
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 1);
    }

    public Optional<ProductTypes> getProductType(Long id) {
        return repo.findById(id);
    }

}
