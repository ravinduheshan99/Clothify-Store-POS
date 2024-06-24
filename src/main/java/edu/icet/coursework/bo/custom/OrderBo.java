package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.entity.OrderEntity;
import javafx.collections.ObservableList;

public interface OrderBo extends SuperBo {
    public boolean addOrder(OrderEntity dto);
    public ObservableList<Order> searchAllOrders();
}
