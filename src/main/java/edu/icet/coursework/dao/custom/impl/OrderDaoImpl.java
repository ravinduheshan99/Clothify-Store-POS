package edu.icet.coursework.dao.custom.impl;

import edu.icet.coursework.dao.custom.OrderDao;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.entity.OrderEntity;
import edu.icet.coursework.util.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean addUser(OrderEntity entity) {
        return false;
    }

    @Override
    public boolean addSupplier(OrderEntity entity) {
        return false;
    }

    @Override
    public boolean addProduct(OrderEntity entity) {
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
    public boolean addOrder(OrderEntity entity) {
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

}
