package edu.icet.coursework.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetailsEntity {
    @Id
    private String orderId2;

    private String productId;
    private Double unitPrice;
    private Integer qty;
    private Double total;
    private Double discount;
    private String customerEmail;

    @ManyToOne  // Define the relationship to OrderEntity
    @JoinColumn(name = "orderId1")
    private OrderEntity order;
}