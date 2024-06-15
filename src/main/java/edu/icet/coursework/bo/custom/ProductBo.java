package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.Supplier;

public interface ProductBo extends SuperBo {
    public boolean addProduct(Product product);
}
