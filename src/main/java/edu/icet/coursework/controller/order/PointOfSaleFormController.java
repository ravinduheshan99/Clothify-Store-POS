package edu.icet.coursework.controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.OrderBo;
import edu.icet.coursework.bo.custom.ProductBo;
import edu.icet.coursework.db.DBConnection;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.OrderDetails;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.tm.TableModelCart;
import edu.icet.coursework.util.BoType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PointOfSaleFormController implements Initializable {
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
    public TableColumn colProductName;
    public Label lblDate;
    public Label lblTime;

    private ProductBo productBoImpl = BoFactory.getInstance().getBo(BoType.PRODUCT);
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private ObservableList<TableModelCart> cartList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        loadProductIds();

        cbxProductId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setProductData((String) newValue);
            }
        });
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(fmt.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(String.format("%02d : %02d : %02d", time.getHour(), time.getMinute(), time.getSecond()));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadProductIds() {
        ObservableList<Product> allProducts = productBoImpl.loadProducts();
        ObservableList<String> productIds = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            productIds.add(product.getProductId());
        }
        cbxProductId.setItems(productIds);
    }

    private void setProductData(String pid) {
        Product product = productBoImpl.searchProduct(pid);
        if (product != null) {
            txtProductName.setText(product.getProductName());
            txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
            txtAvailableQty.setText(String.valueOf(product.getQty()));
        }
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        try {
            // Check if ComboBox and TextFields are initialized
            if (cbxProductId.getValue() == null || txtProductName.getText() == null || txtUnitPrice.getText() == null ||
                    txtAvailableQty.getText() == null || txtQtyNeed.getText() == null) {
                throw new NullPointerException("One or more UI components are not initialized.");
            }

            // Check if ComboBox has a value
            if (cbxProductId.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Product ID is required!").show();
                return;
            }

            String pid = cbxProductId.getValue().toString();
            String productName = txtProductName.getText();

            if (productName == null || productName.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Product Name is required!").show();
                return;
            }

            double unitPrice;
            try {
                unitPrice = Double.parseDouble(txtUnitPrice.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.WARNING, "Invalid Unit Price!").show();
                return;
            }

            int availableQty;
            try {
                availableQty = Integer.parseInt(txtAvailableQty.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.WARNING, "Invalid Available Quantity!").show();
                return;
            }

            int needQty;
            try {
                needQty = Integer.parseInt(txtQtyNeed.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.WARNING, "Invalid Needed Quantity!").show();
                return;
            }

            double total = needQty * unitPrice;

            if (needQty > availableQty) {
                new Alert(Alert.AlertType.WARNING, "Available Quantity Exceeded!").show();
                txtQtyNeed.setText(null);
            } else {
                TableModelCart cart = new TableModelCart(pid, productName, needQty, unitPrice, total);
                cartList.add(cart);
                tblProductCart.setItems(cartList);
                calcNetTotal();
                clearProductDetails();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error adding to cart: " + e.getMessage()).show();
        }
    }

    private void calcNetTotal() {
        double netTot = cartList.stream().mapToDouble(TableModelCart::getTotal).sum();
        lblTotalBillAmount.setText("Total Amount : " + String.format("%.2f", netTot) + " LKR");
    }

    private void clearProductDetails() {
        cbxProductId.setValue(null);
        txtProductName.clear();
        txtUnitPrice.clear();
        txtAvailableQty.clear();
        txtQtyNeed.clear();
    }

    public void btnCheckoutOnAction(ActionEvent actionEvent) {
        try {
            // Ensure order ID is manually set
            String oid = txtOid.getText();
            if (oid == null || oid.trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Order ID is required!").show();
                return;
            }

            String uid = txtCashierId.getText();
            LocalDate orderDate = LocalDate.now();
            LocalDateTime orderTime = LocalDateTime.now();

            double discount = 0.0;
            String cmail = txtCustomerEmail.getText();
            double totalBillAmount = Double.parseDouble(extractBillAmount(lblTotalBillAmount.getText()));

            if (totalBillAmount > 10000) {
                discount = 0.15;
                totalBillAmount = totalBillAmount - (totalBillAmount * discount);
            }

            List<OrderDetails> orderDetailList = new ArrayList<>();
            for (TableModelCart cartItem : cartList) {
                OrderDetails orderDetails = new OrderDetails(oid, cartItem.getProductId(), cartItem.getUnitPrice(), cartItem.getQty(), cartItem.getTotal(), discount, cmail);
                orderDetailList.add(orderDetails);
            }

            Order order = new Order(oid, uid, orderDate, orderTime, orderDetailList, totalBillAmount);
            boolean success = orderBo.addOrder(order);
            if (success) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed Successfully!").show();
                clearForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Operation Unsuccessful!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error placing order: " + e.getMessage()).show();
        }
    }

    private void clearForm() {
        txtOid.clear();
        txtCashierId.clear();
        cbxProductId.setValue(null);
        txtProductName.clear();
        txtUnitPrice.clear();
        txtAvailableQty.clear();
        txtQtyNeed.clear();
        txtDiscount.clear();
        txtCustomerEmail.clear();
        lblTotalBillAmount.setText("Total Amount : 0.00 LKR");
        cartList.clear();
        tblProductCart.setItems(cartList);
        calcNetTotal();
    }

    private String extractBillAmount(String text) {
        Pattern pattern = Pattern.compile("\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "0.00";
        }
    }

    public void btnClearCartOnAction(ActionEvent actionEvent) {
        cartList.clear();
        tblProductCart.setItems(cartList);
        calcNetTotal();
    }

    public void btnCheckDiscountOnAction(ActionEvent actionEvent) {
        if (Double.parseDouble(extractBillAmount(lblTotalBillAmount.getText())) > 10000) {
            txtDiscount.setText("15%");
            new Alert(Alert.AlertType.INFORMATION, "Discount Added Successfully").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "No Discount Available For This Product").show();
            txtDiscount.setText("0%");
        }
    }

    public void btnViewOrdersOnAction(ActionEvent actionEvent) {
        // Implement order viewing functionality
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        // Implement back navigation functionality
    }
}