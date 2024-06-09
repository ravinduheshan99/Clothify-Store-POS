package edu.icet.coursework.controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PointOfSaleFormController {
    public JFXTextField txtOid;
    public JFXTextField txtCashierId;
    public JFXTextField txtAvailableQty;
    public JFXComboBox cbxProductId;
    public JFXTextField txtQtyNeed;
    public JFXTextField txtProductName;
    public JFXTextField txtUnitPrice;
    public TableView tblProductCart;
    public TableColumn colProductId;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public JFXTextField txtCustomerEmail;
    public JFXTextField txtDiscount;
    public Label lblTotalBillAmount;

    public void btnViewOrdersOnAction(ActionEvent actionEvent) {
    }

    public void btnCheckoutOnAction(ActionEvent actionEvent) {
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
    }

    public void btnClearCartOnAction(ActionEvent actionEvent) {
    }
}
