package edu.icet.coursework.controller.user;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class UserRegistrationFormController {
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


    public void btnChooseImageOnAction(ActionEvent actionEvent) {
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
    }

}
