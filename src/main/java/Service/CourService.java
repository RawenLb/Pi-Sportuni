package Service;

import Entity.Cour;
import Entity.Discipline;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourService implements ICourService<Cour> {
    private Connection conn;

    private Statement ste;
    private static CourService instance;

    private PreparedStatement pst;

    public CourService() {
        conn = Utiils.DataSource.getInstance().getCnx();
    }


    @Override
    public void add(Cour c) {
        String requete = "INSERT INTO cour (nom_cour, date, heure_debut, heure_fin, nom_salle, nb_max, id_discipline) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, c.getNom_cour());
            pst.setDate(2, c.getDate());
            pst.setString(3, c.getHeure_debut());
            pst.setString(4, c.getHeure_fin());
            pst.setString(5, c.getNom_salle());
            pst.setString(6, c.getNb_max());
            pst.setInt(7, c.getDiscipline().getId_discipline()); // Utiliser l'id_discipline au lieu du nom_discipline
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(int id) {
        String requete = "DELETE FROM cour WHERE id_cour=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updato(Cour c) {
        String requete = "UPDATE cour SET nom_cour=?, date=?, heure_debut=?, heure_fin=?, nom_salle=?, nb_max=?, id_discipline=? WHERE id_cour=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, c.getNom_cour());
            pst.setDate(2, c.getDate());
            pst.setString(3, c.getHeure_debut());
            pst.setString(4, c.getHeure_fin());
            pst.setString(5, c.getNom_salle());
            pst.setString(6, c.getNb_max());
            pst.setInt(7, c.getDiscipline().getId_discipline());
            pst.setInt(8, c.getId_cour());
            pst.executeUpdate();
            System.out.println("Cour updated!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void update(Cour c, int id) {
        String requete = "UPDATE cour SET nom_cour=?, date=?, heure_debut=?, heure_fin=?, nom_salle=?, nb_max=?, id_discipline=? WHERE id_cour=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, c.getNom_cour());
            pst.setDate(2, c.getDate());
            pst.setString(3, c.getHeure_debut());
            pst.setString(4, c.getHeure_fin());
            pst.setString(5, c.getNom_salle());
            pst.setString(6, c.getNb_max());
            pst.setInt(7, c.getDiscipline().getId_discipline());
            pst.setInt(8, id); // Utilisation de l'id passé en paramètre pour l'identification de la cour à mettre à jour
            pst.executeUpdate();
            System.out.println("Cour updated!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Cour> readAll() {
        String requete = "SELECT cour.*, discipline.nom_discipline " +
                "FROM cour " +
                "LEFT JOIN discipline ON cour.id_discipline = discipline.id_discipline";
        List<Cour> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Discipline discipline = new Discipline();
                discipline.setId_discipline(rs.getInt("id_discipline"));
                discipline.setNom_discipline(rs.getString("nom_discipline"));

                // Créer un objet Cour avec le nom de la discipline associée
                Cour c = new Cour(
                        rs.getInt("id_cour"),
                        rs.getString("nom_cour"),
                        rs.getDate("date"),
                        rs.getString("heure_debut"),
                        rs.getString("heure_fin"),
                        rs.getString("nom_salle"),
                        rs.getString("nb_max"),
                        discipline // Utiliser l'objet Discipline créé
                );
                list.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Cour readById(int id) {
        String requete = "SELECT cour.*, discipline.nom_discipline " +
                "FROM cour " +
                "LEFT JOIN discipline ON cour.id_discipline = discipline.id_discipline " +
                "WHERE id_cour=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Discipline discipline = new Discipline();
                discipline.setId_discipline(rs.getInt("id_discipline"));
                discipline.setNom_discipline(rs.getString("nom_discipline"));

                return new Cour(
                        rs.getInt("id_cour"),
                        rs.getString("nom_cour"),
                        rs.getDate("date"),
                        rs.getString("heure_debut"),
                        rs.getString("heure_fin"),
                        rs.getString("nom_salle"),
                        rs.getString("nb_max"),
                        discipline
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public int getNombreTotalCours() {
        int totalCours = 0;
        String query = "SELECT COUNT(*) AS total_cours FROM cour"; // Utiliser le nom de la table en minuscules
        try {
            pst = conn.prepareStatement(query);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                totalCours = resultSet.getInt("total_cours");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalCours;
    }
    public List<Discipline> getAllDisciplines() {
        List<Discipline> disciplines = new ArrayList<>();
        String query = "SELECT * FROM discipline";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Discipline discipline = new Discipline();
                discipline.setId_discipline(rs.getInt("id_discipline"));
                discipline.setNom_discipline(rs.getString("nom_discipline"));
                disciplines.add(discipline);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return disciplines;
    }

    public static CourService getInstance() {
        if (instance == null) {
            instance = new CourService();
        }
        return instance;
    }


}