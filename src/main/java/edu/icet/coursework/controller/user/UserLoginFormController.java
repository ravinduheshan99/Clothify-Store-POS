package edu.icet.coursework.controller.user;

import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.UserBo;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.util.BoType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class UserLoginFormController {

    public JFXTextField txtEmail;
    public JFXTextField txtPw;

    UserBo userBoImpl = BoFactory.getInstance().getBo(BoType.USER);

    public void lblForgetPasswordOnAction(MouseEvent mouseEvent) {
    }

    public void lblRegistrationFormNavOnAction(MouseEvent mouseEvent) {
    }

    public void lblRegistrationFormMainNavOnAction(MouseEvent mouseEvent) {
    }

    public void checkboxShowPasswordOnAction(ActionEvent actionEvent) {
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        try {
            String un = txtEmail.getText();
            String pw = txtPw.getText();
            if (un.isEmpty() || pw.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please Check Your Login Credentials").show();
                clearFields();
            } else {
                boolean isFound = false;
                ObservableList<User> users = userBoImpl.searchUser();
                for (User user : users) {
                    if (user.getEmail().equals(un) && user.getPassword().equals(pw)) {
                        new Alert(Alert.AlertType.INFORMATION, "Login Successfully").show();
                        clearFields();
                        isFound = true;
                        break; // Break the loop once the user is found
                    }
                }
                if (!isFound) {
                    new Alert(Alert.AlertType.INFORMATION, "User Not Found").show();
                    clearFields();
                }
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtEmail.setText(null);
        txtPw.setText(null);
    }

}

