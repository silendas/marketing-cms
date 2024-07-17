package com.cms.score.productmanagement.dto;

import java.sql.Timestamp;

import com.cms.score.productmanagement.model.Product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ResponseTargetDto {

    private Timestamp createdDate;
    private String createdBy;
    private Timestamp updatedDate;
    private String updatedBy;
    private Product product;
    private double target;
    private double achievement;

}
