package edu.icet.coursework.dao.custom.impl;

import edu.icet.coursework.dao.custom.ProductDao;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.entity.ProductEntity;
import edu.icet.coursework.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    @Override
    public boolean addUser(ProductEntity entity) {
        return false;
    }

    @Override
    public boolean addSupplier(ProductEntity entity) {
        return false;
    }

    @Override
    public boolean addProduct(ProductEntity entity) {
        Session session = null;
        Transaction transaction = null;

        try{
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;
        }catch (Exception e){
            if (transaction !=null){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }

    @Override
    public ObservableList<Product> loadProducts() {
        ObservableList<Product> allProducts = FXCollections.observableArrayList();
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            List<ProductEntity> productEntities = session.createQuery("FROM ProductEntity",ProductEntity.class).list();
            for (ProductEntity entity: productEntities){
                Product product = new ModelMapper().map(entity,Product.class);
                allProducts.add(product);
            }
            transaction.commit();

        }catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Failed to load products",e);

        }finally {
            if (session!=null){
                session.close();
            }
        }
        return allProducts;
    }

    @Override
    public boolean addOrder(ProductEntity entity) {
        return false;
    }

    @Override
    public ObservableList<User> searchUser() {
        return null;
    }

    @Override
    public boolean removeProduct(String cid) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            ProductEntity productEntity = session.get(ProductEntity.class, cid);
            if (productEntity == null) {
                return false;  // No customer found with the given ID
            }

            session.delete(productEntity);

            transaction.commit();
            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // or use a logger to log the exception
            return false;

        } finally {

            if (session != null) {
                session.close();
            }

        }
    }

    @Override
    public boolean updateProduct(ProductEntity entity) {
        Session session = null;
        Transaction transaction = null;

        try {

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session.merge(entity); // merge does not require entity.getId(), just the entity
            transaction.commit();
            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // or use a logger to log the exception
            return false;

        } finally {

            if (session != null) {
                session.close();
            }

        }
    }

    @Override
    public Supplier searchSupplier(String sid) {
        return null;
    }

    @Override
    public boolean removeSupplier(String sid) {
        return false;
    }

    @Override
    public boolean updateSupplier(ProductEntity entity) {
        return false;
    }

    @Override
    public User searchUserById(String uid) {
        return null;
    }

    @Override
    public boolean updateUser(ProductEntity entity) {
        return false;
    }

    @Override
    public boolean removeUser(String uid) {
        return false;
    }

    @Override
    public ObservableList<Supplier> searchAllSuppliers() {
        return null;
    }

    @Override
    public ObservableList<Order> searchAllOrders() {
        return null;
    }

    @Override
    public ObservableList<Product> searchAllProducts() {
        ObservableList<Product> allProducts = FXCollections.observableArrayList();
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            List<ProductEntity> productEntities = session.createQuery("FROM ProductEntity",ProductEntity.class).list();
            for (ProductEntity entity: productEntities){
                Product product = new ModelMapper().map(entity,Product.class);
                allProducts.add(product);
            }
            transaction.commit();

        }catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Failed To Find Products",e);

        }finally {
            if (session!=null){
                session.close();
            }
        }
        return allProducts;
    }

    @Override
    public Product searchProduct(String pid) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            ProductEntity productEntity = session.get(ProductEntity.class,pid);
            if (productEntity == null){
                return null;
            }

            Product product = new ModelMapper().map(productEntity,Product.class);
            transaction.commit();
            return product;

        }catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
            return null;

        }finally {
            if (session!=null){
                session.close();
            }
        }
    }

}
