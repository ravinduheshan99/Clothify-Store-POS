package edu.icet.coursework.controller.supplier;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.SupplierBo;
import edu.icet.coursework.controller.user.UserSession;
import edu.icet.coursework.db.DBConnection;
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

public class SupplierRegistrationFormController implements Initializable {
    public JFXTextField txtSid;
    public JFXTextField txtCompanyEmail;
    public JFXTextField txtCompanyName;
    public AnchorPane adminpane;
    public Label lblUserId;
    public Label lblUserType;
    public JFXTextField txtProductDescription;
    public JFXTextField txtCompanyContactNumber;
    public JFXComboBox cbxCategory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        User currentUser = UserSession.getInstance().getCurrentSession();
        lblUserId.setText(currentUser.getUserId());
        lblUserType.setText(currentUser.getUserType());

        loadCategoryMenu();
        generateSupplierId();
    }


    private void loadCategoryMenu() {
        ObservableList<Object> categories = FXCollections.observableArrayList();
        categories.add("Shirts");
        categories.add("T-shirts");
        categories.add("Jersy");
        categories.add("Shorts");
        categories.add("Denims");
        categories.add("Trousers");
        categories.add("Bottoms");
        cbxCategory.setItems(categories);
    }

    private void generateSupplierId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT COUNT(*) FROM supplierentity");
            Integer count = 0;
            while (resultset.next()) {
                count = resultset.getInt(1);
                System.out.println(count + " count ");
            }
            if (count == 0) {
                txtSid.setText("S001");
            }
            String lastSid = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT supplierId\n" +
                    "From supplierentity\n" +
                    "ORDER BY supplierId DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastSid = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastSid);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    txtSid.setText(String.format("S%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "Warning").show();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    private void clearForm() {
        txtSid.setText(null);
        txtCompanyName.setText(null);
        cbxCategory.setValue(null);
        txtCompanyContactNumber.setText(null);
        txtCompanyEmail.setText(null);
        txtProductDescription.setText(null);
    }


    private SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);


    public void btnAddOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(txtSid.getText(),
                txtCompanyName.getText(),
                cbxCategory.getValue().toString(),
                txtCompanyContactNumber.getText(),
                txtCompanyEmail.getText(),
                txtProductDescription.getText()
        );
        boolean b = supplierBo.addSupplier(supplier);
        if (b) {
            clearForm();
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added Successfully!").show();
            generateSupplierId();
        } else {
            new Alert(Alert.AlertType.ERROR, "Operation Unsuccessfull!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(txtSid.getText(),
                txtCompanyName.getText(),
                cbxCategory.getValue().toString(),
                txtCompanyContactNumber.getText(),
                txtCompanyEmail.getText(),
                txtProductDescription.getText()
        );
        boolean b = supplierBo.updateSupplier(supplier);
        if (b) {
            clearForm();
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated Successfully!").show();
            generateSupplierId();
        } else {
            new Alert(Alert.AlertType.ERROR, "Supplier Not Updated!").show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Supplier supplier = supplierBo.searchSupplier(txtSid.getText());
        if (supplier == null) {
            new Alert(Alert.AlertType.WARNING, "Supplier Not Found!").show();
            clearForm();
        } else {
            txtCompanyName.setText(supplier.getCompanyName());
            cbxCategory.setValue(supplier.getProductCategory());
            txtCompanyContactNumber.setText(supplier.getCompanyContactNumber());
            txtCompanyEmail.setText(supplier.getCompanyEmail());
            txtProductDescription.setText(supplier.getProductDescription());
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure. Do you want to remove this supplier?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean b = supplierBo.removeSupplier(txtSid.getText());
                if (b) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Removed Successfully!").show();
                    clearForm();
                    generateSupplierId();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Operation Unsuccessfull!").show();
                    clearForm();
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
