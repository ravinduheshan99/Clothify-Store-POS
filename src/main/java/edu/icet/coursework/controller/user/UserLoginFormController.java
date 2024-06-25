package edu.icet.coursework.controller.user;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.UserBo;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.util.BoType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class UserLoginFormController implements Initializable{

    public JFXTextField txtEmail;
    public JFXPasswordField txtPw;
    public AnchorPane adminPane;
    public CheckBox checkBoxShowPw;
    public JFXTextField txtPwShow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPwShow.textProperty().bindBidirectional(txtPw.textProperty());

        checkBoxShowPw.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                txtPwShow.setVisible(true);
                txtPw.setVisible(false);
            } else {
                txtPwShow.setVisible(false);
                txtPw.setVisible(true);
            }
        });
    }


    UserBo userBoImpl = BoFactory.getInstance().getBo(BoType.USER);


    public void lblForgetPasswordOnAction(MouseEvent mouseEvent) {
        Stage stage=(Stage) adminPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/forgetPasswordForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        ((Stage) adminPane.getScene().getWindow()).close();
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        try {
            String un = txtEmail.getText();
            String pw = txtPw.getText();
            if (un == null || pw == null) {
                new Alert(Alert.AlertType.ERROR, "Please Check Your Login Credentials").show();
                clearFields();
            } else {
                boolean isFound = false;
                ObservableList<User> users = userBoImpl.searchUser();
                for (User user : users) {
                    if (user.getEmail().equals(un) && user.getPassword().equals(pw)) {
                        String userType = user.getUserType();
                        UserSession.getInstance().CurrentSession(user);
                        new Alert(Alert.AlertType.INFORMATION, "Login Successfully").show();
                        clearFields();
                        isFound = true;
                        navigateToDashboard(userType);
                        break; // Break the loop once the user is found
                    }
                }
                if (!isFound) {
                    new Alert(Alert.AlertType.INFORMATION, "User Not Found").show();
                    clearFields();
                }
            }

        } catch (Exception e) {

        }
    }


    private void navigateToDashboard(String userType) {
        if (userType.equals("Admin")){
            Stage stage=(Stage) adminPane.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/adminDashboardForm.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();
            ((Stage) adminPane.getScene().getWindow()).close();
        }
        Stage stage=(Stage) adminPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/employeeDashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        ((Stage) adminPane.getScene().getWindow()).close();
    }

    private void clearFields() {
        txtEmail.setText(null);
        txtPw.setText(null);
    }

}

