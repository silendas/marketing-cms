package com.cms.score.common.reuse;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class Filter<T> {

    public Specification<T> byNameLike(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name != null) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
            }
            return null;
        };
    }

    public Specification<T> isNotDeleted() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.notEqual(root.get("isDeleted"), 1);
        };
    }

    public Specification<T> orderByIdDesc() {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("id"))); // Urutkan berdasarkan ID secara descending
            return criteriaBuilder.conjunction();
        };
    }

    public Specification<T> orderByIdAsc() {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("id"))); // Urutkan berdasarkan ID secara descending
            return criteriaBuilder.conjunction();
        };
    }

}
