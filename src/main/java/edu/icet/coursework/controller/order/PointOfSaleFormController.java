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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));

        loadDateAndTime();
        //generateOrderId();
        loadProductIds();
        cbxProductId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setProductData((String) newValue);
        });
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(fmt.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(time.getHour() + " : " + time.getMinute() + " : " + time.getSecond());
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private ProductBo productBoImpl = BoFactory.getInstance().getBo(BoType.PRODUCT);

    private void loadProductIds() {
        ObservableList<Product> allProducts = productBoImpl.loadProducts();
        ObservableList<String> productIds = FXCollections.observableArrayList();
        allProducts.forEach(product -> {
            productIds.add(product.getProductId());
        });
        cbxProductId.setItems(productIds);
    }

    private void setProductData(String pid) {
        Product product = productBoImpl.searchProduct(pid);
        txtProductName.setText(product.getProductName());
        txtUnitPrice.setText(product.getUnitPrice() + "0");
        txtAvailableQty.setText(product.getQty() + "");
    }

    private void generateOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT COUNT(*) FROM orderentity");
            Integer count = 0;
            while (resultset.next()) {
                count = resultset.getInt(1);
                System.out.println(count + " count ");
            }
            if (count == 0) {
                txtOid.setText("O001");
            }
            String lastOid = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT orderId\n" +
                    "From orderentity\n" +
                    "ORDER BY orderId DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOid = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOid);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    txtOid.setText(String.format("O%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "Warning").show();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    private void clearProductDetails() {
        cbxProductId.setValue(null);
        txtProductName.setText(null);
        txtUnitPrice.setText(null);
        txtAvailableQty.setText(null);
        txtQtyNeed.setText(null);
    }

    private void calcNetTotal() {
        Double netTot = 0.0;
        for (TableModelCart cartObject : cartList) {
            netTot += cartObject.getTotal();
        }
        lblTotalBillAmount.setText("Total Amount : " + netTot + "0 LKR");
    }

    private String extractBillAmount(String text) {
        // Define the regex pattern to match the numeric part
        Pattern pattern = Pattern.compile("\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(text);

        // Find and return the matched numeric value
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "No match found";
        }
    }

    private void clearForm() {
        txtOid.setText(null);
        txtCashierId.setText(null);
        cbxProductId.setValue(null);
        txtProductName.setText(null);
        txtUnitPrice.setText(null);
        txtAvailableQty.setText(null);
        txtQtyNeed.setText(null);
        txtDiscount.setText(null);
        txtCustomerEmail.setText(null);
        lblTotalBillAmount.setText("Total Amount : 0.00 LKR");
        cartList.clear();
        tblProductCart.setItems(cartList);
        calcNetTotal();
    }


    ObservableList<TableModelCart> cartList = FXCollections.observableArrayList();


    public void btnAddToCartOnAction(ActionEvent actionEvent) throws ParseException {
        String pid = cbxProductId.getValue().toString();
        String productName = txtProductName.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int availableQty = Integer.parseInt(txtAvailableQty.getText());
        int needQty = Integer.parseInt(txtQtyNeed.getText());
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
    }

    public void btnClearCartOnAction(ActionEvent actionEvent) {
        cartList.clear();
        tblProductCart.setItems(cartList);
        calcNetTotal();
    }


    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);


    public void btnCheckoutOnAction(ActionEvent actionEvent) {
        try {
            String oid = txtOid.getText();
            String uid = txtCashierId.getText();

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = format.parse(lblDate.getText());

            LocalTime time = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String orderTime = time.format(formatter);

            Double discount = 0.0;
            String cmail = txtCustomerEmail.getText();
            Double totalBillAmount = Double.parseDouble(extractBillAmount(lblTotalBillAmount.getText()));

            if (totalBillAmount>10000){
                discount=0.15;
                totalBillAmount=totalBillAmount-(totalBillAmount*discount);
            }

            List<OrderDetails> orderDetailList = new ArrayList<>();
            for (TableModelCart cartItem: cartList){
                OrderDetails orderDetails = new OrderDetails(oid, cartItem.getProductId(), cartItem.getUnitPrice(), cartItem.getQty(), cartItem.getTotal(), discount, cmail);
                orderDetailList.add(orderDetails);
            }

            Order order = new Order(oid,uid,orderDate,orderTime,orderDetailList,totalBillAmount);
            boolean b = orderBo.addOrder(order);
            if(b){
                new Alert(Alert.AlertType.CONFIRMATION,"Order Placed Successfully!").show();
                clearForm();
            }else {
                new Alert(Alert.AlertType.ERROR,"Operation Unsuccessfull!").show();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnViewOrdersOnAction(ActionEvent actionEvent) {
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
    }

    public void btnCheckDiscountOnAction(ActionEvent actionEvent) {
        if (Double.parseDouble(extractBillAmount(lblTotalBillAmount.getText()))>10000){
            txtDiscount.setText("15%");
            new Alert(Alert.AlertType.INFORMATION,"Discount Added Successfully").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"No Discount Available For This Product").show();
            txtDiscount.setText("0%");
        }
    }
}