package edu.icet.coursework.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDate orderDate;
    private LocalDateTime orderTime;
    private List<OrderDetails> orderDetailsList;
    private Double totalBillAmount;
}