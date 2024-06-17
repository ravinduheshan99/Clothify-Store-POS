package edu.icet.coursework.dto;


import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private String userId;
    private Date orderDate;
    private String orderTime;  // Use LocalDateTime instead of TemporalAccessor
    private List<OrderDetails> orderDetailsList;
    private Double totalBillAmount;
}