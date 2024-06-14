package edu.icet.coursework.dao.custom.impl;

import edu.icet.coursework.dao.custom.UserDao;
import edu.icet.coursework.entity.UserEntity;
import edu.icet.coursework.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
}
