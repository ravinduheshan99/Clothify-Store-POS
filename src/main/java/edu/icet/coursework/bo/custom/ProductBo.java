package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.Product;
import javafx.collections.ObservableList;


public interface ProductBo extends SuperBo {
    public ObservableList<Product> loadProducts();
    public boolean addProduct(Product product);
    public Product searchProduct(String pid);
    public boolean removeProduct(String cid);
    public boolean updateProduct(Product product);
    public ObservableList<Product> searchAllProducts();
}
