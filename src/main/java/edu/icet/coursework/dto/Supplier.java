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
public class Supplier {

    @Id
    private String supplierId;

    private String productId;
    private String productName;
    private String companyName;
    private String companyEmail;

}
