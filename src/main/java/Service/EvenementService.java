package Service;

import Entity.Evenement;
import Utiils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements Gservice<Evenement>  {
    private static EvenementService instance;
    private Connection connexion ;



    private static Statement ste;
    private PreparedStatement pst;


    public EvenementService() {
        connexion = DataSource.getInstance().getCnx();
    }

    public Evenement getLastInsertedEvenement() {
        String query = "SELECT * FROM evenement ORDER BY id_e DESC LIMIT 1";
        try (
                PreparedStatement preparedStatement = connexion.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id_e");
                String nomE = resultSet.getString("nom_e");
                String nomSalle = resultSet.getString("nom_salle");
                Date date_debut = resultSet.getDate("date_debut");
                String nomDiscipline = resultSet.getString("nom_discipline");
                String description = resultSet.getString("description");
                int nbr_max = resultSet.getInt("nbr_max");
                Date date_fin = resultSet.getDate("date_fin");

                Evenement evenement = new Evenement(id, nomE, nomSalle, date_debut, nomDiscipline, description, nbr_max, date_fin);
                return evenement;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(Evenement evenement) {
        String requete = "INSERT INTO evenement (date_debut, date_fin, description, nbr_max, nom_discipline, nom_e, nom_salle, id_u) values (?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connexion.prepareStatement(requete)) {
            pstmt.setDate(1, evenement.getDate_debut());
            pstmt.setDate(2, evenement.getDate_fin());
            pstmt.setString(3, evenement.getDescription());
            pstmt.setInt(4, evenement.getNbr_max());
            pstmt.setString(5, evenement.getNom_discipline());
            pstmt.setString(6, evenement.getNom_e());
            pstmt.setString(7, evenement.getNom_salle());
            pstmt.setInt(8, evenement.getId_u());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Evenement evenement) {
        try {
            String requete = "delete from evenement where id_e=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, evenement.getId_e());
            pst.executeUpdate();
            System.out.println("Suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void update(Evenement evenement) {
        System.out.println(evenement.getId_u());
        String requete = "update evenement set nom_e = ?, nom_salle = ?, date_debut = ?, nom_discipline = ?, date_fin = ?, description = ?, nbr_max = ?, id_u = ? where id_e = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(requete)) {
            pstmt.setString(1, evenement.getNom_e());
            pstmt.setString(2, evenement.getNom_salle());
            pstmt.setDate(3, evenement.getDate_debut());
            pstmt.setString(4, evenement.getNom_discipline());
            pstmt.setDate(5, evenement.getDate_fin());
            pstmt.setString(6, evenement.getDescription());
            pstmt.setInt(7, evenement.getNbr_max());
            pstmt.setInt(8, evenement.getId_u());
            pstmt.setInt(9, evenement.getId_e());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<Evenement> readAll() {


        String requete = "select * from evenement";
        List<Evenement> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            ResultSet rs;
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Evenement e1=new Evenement();
                e1.setId_e( rs.getInt("id_e"));
                e1.setDate_debut(rs.getDate("date_debut"));
                e1.setDate_fin(rs.getDate("date_fin"));
                e1.setNom_discipline(rs.getString("nom_discipline"));
                e1.setDescription(rs.getString("description"));
                e1.setNbr_max(rs.getInt("nbr_max"));
                e1.setNom_salle(rs.getString("nom_salle"));
                e1.setNom_e(rs.getString("nom_e"));
                /*
                 rs.getString("nom_e"),
                        rs.getString("nom_salle"),
                        rs.getDate("dateDebut"),
                        rs.getString("nom_discipline"),
                        rs.getString("description"),
                        rs.getInt("nbr_max"),
                        rs.getInt(8),
                        rs.getDate("dateFin")
                 */
                list.add(e1);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


    @Override
    public Evenement readById(int id) {
        String requete = "select * from evenement where id=" + id;
        Evenement evenement = null;
        try {
            ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                evenement = new Evenement(
                        rs.getInt(1),
                        rs.getString("nom_e"),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getDate(8));
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return evenement;
    }

    public List <Evenement> getAllEvenements() {
        List<Evenement> evenements;

        evenements = new ArrayList<>();
        return evenements;}

    public int GetNombreDePlaces(int idE) {
        int nombreDePlaces = 0;
        try {
            String query = "SELECT COUNT(*) AS count FROM reservation WHERE id_e = ?";
            PreparedStatement pst = connexion.prepareStatement(query);
            pst.setInt(1, idE);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nombreDePlaces = rs.getInt("count");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return nombreDePlaces;
    }

}
