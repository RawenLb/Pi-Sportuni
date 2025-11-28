package Controller;

import Entity.Cour;
import Utiils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;

import java.net.URL;
import java.time.LocalDate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;


public class Notification implements Initializable {

    @FXML
    private ListView<String> notificationsListView;

    @FXML
    private Label clDate;

    @FXML
    private Label clDiscipline;

    @FXML
    private Label clHeureDebut;

    @FXML
    private Label clNom;

    @FXML
    private Label clNomSalle;

    @FXML
    private Label courseStatusLabel;

    private ObservableList<String> notificationsList = FXCollections.observableArrayList();

    public void updateNotificationsList(ObservableList<String> notifications) {
        notificationsList.setAll(notifications);
    }

    public void initData(Cour cour) {
        clNom.setText("Nom du cours : " + cour.getId_cour());
        clDiscipline.setText("Discipline : " + cour.getDiscipline().getNom_discipline());
        clDate.setText("Date : " + cour.getDate().toString());
        clHeureDebut.setText("Heure début : " + cour.getHeure_debut());
    }

    private String formatCourseDetails(Cour cour) {
        LocalDate today = LocalDate.now();
        LocalDate coursDate = cour.getDate().toLocalDate(); // Convertir la date de java.sql.Date en LocalDate
        String etatCours = coursDate.isBefore(today) ? "Terminé" : "En cours";

        return String.format("Nom du cours : %s\nDiscipline : %s\nDate : %s\nHeure début : %s\nNom de la salle : %s\nÉtat du cours : %s\n",
                cour.getNom_cour(), cour.getDiscipline().getNom_discipline(), coursDate, cour.getHeure_debut(), cour.getNom_salle(), etatCours);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAndDisplayNotifications();
    }
    private void loadAndDisplayNotifications() {
        try {
            Connection conn = DataSource.getCnx();
            if (conn == null) {
                showAlert("Erreur", "Impossible de se connecter à la base de données.", Alert.AlertType.ERROR);
                return;
            }

            String query = "SELECT * FROM notifications";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            ObservableList<String> notifications = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String courseName = resultSet.getString("nom_cour");
                String discipline = resultSet.getString("nom_discipline");
                String salle = resultSet.getString("nom_salle");
                Date date = resultSet.getDate("date");
                Time startTime = resultSet.getTime("heure_debut");

                // Créer un objet Cour avec les données récupérées
                Cour cour = new Cour();
                cour.setId_cour(id);
                cour.setNom_cour(courseName);
                cour.getDiscipline().setNom_discipline(discipline);
                cour.setNom_salle(salle);
                cour.setDate(date);
                cour.setHeure_debut(startTime.toLocalTime().toString());

                // Ajouter les informations du cours à la liste des notifications
                notifications.add(formatCourseDetails(cour));
            }

            // Mettre à jour la liste des notifications dans la ListView
            notificationsListView.setItems(notifications);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les notifications.", Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}