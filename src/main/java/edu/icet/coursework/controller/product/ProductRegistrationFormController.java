package edu.icet.coursework.controller.product;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.ProductBo;
import edu.icet.coursework.controller.user.UserSession;
import edu.icet.coursework.db.DBConnection;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

public class ProductRegistrationFormController implements Initializable {
    public JFXTextField txtPid;
    public JFXTextField txtProductPic;
    public JFXTextField txtProductName;
    public JFXTextField txtPrice;
    public JFXComboBox cbxCategory;
    public JFXTextField txtProductDescription;
    public Label lblProductPicName;
    public JFXComboBox cbxSize;
    public JFXTextField txtQty;
    public JFXTextField txtSid;
    public ImageView imgProductPic;
    public AnchorPane adminpane;
    public Label lblUserId;
    public Label lblUserType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = UserSession.getInstance().getCurrentSession();
        lblUserId.setText(currentUser.getUserId()+"-");
        lblUserType.setText(currentUser.getUserType());

        generateProductId();
        loadCategoryMenu();
        loadSizeMenu();
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

    private void generateProductId() {
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT COUNT(*) FROM productentity");
            Integer count = 0;
            while (resultset.next()){
                count=resultset.getInt(1);
                System.out.println(count + " count ");
            }
            if (count==0){
                txtPid.setText("P001");
            }
            String lastPid = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT productId\n" +
                    "From productentity\n" +
                    "ORDER BY productId DESC\n" +
                    "LIMIT 1;");
            if(resultSet1.next()){
                lastPid = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastPid);
                if (matcher.find()){
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    txtPid.setText(String.format("P%03d",number));
                }else {
                    new Alert(Alert.AlertType.WARNING, "Warning").show();
                }
            }

        }catch (SQLException | ClassNotFoundException e){
            throw  new RuntimeException();
        }
    }

    private void clearText() {
        txtPid.setText(null);
        txtProductName.setText(null);
        txtPrice.setText(null);
        txtQty.setText(null);
        txtProductDescription.setText(null);
        cbxSize.setValue(null);
        cbxCategory.setValue(null);
    }


    ProductBo productBoImpl = BoFactory.getInstance().getBo(BoType.PRODUCT);

    public void lblOnActionRegistrationFormMainNav(MouseEvent mouseEvent) {
    }

    public void btnChooseImageOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        Product product = new Product(txtPid.getText(),
                          txtProductName.getText(),
                          Double.parseDouble(txtPrice.getText()),
                          Integer.parseInt(txtQty.getText()),
                          txtProductDescription.getText(),
                          cbxSize.getValue().toString(),
                          cbxCategory.getValue().toString()
        );
        boolean b = productBoImpl.addProduct(product);
        if(b){
            new Alert(Alert.AlertType.CONFIRMATION,"Product Added Successfully!").show();
            clearText();
        }else {
            new Alert(Alert.AlertType.ERROR,"Operation Unsuccessfull!").show();
            clearText();
        }
    }


    public void btnRemoveOnAction(ActionEvent actionEvent) {
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

    public void btnSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

}
