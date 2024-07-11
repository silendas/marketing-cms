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
                Message.SUCESSFULLY_DEFAULT.getMessage(), res.getPageable(), res.getContent(), null), 1);
    }

    public ResponseEntity<Object> getProductById(Long id) {
        Optional<Product> product = getProduct(id);
        if (!product.isPresent()) {
            return Response.buildResponse(new GlobalDto(Message.NOT_FOUND_DEFAULT.getStatusCode(), null,
                    Message.NOT_FOUND_DEFAULT.getMessage(), null, product, null), 1);
        }
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.findById(id), null), 1);
    }

    public ResponseEntity<Object> createProduct(ProductDto dto) {
        List<String> details = new ArrayList<>();
        Product product = new Product();
        if (dto.getName() == null || dto.getName().isEmpty()) {
            details.add("Name tidak boleh kosong");
        }
        product.setName(dto.getName());
        Optional<ProductTypes> productType = getProductType(dto.getProductTypeId());
        if (productType.isPresent()) {
            product.setProductType(productType.get());
        } else {
            details.add("Tipe Produk tidak ditemukan");
        }
        if (details.size() > 0) {
            return Response.buildResponse(new GlobalDto(Message.FAILED_DEFAULT.getStatusCode(), null,
                    Message.FAILED_DEFAULT.getMessage(), null, null, details), 1);
        }
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(product), null), 0);
    }

    public ResponseEntity<Object> updateProduct(Long id, ProductDto dto) {
        List<String> details = new ArrayList<>();
        Optional<Product> product = getProduct(id);
        if (dto.getName() == null || dto.getName().isEmpty()) {
            details.add("Name tidak boleh kosong");
        }
        product.get().setName(dto.getName());
        Optional<ProductTypes> productType = getProductType(dto.getProductTypeId());
        if (productType.isPresent()) {
            product.get().setProductType(productType.get());
        } else {
            details.add("Tipe Produk tidak ditemukan");
        }
        if (details.size() > 0) {
            return Response.buildResponse(new GlobalDto(Message.FAILED_DEFAULT.getStatusCode(), null,
                    Message.FAILED_DEFAULT.getMessage(), null, null, details), 1);
        }
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(product.get()), null), 0);
    }

    public ResponseEntity<Object> patchProduct(Long id, ProductDto dto) {
        List<String> details = new ArrayList<>();
        Optional<Product> productOptional = getProduct(id);
        Product product = productOptional.get();

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            product.setName(dto.getName());
        }

        if (dto.getProductTypeId() != null) {
            Optional<ProductTypes> productType = getProductType(dto.getProductTypeId());
            if (productType.isPresent()) {
                product.setProductType(productType.get());
            } else {
                details.add("Tipe Produk tidak ditemukan");
            }
        }
        if (details.size() > 0) {
            return Response.buildResponse(new GlobalDto(Message.FAILED_DEFAULT.getStatusCode(), null,
                    Message.FAILED_DEFAULT.getMessage(), null, null, details), 1);
        }
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, repo.save(product), null), 0);
    }

    public ResponseEntity<Object> deleteProduct(Long id) {
        Optional<Product> product = getProduct(id);
        product.get().setIsDeleted(1);
        repo.save(product.get());
        return Response.buildResponse(new GlobalDto(Message.SUCESSFULLY_DEFAULT.getStatusCode(), null,
                Message.SUCESSFULLY_DEFAULT.getMessage(), null, null, null), 0);
    }

    public Optional<Product> getProduct(Long id) {
        return repo.findById(id);
    }

    public Optional<ProductTypes> getProductType(Long id) {
        return typeRepo.findById(id);
    }

}
