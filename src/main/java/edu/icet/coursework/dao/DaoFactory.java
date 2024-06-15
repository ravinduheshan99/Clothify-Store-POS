package edu.icet.coursework.dao;

import edu.icet.coursework.bo.custom.impl.SupplierBoImpl;
import edu.icet.coursework.dao.custom.impl.ProductDaoImpl;
import edu.icet.coursework.dao.custom.impl.SupplierDaoImpl;
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
            case SUPPLIER:
                return (T) new SupplierDaoImpl();
            case PRODUCT:
                return (T) new ProductDaoImpl();
        }
        return null;
    }
}
