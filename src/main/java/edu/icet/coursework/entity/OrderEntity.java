package edu.icet.coursework.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderentity") // Ensure table name matches your database schema
public class OrderEntity {
    @Id
    private String orderId1; // Manually assigned identifier

    private String userId;
    private Date orderDate;
    private String orderTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetailsEntity> orderDetailsList;

    private Double totalBillAmount;
}
