package edu.icet.coursework.controller.user;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgetPasswordFormController {
    public AnchorPane adminpane;
    public JFXTextField txtEmail;
    public JFXTextField txtOtp;


    public void btnRequestOtpOnAction(ActionEvent actionEvent) {
    }

    public void btnEnterOnAction(ActionEvent actionEvent) {
    }

    public void lblLoginFormNavOnAction(MouseEvent mouseEvent) {
        Stage stage=(Stage) adminpane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/userLoginForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        ((Stage) adminpane.getScene().getWindow()).close();
    }
}
