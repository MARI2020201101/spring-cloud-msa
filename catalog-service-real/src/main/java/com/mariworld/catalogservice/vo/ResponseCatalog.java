package com.mariworld.catalogservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseCatalog {
    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer stock;
    private Date createdAt;
}
