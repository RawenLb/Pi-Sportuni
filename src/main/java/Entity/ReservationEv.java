package Entity;

import java.sql.Date;

public class ReservationEv {
    private int id_r;
    private Date date_r;
    private int id_u; // ID USER
    private int nbr_max;
    private int id_e; // ID EVENT

    public ReservationEv(int id_r, Date date_r, int id_u, int nbr_max, int id_e) {
        this.id_r = id_r;
        this.date_r = date_r;
        this.id_u = id_u;
        this.nbr_max = nbr_max;
        this.id_e = id_e;
    }

    public ReservationEv(Date date_r, int nbr_max, int id_e) {
        this.date_r = date_r;
        this.id_u = 1; // TODO: Change the User ID
        this.nbr_max = nbr_max;
        this.id_e = id_e;
    }

    public ReservationEv() {

    }

    public int getId_r() {
        return id_r;
    }

    public void setId_r(int id_r) {
        this.id_r = id_r;
    }

    public Date getDate_r() {
        return date_r;
    }

    public void setDate_r(Date date_r) {
        this.date_r = date_r;
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

    public int getId_e() {
        return id_e;
    }

    public void setId_e(int id_e) {
        this.id_e = id_e;
    }
}
