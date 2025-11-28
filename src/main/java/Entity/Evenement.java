package Entity;

import java.sql.Date;


public class Evenement {
    private int id_e;
    private String nom_e;
    private String nom_salle;
    private Date date_debut;
    private String nom_discipline;
    private String description;
    private int id_u;
    private int nbr_max;
    private Date date_fin;




    // Constructeur
    public Evenement(int id_e, String nom_e, String nom_salle, Date date_debut, String nom_discipline, String description, int id_u, int nbr_max, Date date_fin) {
        this.id_e = id_e;
        this.nom_e = nom_e;
        this.nom_salle = nom_salle;
        this.date_debut=date_debut;
        this.nom_discipline = nom_discipline;
        this.description = description;
        this.id_u = id_u;
        this.nbr_max= nbr_max;
        this.date_fin=date_fin;

    }
    public Evenement(int id_e, String nom_e, String nom_salle, Date date_debut, String nom_discipline, String description, int nbr_max, Date date_fin) {
        this.id_e = id_e;
        this.nom_e = nom_e;
        this.nom_salle = nom_salle;
        this.date_debut=date_debut;
        this.nom_discipline = nom_discipline;
        this.description = description;
        this.nbr_max= nbr_max;
        this.date_fin=date_fin;
        this.id_u = 1; //TODO: CHANGE THE ADMIN ID
    }
    public Evenement(String nom_e, String nom_salle, String date_debut, String nom_discipline, String description, int nbr_max, String date_fin ) {

        this.nom_e = nom_e;
        this.nom_salle = nom_salle;
        this.date_debut= Date.valueOf(date_debut);
        this.nom_discipline = nom_discipline;
        this.description = description;
        this. nbr_max= nbr_max;
        this.date_fin= Date.valueOf(date_fin);
    }


    public Evenement(String text, String text1, String text2, int i, String text3, String text4, String text5) {
    }

    public Evenement() {

    }



    public Evenement(String nom_e, String nom_salle, Date date_debut, String nom_discipline, String description, int id_u, int nbr_max, Date date_fin) {
        this.nom_e = nom_e;
        this.nom_salle = nom_salle;
        this.date_debut = date_debut;
        this.nom_discipline = nom_discipline;
        this.description = description;
        this.id_u = id_u;
        this.nbr_max = nbr_max;
        this.date_fin = date_fin;
    }

    public Evenement(String nomE, String nom, Date date_debut, String nomDiscipline, String description, int nbrMax, Date date_fin) {
        this.nom_e = nomE;
        this.nom_salle = nom;
        this.date_debut = date_debut;
        this.nom_discipline = nomDiscipline;
        this.description = description;
        this.nbr_max = nbrMax;
        this.date_fin = date_fin;
        this.id_u = 1; // TODO: CHANGE THE ADMIN ID
    }

    // Getters et setters
    public int getId_e() {
        return id_e;
    }

    public  void setId_e(int id_e) {
        this.id_e = id_e;
    }

    public String getNom_e() {
        return nom_e;
    }

    public void setNom_e(String nom_e) {
        this.nom_e = nom_e;
    }

    public String getNom_salle() {
        return nom_salle;
    }

    public void setNom_salle(String nom_salle) {
        this.nom_salle = nom_salle;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date  date_debut) {
        this.date_debut = date_debut;
    }



    public String getNom_discipline() {
        return nom_discipline;
    }

    public void setNom_discipline(String nom_discipline) {
        this.nom_discipline = nom_discipline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }



    public int getNbr_max() {
        return nbr_max;
    }

    public void setNbr_max(int nbr_max) {
        this.nbr_max = nbr_max;
    }
    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id_e=" + id_e +
                ", nom_e='" + nom_e + '\'' +
                ", nom_salle='" + nom_salle + '\'' +
                ", date_debut=" + date_debut +
                ", nom_discipline='" + nom_discipline + '\'' +
                ", description='" + description + '\'' +
                ", id_u=" + id_u +
                ", nbr_max=" + nbr_max +
                ", date_fin=" + date_fin +
                '}';
    }

}
