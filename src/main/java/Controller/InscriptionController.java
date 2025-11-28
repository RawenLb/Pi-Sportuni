package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Service.UserService;
import Entity.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Connection;
import java.util.ResourceBundle;

public class InscriptionController {
    UserService us = new UserService();
    @FXML
    private Button btn_goback;
    @FXML
    private Button btn_isncri;

    @FXML
    private ComboBox <String> cmbRole;

    @FXML
    private TextField tfadress;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfnumero;

    @FXML
    private TextField tfprenom;

    @FXML
    private TextField tfpwd;

    private Connection cnx;
    @FXML

    void add(ActionEvent event) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(tfpwd.getText().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hash) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String HASHED_MDP = hexString.toString();
            User u = new User(tfnom.getText(), tfprenom.getText(), tfemail.getText(), HASHED_MDP, Integer.parseInt(tfnumero.getText()), tfadress.getText());
            us.add(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User added successfully");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error adding user");
            alert.showAndWait();
        }
    };

    public void initialize() {
//        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "Client");
//        cmbRole.setItems(roles);


    }

    @FXML
    void Back(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }





}
