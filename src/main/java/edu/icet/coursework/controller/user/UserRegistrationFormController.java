package edu.icet.coursework.controller.user;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.UserBo;
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
import javafx.scene.control.DatePicker;
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

public class UserRegistrationFormController implements Initializable {
    public JFXTextField txtUid;
    public JFXTextField txtFname;
    public JFXTextField txtLname;
    public JFXComboBox cbxUserType;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNo;
    public JFXTextField txtEmail;
    public JFXComboBox cbxGender;
    public DatePicker DtpDob;
    public AnchorPane adminpane;
    public Label lblUserId;
    public Label lblUserType;
    public JFXPasswordField txtPw;
    public JFXPasswordField txtPwConfirm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = UserSession.getInstance().getCurrentSession();
        lblUserId.setText(currentUser.getUserId()+"-");
        lblUserType.setText(currentUser.getUserType());

        loadUserTypesMenu();
        loadGendersMenu();
        generateUserId();
    }


    private void loadUserTypesMenu(){
        ObservableList<Object> userTypes = FXCollections.observableArrayList();
        userTypes.add("Admin");
        userTypes.add("Employee");
        cbxUserType.setItems(userTypes);
    }

    private void loadGendersMenu(){
        ObservableList<Object> genders = FXCollections.observableArrayList();
        genders.add("Male");
        genders.add("Female");
        cbxGender.setItems(genders);
    }

    private void generateUserId() {
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT COUNT(*) FROM userentity");
            Integer count = 0;
            while (resultset.next()){
                count=resultset.getInt(1);
                System.out.println(count + " count ");
            }
            if (count==0){
                txtUid.setText("U001");
            }
            String lastUid = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT userId\n" +
                    "From userentity\n" +
                    "ORDER BY userId DESC\n" +
                    "LIMIT 1;");
            if(resultSet1.next()){
                lastUid = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastUid);
                if (matcher.find()){
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    txtUid.setText(String.format("U%03d",number));
                }else {
                    new Alert(Alert.AlertType.WARNING, "Warning").show();
                }
            }

        }catch (SQLException | ClassNotFoundException e){
            throw  new RuntimeException();
        }
    }

    private void clearText() {
        txtUid.setText(null);
        txtFname.setText(null);
        txtLname.setText(null);
        cbxUserType.setValue(null);
        txtAddress.setText(null);
        txtContactNo.setText(null);
        txtEmail.setText(null);
        txtPwConfirm.setText(null);
        cbxGender.setValue(null);
        DtpDob.setValue(null);
        txtPw.setText(null);
    }


    private UserBo userBoImpl = BoFactory.getInstance().getBo(BoType.USER);


    public void btnRegisterOnAction(ActionEvent actionEvent) {
        User user = new User(txtUid.getText(),
                        cbxUserType.getValue().toString(),
                        txtFname.getText(),
                        txtLname.getText(),
                        DtpDob.getValue(),
                        cbxGender.getValue().toString(),
                        txtAddress.getText(),
                        txtContactNo.getText(),
                        txtEmail.getText(),
                        txtPw.getText(),
                        txtPwConfirm.getText());

        boolean b = userBoImpl.addUser(user);
        if(b){
            new Alert(Alert.AlertType.CONFIRMATION,"User Added Successfully!").show();
            clearText();
            generateUserId();
        }else {
            new Alert(Alert.AlertType.ERROR,"Operation Unsuccessfull!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String pw = userBoImpl.searchUserById(txtUid.getText()).getPassword();
        String pwCfrm = userBoImpl.searchUserById(txtUid.getText()).getPasswordConfirm();
        User user = new User(txtUid.getText(),
                cbxUserType.getValue().toString(),
                txtFname.getText(),
                txtLname.getText(),
                DtpDob.getValue(),
                cbxGender.getValue().toString(),
                txtAddress.getText(),
                txtContactNo.getText(),
                txtEmail.getText(),
                pw,
                pwCfrm);
        boolean b = userBoImpl.updateUser(user);
        if(b){
            new Alert(Alert.AlertType.CONFIRMATION,"User Updated Successfully!").show();
            clearText();
            generateUserId();
        }else {
            new Alert(Alert.AlertType.ERROR,"Operation Unsuccessfull!").show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        User user = userBoImpl.searchUserById(txtUid.getText());
        if(user==null){
            new Alert(Alert.AlertType.WARNING,"User Not Found!").show();
            clearText();
        }else {
            cbxUserType.setValue(user.getUserType());
            txtFname.setText(user.getFname());
            txtLname.setText(user.getLname());
            cbxGender.setValue(user.getGender());
            DtpDob.setValue(user.getDob());
            txtAddress.setText(user.getAddress());
            txtContactNo.setText(user.getContactNo());
            txtEmail.setText(user.getEmail());
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        boolean b = userBoImpl.removeUser(txtUid.getText());
        if(b){
            new Alert(Alert.AlertType.CONFIRMATION,"User Removed Successfully!").show();
            clearText();
            generateUserId();
        }else {
            new Alert(Alert.AlertType.ERROR,"Operation Unsuccessfull!").show();
            clearText();
            generateUserId();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage) adminpane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/adminDashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        ((Stage) adminpane.getScene().getWindow()).close();
    }

}
