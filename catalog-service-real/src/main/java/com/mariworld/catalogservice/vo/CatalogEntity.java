package com.mariworld.catalogservice.vo;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "catalog")
public class CatalogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;

    private String productName;

    private Integer stock;

    private Integer unitPrice;

    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;
}
