package edu.icet.coursework.dao;

import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.dto.User;
import javafx.collections.ObservableList;

public interface CrudDao <T> extends SuperDao{

    public boolean addUser(T entity);
    public boolean addSupplier(T entity);
    public boolean addProduct(T entity);
    public Product searchProduct(String pid);
    public ObservableList<Product> loadProducts();
    public boolean addOrder(T entity);
    public ObservableList<User> searchUser();
    public boolean removeProduct(String pid);
    public boolean updateProduct(T entity);
    public Supplier searchSupplier(String sid);
    public boolean removeSupplier(String sid);
    public boolean updateSupplier(T entity);
    public User searchUserById(String uid);
    public boolean updateUser(T entity);
    public boolean removeUser(String uid);
    public ObservableList<Supplier> searchAllSuppliers();
    public ObservableList<Order> searchAllOrders();
    public ObservableList<Product> searchAllProducts();
}
