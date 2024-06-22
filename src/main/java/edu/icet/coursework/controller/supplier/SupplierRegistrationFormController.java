package edu.icet.coursework.controller.supplier;

import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.SupplierBo;
import edu.icet.coursework.controller.user.UserSession;
import edu.icet.coursework.db.DBConnection;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierRegistrationFormController implements Initializable {
    public JFXTextField txtSid;
    public JFXTextField txtPid;
    public JFXTextField txtProductName;
    public JFXTextField txtCompanyEmail;
    public JFXTextField txtCompanyName;
    public AnchorPane adminpane;
    public Label lblUserId;
    public Label lblUserType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = UserSession.getInstance().getCurrentSession();
        lblUserId.setText(currentUser.getUserId()+"-");
        lblUserType.setText(currentUser.getUserType());

        generateSupplierId();
    }

    private void generateSupplierId() {
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT COUNT(*) FROM supplierentity");
            Integer count = 0;
            while (resultset.next()){
                count=resultset.getInt(1);
                System.out.println(count + " count ");
            }
            if (count==0){
                txtSid.setText("S001");
            }
            String lastSid = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT supplierId\n" +
                    "From supplierentity\n" +
                    "ORDER BY supplierId DESC\n" +
                    "LIMIT 1;");
            if(resultSet1.next()){
                lastSid = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastSid);
                if (matcher.find()){
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    txtSid.setText(String.format("S%03d",number));
                }else {
                    new Alert(Alert.AlertType.WARNING, "Warning").show();
                }
            }

        }catch (SQLException | ClassNotFoundException e){
            throw  new RuntimeException();
        }
    }

    private void clearText() {
        txtSid.setText(null);
        txtPid.setText(null);
        txtProductName.setText(null);
        txtCompanyName.setText(null);
        txtCompanyEmail.setText(null);
    }

    private SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);

    public void lblOnActionRegistrationFormMainNav(MouseEvent mouseEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Supplier supplier = supplierBo.searchSupplier(txtSid.getText());
        if(supplier==null){
            new Alert(Alert.AlertType.WARNING,"Supplier Not Found!").show();
            clearText();
        }else {
            txtPid.setText(supplier.getProductId());
            txtProductName.setText(supplier.getProductName());
            txtCompanyName.setText(supplier.getCompanyName());
            txtCompanyEmail.setText(supplier.getCompanyEmail());
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(txtSid.getText(),
                            txtPid.getText(),
                            txtProductName.getText(),
                            txtCompanyName.getText(),
                            txtCompanyEmail.getText());
        boolean b  = supplierBo.addSupplier(supplier);
        if(b){
            clearText();
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier Added Successfully!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Operation Unsuccessfull!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(txtSid.getText(),
                txtPid.getText(),
                txtProductName.getText(),
                txtCompanyName.getText(),
                txtCompanyEmail.getText());
        boolean b = supplierBo.updateSupplier(supplier);
        if (b) {
            clearText();
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated Successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Supplier Not Updated!").show();
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        boolean b = supplierBo.removeSupplier(txtSid.getText());
        if(b){
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier Removed Successfully!").show();
            clearText();
        }else {
            new Alert(Alert.AlertType.ERROR,"Operation Unsuccessfull!").show();
            clearText();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        User currentUser = UserSession.getInstance().getCurrentSession();
        if (currentUser.getUserType().equals("Admin")){
            Stage stage=(Stage) adminpane.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/adminDashboardForm.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();
            ((Stage) adminpane.getScene().getWindow()).close();
        }
        Stage stage=(Stage) adminpane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/employeeDashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        ((Stage) adminpane.getScene().getWindow()).close();
    }

}
