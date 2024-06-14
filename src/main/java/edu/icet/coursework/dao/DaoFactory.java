package edu.icet.coursework.dao;

import edu.icet.coursework.dao.custom.impl.UserDaoImpl;
import edu.icet.coursework.util.DaoType;

public class DaoFactory {
    //Sigleton Design Patter
    private DaoFactory() {}

    private static DaoFactory instance;

    public static DaoFactory getInstance() {
        return instance != null ? instance : (instance = new DaoFactory());
    }

    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case USER:
                return (T)new UserDaoImpl();
        }
        return null;
    }
}
