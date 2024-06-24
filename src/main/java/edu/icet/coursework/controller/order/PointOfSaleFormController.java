package edu.icet.coursework.controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.OrderBo;
import edu.icet.coursework.bo.custom.ProductBo;
import edu.icet.coursework.controller.user.UserSession;
import edu.icet.coursework.db.DBConnection;
import edu.icet.coursework.dto.Order;
import edu.icet.coursework.dto.OrderDetails;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.dto.tm.TableModelCart;
import edu.icet.coursework.entity.OrderDetailsEntity;
import edu.icet.coursework.entity.OrderEntity;
import edu.icet.coursework.util.BoType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public AnchorPane adminpane;
    public Label lblUserId;
    public Label lblUserType;

    private ProductBo productBoImpl = BoFactory.getInstance().getBo(BoType.PRODUCT);
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private ObservableList<TableModelCart> cartList = FXCollections.observableArrayList();

    double discount = 0.0;
    double totalBillAmount = 0.0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = UserSession.getInstance().getCurrentSession();
        lblUserId.setText(currentUser.getUserId());
        lblUserType.setText(currentUser.getUserType());

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        loadProductIds();
        generateOrderId();
        txtCashierId.setText(lblUserId.getText());

        cbxProductId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setProductData((String) newValue);
            }
        });
    }

    private void generateOrderId() {
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT COUNT(*) FROM orderentity");
            Integer count = 0;
            while (resultset.next()){
                count=resultset.getInt(1);
                System.out.println(count + " count ");
            }
            if (count==0){
                txtOid.setText("O001");
            }
            String lastOid = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT orderId\n" +
                    "From orderentity\n" +
                    "ORDER BY orderId DESC\n" +
                    "LIMIT 1;");
            if(resultSet1.next()){
                lastOid = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOid);
                if (matcher.find()){
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    txtOid.setText(String.format("O%03d",number));
                }else {
                    new Alert(Alert.AlertType.WARNING, "Warning").show();
                }
            }

        }catch (SQLException | ClassNotFoundException e){
            throw  new RuntimeException();
        }
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

    public void btnCheckoutOnAction(ActionEvent actionEvent) {
        try {
            // Extract data from UI components
            String oid = txtOid.getText();
            String uid = txtCashierId.getText();
            LocalDate orderDate = LocalDate.now();
            LocalDateTime orderTime = LocalDateTime.now();
            String cmail = txtCustomerEmail.getText();


            // Create a list to hold OrderDetails
            List<OrderDetails> orderDetailList = new ArrayList<>();

            // Populate OrderDetails from cartList
            for (TableModelCart cartItem : cartList) {
                double total = cartItem.getTotal() - (cartItem.getTotal() * discount);
                OrderDetails orderDetails = new OrderDetails(cartItem.getProductId(), cartItem.getUnitPrice(), cartItem.getQty(), total, discount, cmail);
                orderDetailList.add(orderDetails);
            }

            // Create Order DTO
            Order order = new Order(oid, uid, orderDate, orderTime, orderDetailList, totalBillAmount);

            // Create OrderEntity and map from Order DTO
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderId(order.getOrderId());
            orderEntity.setUserId(order.getUserId());
            orderEntity.setOrderDate(order.getOrderDate());
            orderEntity.setOrderTime(order.getOrderTime());
            orderEntity.setTotalBillAmount(order.getTotalBillAmount());

            // Create OrderDetailsEntity list and map from OrderDetails DTOs
            List<OrderDetailsEntity> orderDetailsEntities = new ArrayList<>();
            for (OrderDetails orderDetails : order.getOrderDetailsList()) {
                OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();
                orderDetailsEntity.setProductId(orderDetails.getProductId());
                orderDetailsEntity.setUnitPrice(orderDetails.getUnitPrice());
                orderDetailsEntity.setQty(orderDetails.getQty());
                orderDetailsEntity.setTotal(orderDetails.getTotal());
                orderDetailsEntity.setDiscount(orderDetails.getDiscount());
                orderDetailsEntity.setCustomerEmail(orderDetails.getCustomerEmail());
                orderDetailsEntity.setOrder(orderEntity); // Set the association to OrderEntity
                orderDetailsEntities.add(orderDetailsEntity);
            }

            // Set the list of OrderDetailsEntity in OrderEntity
            orderEntity.setOrderDetailsList(orderDetailsEntities);

            // Persist the OrderEntity using your business object (orderBo)
            boolean success = orderBo.addOrder(orderEntity);

            // Handle success or failure
            if (success) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed Successfully!").show();
                for(TableModelCart cartItem: cartList){
                    Product product = productBoImpl.searchProduct(cartItem.getProductId());
                    product.setQty((product.getQty()-cartItem.getQty()));
                    productBoImpl.updateProduct(product);
                }
                clearForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Operation Unsuccessful!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error during checkout: " + e.getMessage()).show();
        }
    }

    public void btnClearCartOnAction(ActionEvent actionEvent) {
        clearForm();
    }

    public void btnCheckDiscountOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to check available discounts?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (Double.parseDouble(extractBillAmount(lblTotalBillAmount.getText()))>10000){
                    txtDiscount.setText("20%");
                    discount = Double.parseDouble(txtDiscount.getText().replace("%", "")) / 100;
                    totalBillAmount=totalBillAmount-(totalBillAmount*discount);
                    lblTotalBillAmount.setText("Total Amount : "+totalBillAmount+"0 LKR");
                } else if (Double.parseDouble(extractBillAmount(lblTotalBillAmount.getText()))>5000) {
                    txtDiscount.setText("15%");
                    discount = Double.parseDouble(txtDiscount.getText().replace("%", "")) / 100;
                    totalBillAmount=totalBillAmount-(totalBillAmount*discount);
                    lblTotalBillAmount.setText("Total Amount : "+totalBillAmount+"0 LKR");
                }else {
                    txtDiscount.setText("0%");
                    discount = Double.parseDouble(txtDiscount.getText().replace("%", "")) / 100;
                    totalBillAmount=totalBillAmount-(totalBillAmount*discount);
                    lblTotalBillAmount.setText("Total Amount : "+totalBillAmount+"0 LKR");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnViewOrdersOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) adminpane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/viewOrdersForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        ((Stage) adminpane.getScene().getWindow()).close();
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        User currentUser = UserSession.getInstance().getCurrentSession();
        if (currentUser.getUserType().equals("Admin")) {
            Stage stage = (Stage) adminpane.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/adminDashboardForm.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();
            ((Stage) adminpane.getScene().getWindow()).close();
        }
        Stage stage = (Stage) adminpane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/employeeDashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        ((Stage) adminpane.getScene().getWindow()).close();
    }


    private void calcNetTotal() {
        double netTot = cartList.stream().mapToDouble(TableModelCart::getTotal).sum();
        totalBillAmount=netTot;
        lblTotalBillAmount.setText("Total Amount : " + String.format("%.2f", netTot) + " LKR");
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

    private void clearProductDetails() {
        cbxProductId.setValue(null);
        txtProductName.clear();
        txtUnitPrice.clear();
        txtAvailableQty.clear();
        txtQtyNeed.clear();
    }

    private void clearForm() {
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
}