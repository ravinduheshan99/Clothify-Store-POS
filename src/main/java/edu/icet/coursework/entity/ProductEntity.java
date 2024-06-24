package edu.icet.coursework.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductEntity {

    @Id
    private String productId;

    private String supplierId;
    private String category;
    private String productName;
    private String size;
    private Double unitPrice;
    private Integer qty;
    private String description;
}
