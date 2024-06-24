package edu.icet.coursework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private String productId;
    private Double unitPrice;
    private Integer qty;
    private Double total;
    private String customerEmail;
}