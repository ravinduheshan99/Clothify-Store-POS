package edu.icet.coursework.controller.report;

import edu.icet.coursework.bo.BoFactory;
import edu.icet.coursework.bo.custom.ProductBo;
import edu.icet.coursework.controller.user.UserSession;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class ReportsForAdminFormController implements Initializable {
    public AnchorPane adminpane;
    public Label lblUserId;
    public Label lblUserType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = UserSession.getInstance().getCurrentSession();
        lblUserId.setText(currentUser.getUserId());
        lblUserType.setText(currentUser.getUserType());
    }


    ProductBo productBoImpl = BoFactory.getInstance().getBo(BoType.PRODUCT);


    public boolean exportInventoryReport(String reportFormat) {
        String path = "D:\\Documents\\Career\\My Projects\\Clothify Store POS JavaFX\\clothify-store-pos-javafx\\reports";
        List<Product> products = productBoImpl.searchAllProducts();

        try {
            // Load JRXML file
            File file = new File("D:\\Documents\\Career\\My Projects\\Clothify Store POS JavaFX\\clothify-store-pos-javafx\\src\\main\\resources\\view\\inventoryReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            // Create data source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);

            // Set parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Created By", "Clothify Store");

            // Fill report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export report based on format
            if ("html".equalsIgnoreCase(reportFormat)) {
                JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\InventoryReport.html");
            } else if ("pdf".equalsIgnoreCase(reportFormat)) {
                JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\InventoryReport.pdf");
            } else {
                // Invalid format
                showAlert("Error", "Unsupported Report Format", Alert.AlertType.ERROR);
                return false;
            }

            // Success message
            showAlert("Success", "Inventory Report Generated Successfully", Alert.AlertType.INFORMATION);
            return true;

        } catch (JRException e) {
            // Exception occurred
            showAlert("Error", "Failed To Generate Inventory Report:\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void btnInventoryReportOnAction(ActionEvent actionEvent) {
        try {
            if (!exportInventoryReport("pdf")) {
                showAlert("Error", "Failed To Generate Inventory Report", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSupplierReportOnAction(ActionEvent actionEvent) {
    }

    public void btnSalesReportOnAction(ActionEvent actionEvent) {
    }

    public void btnEmployeeReportOnAction(ActionEvent actionEvent) {
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

}
