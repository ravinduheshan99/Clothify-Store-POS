package edu.icet.coursework.dao.custom.impl;

import edu.icet.coursework.dao.custom.ProductDao;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.entity.OrderEntity;
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
