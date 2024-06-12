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
public class OrderEntity {

    @Id
    private String orderId;

    private String userId;
    private String productId;
    private Double unitPrice;
    private Integer qty;
    private Double total;
    private String customerEmail;
}
