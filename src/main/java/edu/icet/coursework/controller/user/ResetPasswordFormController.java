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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;


public class ResetPasswordFormController {
    public JFXTextField txtEmail;
    public AnchorPane adminpane;
    public JFXPasswordField txtNewPw;
    public JFXPasswordField txtNewPwConfirm;

    private UserBo userBoImpl = BoFactory.getInstance().getBo(BoType.USER);

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtEmail.setText(null);
        txtNewPw.setText(null);
        txtNewPwConfirm.setText(null);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String newPw = txtNewPw.getText();
        String newPwCfrm = txtNewPwConfirm.getText();

        boolean userFound = false;
        ObservableList<User> users = userBoImpl.searchUser();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                userFound = true;
                if (!newPw.equals(user.getPassword()) && newPw.equals(newPwCfrm)) {
                    user.setPassword(newPw);
                    user.setPasswordConfirm(newPwCfrm);
                    userBoImpl.updateUser(user);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Password updated successfully.");
                    successAlert.showAndWait();
                    clearAllFields();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "You can't use your old passwords here. Please check your password fields and retry.");
                    errorAlert.showAndWait();
                    clearPasswordFields();
                }
                break;
            }
        }

        if (!userFound) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Please enter a valid user email.");
            errorAlert.showAndWait();
            clearAllFields();
        }
    }

    private void clearAllFields() {
        txtEmail.setText(null);
        txtNewPw.setText(null);
        txtNewPwConfirm.setText(null);
    }

    private void clearPasswordFields() {
        txtNewPw.setText(null);
        txtNewPwConfirm.setText(null);
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure. Do You Want To Exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Stage stage = (Stage) adminpane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/userLoginForm.fxml"))));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
