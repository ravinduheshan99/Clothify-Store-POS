package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.Product;

public interface OrderBo extends SuperBo {
    public boolean addOrder(Order order);
}
