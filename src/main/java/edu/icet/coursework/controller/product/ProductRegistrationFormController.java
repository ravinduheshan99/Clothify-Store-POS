package edu.icet.coursework.controller.product;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.ProductBo;
import edu.icet.coursework.bo.custom.SupplierBo;
import edu.icet.coursework.controller.user.UserSession;
import edu.icet.coursework.db.DBConnection;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProductRegistrationFormController implements Initializable {
    public JFXTextField txtPid;
    public JFXTextField txtProductName;
    public JFXTextField txtPrice;
    public JFXTextField txtProductDescription;
    public JFXComboBox cbxSize;
    public JFXTextField txtQty;
    public AnchorPane adminpane;
    public Label lblUserId;
    public Label lblUserType;
    public JFXTextField txtProductCategory;
    public JFXComboBox cbxSupplierId;
    public Label lblUserName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = UserSession.getInstance().getCurrentSession();
        lblUserId.setText(currentUser.getUserId());
        lblUserType.setText(currentUser.getUserType());
        lblUserName.setText(currentUser.getFname());

        generateProductId();
        loadSupplierIdMenu();
        loadSizeMenu();

        cbxSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setSupplierData((String) newValue);
            }
        });
    }


    ProductBo productBoImpl = BoFactory.getInstance().getBo(BoType.PRODUCT);
    SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);


    private void setSupplierData(String sid) {
        Supplier supplier = supplierBo.searchSupplier(sid);
        if (supplier != null) {
            txtProductCategory.setText(supplier.getProductCategory());
            txtProductDescription.setText(supplier.getProductDescription());
        }
    }

    private void loadSupplierIdMenu() {
        ObservableList<Supplier> suppliers = supplierBo.searchAllSuppliers();
        ObservableList<Object> supplierIds = FXCollections.observableArrayList();

        for (Supplier supplier:suppliers){
            supplierIds.add(supplier.getSupplierId());
        }

        cbxSupplierId.setItems(supplierIds);
    }

    private void loadSizeMenu() {
        ObservableList<Object> sizes = FXCollections.observableArrayList();
        sizes.add("XS");
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");
        sizes.add("XL");
        sizes.add("XXL");
        cbxSize.setItems(sizes);
    }

    private void generateProductId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT COUNT(*) FROM productentity");
            Integer count = 0;
            while (resultset.next()) {
                count = resultset.getInt(1);
                System.out.println(count + " count ");
            }
            if (count == 0) {
                txtPid.setText("P001");
            }
            String lastPid = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT productId\n" +
                    "From productentity\n" +
                    "ORDER BY productId DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastPid = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastPid);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    txtPid.setText(String.format("P%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "Warning").show();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    private void clearForm() {
        txtPid.setText(null);
        cbxSupplierId.setValue(null);
        txtProductCategory.setText(null);
        txtProductName.setText(null);
        txtPrice.setText(null);
        txtQty.setText(null);
        txtProductDescription.setText(null);
        cbxSize.setValue(null);
    }


    public void btnAddOnAction(ActionEvent actionEvent) {
        Product product = new Product(txtPid.getText(),
                cbxSupplierId.getValue().toString(),
                txtProductCategory.getText(),
                txtProductName.getText(),
                cbxSize.getValue().toString(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText()),
                txtProductDescription.getText()
        );
        boolean b = productBoImpl.addProduct(product);
        if (b) {
            new Alert(Alert.AlertType.CONFIRMATION, "Product Added Successfully!").show();
            clearForm();
            generateProductId();
        } else {
            new Alert(Alert.AlertType.ERROR, "Operation Unsuccessfull!").show();
            clearForm();
            generateProductId();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Product product = new Product(txtPid.getText(),
                cbxSupplierId.getValue().toString(),
                txtProductCategory.getText(),
                txtProductName.getText(),
                cbxSize.getValue().toString(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText()),
                txtProductDescription.getText()
        );
        boolean b = productBoImpl.updateProduct(product);
        if (b) {
            new Alert(Alert.AlertType.CONFIRMATION, "Product Updated Successfully!").show();
            clearForm();
            generateProductId();
        } else {
            new Alert(Alert.AlertType.ERROR, "Product Not Updated!").show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Product product = productBoImpl.searchProduct(txtPid.getText());
        if (product == null) {
            new Alert(Alert.AlertType.WARNING, "Product Not Found!").show();
            clearForm();
        } else {
            cbxSupplierId.setValue(product.getSupplierId());
            txtProductCategory.setText(product.getCategory());
            txtProductName.setText(product.getProductName());
            txtPrice.setText(product.getUnitPrice().toString());
            cbxSize.setValue(product.getSize());
            txtQty.setText(product.getQty().toString());
            txtProductDescription.setText(product.getDescription());
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure. Do you want to remove this product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean b = productBoImpl.removeProduct(txtPid.getText());
                if (b) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Product Removed Successfully!").show();
                    clearForm();
                    generateProductId();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Operation Unsuccessfull!").show();
                    clearForm();
                    generateProductId();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearForm();
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

}
