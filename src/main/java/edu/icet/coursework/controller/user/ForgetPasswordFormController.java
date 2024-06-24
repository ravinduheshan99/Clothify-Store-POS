package edu.icet.coursework.controller.user;

import com.jfoenix.controls.JFXTextField;
import edu.icet.coursework.util.Mail;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class ForgetPasswordFormController {
    public AnchorPane adminpane;
    public JFXTextField txtEmail;
    public JFXTextField txtOtp;

    private int otp = 0;

    public void btnRequestOtpOnAction(ActionEvent actionEvent) {
        sendEmailNotification(txtEmail.getText());
    }

    public void btnEnterOnAction(ActionEvent actionEvent) {
        try {
            if (otp == Integer.parseInt(txtOtp.getText())) {
                Stage stage = (Stage) adminpane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/resetPasswordForm.fxml"))));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect OTP. Please Try Again");
                alert.showAndWait();
                txtOtp.setText(null);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid OTP format. Please enter a valid number.");
            alert.showAndWait();
            txtOtp.setText(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void lblLoginFormNavOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) adminpane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/userLoginForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        ((Stage) adminpane.getScene().getWindow()).close();
    }

    private void sendEmailNotification(String email) {
        Mail mail = new Mail();
        otp = generateOTP();
        mail.setMsg("Your OTP for Reset Password is: " + otp + "\nThank You,\nClothify Store Support Team");
        mail.setTo(email);
        mail.setSubject("Clothify Store Password Reset OTP");

        Thread thread = new Thread(mail);
        thread.start();

        showNotification("OTP Sent", "OTP sent to your Email Thank you.");
    }

    private void showNotification(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.show();
        });
    }

    public static int generateOTP() {
        Random random = new Random();
        int otp = random.nextInt(9000000) + 1000000;
        System.out.println("Generated OTP: " + otp);
        return otp;
    }
}
