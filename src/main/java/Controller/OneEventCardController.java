package Controller;

import Entity.Evenement;
import Entity.MailUtil;
import Entity.ReservationEv;
import Service.EvenementService;
import Service.ReservationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class OneEventCardController {
    public Text nom_e;
    public Text nom_salle;
    public Text dateDebut;
    public Text description;
    public Text discipline;
    public Text nb_left;
    public HBox AddToCart;
    @FXML
    public Text reserv_txt;
    ReservationService reservationService = new ReservationService();

    public void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Set the title and content text
        alert.setTitle(title);
        alert.setHeaderText(null);  // Set header text to null to hide it
        alert.setContentText(msg);

        // Show the alert dialog
        alert.showAndWait();
    }

    @FXML
    public void initialize() {

    }

    public void setEventData(Evenement evenement) {
        // Instancier le service de produit
        if (reservationService.isEventReserved(evenement.getId_e(), 1) != null) // TODO: Change the USER ID
            reserv_txt.setText("Annuler");
        else
            reserv_txt.setText("Reserver");


        nom_e.setText(evenement.getNom_e());

        nom_salle.setText("Nom Salle:" + evenement.getNom_salle());
        dateDebut.setText("Date de début:" + evenement.getDate_debut());
        description.setText("Description:" + evenement.getDescription());
        discipline.setText("discipline" + evenement.getNom_discipline());

        AddToCart.setOnMouseClicked(event -> {

            System.out.println("Adding a new Reservation for : " + evenement.getId_e());
            ReservationService reservationService = new ReservationService();

            try {
                // FOR INTEGRATION CHANGE USERID
                java.util.Date currentDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());

                ReservationEv ReservationEv = new ReservationEv(sqlDate, evenement.getNbr_max(), evenement.getId_e());
                if (reservationService.isEventReserved(evenement.getId_e(), 1) != null) { // TODO: Change the USER ID
                    reservationService.delete(evenement.getId_e(), 1);// TODO: Change the USER ID
                    MailUtil.sendMailReservation("Votre réservation à été annulé");
                    reserv_txt.setText("Reserver");
                    showAlert("Succees", "Votre réservation à été bien annule");
                }
                else {
                    reservationService.add(ReservationEv);
                    MailUtil.sendMailReservation("Votre réservation à été accepté");
                    reserv_txt.setText("Annuler");
                    showAlert("Succees", "Votre réservation à été bien envoyé, Nous allons vous contactez via E-mail ultérieurement");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
