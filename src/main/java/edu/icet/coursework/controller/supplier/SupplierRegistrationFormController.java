package edu.icet.coursework.controller.supplier;

import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.SupplierBo;
import edu.icet.coursework.db.DBConnection;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
    }

    public void lblRegistrationFormMainNavOnAction(MouseEvent mouseEvent) {
    }
    
}
