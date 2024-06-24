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
public class SupplierEntity {

    @Id
    private String supplierId;

    private String companyName;
    private String productCategory;
    private String companyContactNumber;
    private String companyEmail;
    private String productDescription;
}
