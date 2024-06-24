package edu.icet.coursework.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderDetailsEntity")
@IdClass(OrderDetailsEntity.OrderDetailsEntityId.class)
public class OrderDetailsEntity {

    @Id
    private String productId;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_orderId")
    private OrderEntity order;

    private Double unitPrice;
    private Integer qty;
    private Double total;
    private Double discount;
    private String customerEmail;

    // Inner class for composite primary key
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetailsEntityId implements Serializable {
        private String productId;
        private String order;
    }
}
