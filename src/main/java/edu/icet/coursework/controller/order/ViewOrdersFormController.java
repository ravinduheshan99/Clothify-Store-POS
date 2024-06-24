package edu.icet.coursework.controller.order;

import edu.icet.coursework.controller.user.UserSession;
import edu.icet.coursework.dto.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewOrdersFormController implements Initializable {

    public TableView tblOrders;
    public TableColumn colOid;
    public TableColumn colUid;
    public TableColumn colTotal;
    public AnchorPane adminpane;
    public Label lblUserId;
    public Label lblUserType;
    public TableColumn colDiscount;
    public TableColumn colFinalTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = UserSession.getInstance().getCurrentSession();
        lblUserId.setText(currentUser.getUserId()+"-");
        lblUserType.setText(currentUser.getUserType());

        //colOid.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        //colUid.setCellValueFactory(new PropertyValueFactory<>("userId"));
        //colTotal.setCellValueFactory(new PropertyValueFactory<>(""));
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage) adminpane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/pointOfSaleForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        ((Stage) adminpane.getScene().getWindow()).close();
    }

}
