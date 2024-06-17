package edu.icet.coursework.bo;

import edu.icet.coursework.bo.custom.impl.OrderBoImpl;
import edu.icet.coursework.bo.custom.impl.ProductBoImpl;
import edu.icet.coursework.bo.custom.impl.SupplierBoImpl;
import edu.icet.coursework.bo.custom.impl.UserBoImpl;
import edu.icet.coursework.dao.custom.impl.ProductDaoImpl;
import edu.icet.coursework.util.BoType;

//Factory Design Pattern
public class BoFactory {
    //Singleton Design Pattern
    private BoFactory() {}

    private static BoFactory instance;

    public static BoFactory getInstance() {
        return instance != null ? instance : (instance = new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case USER:
                return (T) new UserBoImpl();
            case SUPPLIER:
                return (T) new SupplierBoImpl();
            case PRODUCT:
                return (T) new ProductBoImpl();
            case ORDER:
                return (T) new OrderBoImpl();
        }
        return null;
    }
}
