package edu.icet.coursework.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TableModelCart {

    private String productId;
    private String productName;
    private Integer qty;
    private Double unitPrice;
    private Double Total;
}
