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

    private String productName;
    private Double unitPrice;
    private Integer qty;
    private String description;
    private String size;
    private String category;

}
