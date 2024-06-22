package edu.icet.coursework.dao.custom.impl;

import edu.icet.coursework.dao.custom.UserDao;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.entity.OrderEntity;
import edu.icet.coursework.entity.ProductEntity;
import edu.icet.coursework.entity.UserEntity;
import edu.icet.coursework.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean addUser(UserEntity entity) {

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
    public boolean addSupplier(UserEntity entity) {
        return false;
    }

    @Override
    public boolean addProduct(UserEntity entity) {
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
    public boolean addOrder(UserEntity entity) {
        return false;
    }

    @Override
    public ObservableList<User> searchUser() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            List<UserEntity> userEntities = session.createQuery("FROM UserEntity",UserEntity.class).list();
            for (UserEntity entity: userEntities){
                User user = new ModelMapper().map(entity,User.class);
                allUsers.add(user);
            }
            transaction.commit();

        }catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Failed To Find Users",e);

        }finally {
            if (session!=null){
                session.close();
            }
        }
        return allUsers;
    }

    @Override
    public boolean removeProduct(String cid) {
        return false;
    }

    @Override
    public boolean updateProduct(UserEntity entity) {
        return false;
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
    public boolean updateSupplier(UserEntity entity) {
        return false;
    }

}
