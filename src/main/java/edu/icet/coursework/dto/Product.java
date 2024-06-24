package edu.icet.coursework.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {

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
