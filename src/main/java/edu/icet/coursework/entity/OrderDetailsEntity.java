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
@Table(name = "OrderDetailsEntity")
public class OrderDetailsEntity {
    @Id
    @Column(name = "orderId2", nullable = false, length = 255)
    private String orderId2;

    private String productId;
    private Double unitPrice;
    private Integer qty;
    private Double total;
    private Double discount;
    private String customerEmail;

    @ManyToOne
    @JoinColumn(name = "orderId1", nullable = false)
    private OrderEntity order;
}