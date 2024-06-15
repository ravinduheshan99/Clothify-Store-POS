package edu.icet.coursework.dao.custom.impl;

import edu.icet.coursework.dao.custom.ProductDao;
import edu.icet.coursework.entity.ProductEntity;
import edu.icet.coursework.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
}
