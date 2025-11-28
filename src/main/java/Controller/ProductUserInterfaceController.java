package Controller;

import Entity.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Service.ServiceProduct;
import Utiils.DataSource;
import java.io.IOException;
import java.sql.Connection;

public class ProductUserInterfaceController {
    private ServiceProduct productService = new ServiceProduct();
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> tvProduct;

    @FXML
    private TableColumn<Product, String> clNom;

    @FXML
    private TableColumn<Product, String> clCategory;

    @FXML
    private TableColumn<Product, Double> clPrix;

    @FXML
    private Button btnReturn;

    @FXML
    void initialize() {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            productList.addAll(productService.readAll());
            tvProduct.setItems(productList);
            clNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            clCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            clPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void returnToMenu(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/MenuUser.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}