package edu.icet.coursework.dao.custom.impl;

import edu.icet.coursework.dao.custom.SupplierDao;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.entity.SupplierEntity;
import edu.icet.coursework.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public boolean addUser(SupplierEntity entity) {
        return false;
    }

    @Override
    public boolean addSupplier(SupplierEntity entity) {
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
    public boolean addProduct(SupplierEntity entity) {
        return false;
    }

    @Override
    public Product searchProduct(String pid) {
        return null;
    }

    @Override
    public ObservableList<Product> loadProducts() {
        return null;
    }

    @Override
    public boolean addOrder(SupplierEntity entity) {
        return false;
    }

    @Override
    public ObservableList<User> searchUser() {
        return null;
    }

    @Override
    public boolean removeProduct(String cid) {
        return false;
    }

    @Override
    public boolean updateProduct(SupplierEntity entity) {
        return false;
    }

    @Override
    public Supplier searchSupplier(String sid) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            SupplierEntity supplierEntity = session.get(SupplierEntity.class,sid);
            if (supplierEntity == null){
                return null;
            }

            Supplier supplier = new ModelMapper().map(supplierEntity,Supplier.class);
            transaction.commit();
            return supplier;

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

    @Override
    public boolean removeSupplier(String sid) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            SupplierEntity supplierEntity = session.get(SupplierEntity.class, sid);
            if (supplierEntity == null) {
                return false;  // No customer found with the given ID
            }

            session.delete(supplierEntity);

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
    public boolean updateSupplier(SupplierEntity entity) {
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
    public User searchUserById(String uid) {
        return null;
    }

    @Override
    public boolean updateUser(SupplierEntity entity) {
        return false;
    }

    @Override
    public boolean removeUser(String uid) {
        return false;
    }

    @Override
    public ObservableList<Supplier> searchAllSuppliers() {
        ObservableList<Supplier> allSuppliers = FXCollections.observableArrayList();
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            List<SupplierEntity> supplierEntities = session.createQuery("FROM SupplierEntity",SupplierEntity.class).list();
            for (SupplierEntity entity: supplierEntities){
                Supplier supplier = new ModelMapper().map(entity,Supplier.class);
                allSuppliers.add(supplier);
            }
            transaction.commit();

        }catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Failed To Find Suppliers",e);

        }finally {
            if (session!=null){
                session.close();
            }
        }
        return allSuppliers;
    }

    @Override
    public ObservableList<Order> searchAllOrders() {
        return null;
    }

    @Override
    public ObservableList<Product> searchAllProducts() {
        return null;
    }

}
