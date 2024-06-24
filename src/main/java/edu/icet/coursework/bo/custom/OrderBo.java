package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.entity.OrderEntity;

public interface OrderBo extends SuperBo {
    public boolean addOrder(OrderEntity dto);
}
