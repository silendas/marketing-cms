package com.cms.score.common.reuse;

import org.springframework.data.jpa.domain.Specification;

public class Filter<T> {
    
    public Specification<T> byNameLike(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name != null) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
            }
            return null;
        };
    }
    
}
