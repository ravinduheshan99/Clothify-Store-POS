package edu.icet.coursework.controller.user;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.UserBo;
import edu.icet.coursework.db.DBConnection;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    public JFXTextField txtPwConfirm;
    public JFXComboBox cbxGender;
    public DatePicker DtpDob;
    public JFXTextField txtPw;
    public ImageView imgProfilePic;
    public JFXTextField txtProPic;
    public Label lblProPicName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    private UserBo userBoImpl = BoFactory.getInstance().getBo(BoType.USER);

    public void btnChooseImageOnAction(ActionEvent actionEvent) {
    }

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
    }

}
