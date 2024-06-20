package edu.icet.coursework.dao.custom.impl;

import edu.icet.coursework.dao.custom.SupplierDao;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.entity.OrderEntity;
import edu.icet.coursework.entity.SupplierEntity;
import edu.icet.coursework.util.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

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



}
