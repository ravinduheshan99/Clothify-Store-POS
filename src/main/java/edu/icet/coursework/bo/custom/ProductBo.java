package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.Supplier;
import javafx.collections.ObservableList;

public interface ProductBo extends SuperBo {
    public boolean addProduct(Product product);
    public Product searchProduct(String pid);
    public ObservableList<Product> loadProducts();
}
