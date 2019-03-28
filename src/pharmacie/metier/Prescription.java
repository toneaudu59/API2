package pharmacie.metier;
/**
 * classe métier de gestion d'une prescription
 *
 * @author Arnaud Laffineur
 * @version 1.0
 *
 */

import java.time.LocalDate;

public class Prescription {
    /**
     * identifiant unique de la prescription
     */
    private int id;
    
    /**
     * date de la prescription
     */
    private LocalDate date;
    
    /**
     * identifiant du medecin qui a fait la prescription
     */
    private int idmed;
    
    /**
     * identifiant du patient qui a la prescription
     */
    private int idpat;

    /**
     * constructeur par défaut
     */
    public Prescription() {
    }

    /**
     * constructeur paramétré
     *
     * @param id identifiant unique de la prescription, affecté par la base de données
     * @param date date de la prescription
     * @param idmed identifiant du medecin qui a fait la prescription
     * @param idpat identifiant du patient qui a la prescription

     */
    public Prescription(int id, LocalDate date,int idmed,int idpat) {
        this.id = id;
        this.date = date;
        this.idmed=idmed;
        this.idpat=idpat;
    }
    
    /**
     * constructeur paramétré
     *
     * @param id identifiant unique de la prescription, affecté par la base de données
     * @param idmed identifiant du medecin qui a fait la prescription
     * @param idpat identifiant du patient qui a la prescription

     */
    public Prescription(int id,int idmed,int idpat) {
        this.id = id;
        this.idmed=idmed;
        this.idpat=idpat;
    }

    /**
     * getter id
     * @return identifiant de la prescription
     */
    public int getId() {
        return id;
    }

    /**
     * setter id
     * @param id identifiant de la prescription
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter date
     * @return date de la prescription
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * setter date
     * @param date date de la prescription
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * getter idmed
     * @return identifiant du medecin qui a fait la prescription
     */
    public int getIdmed() {
        return idmed;
    }

    /**
     * setter idmed
     * @param idmed identifiant du medecin qui a fait la prescription
     */
    public void setIdmed(int idmed) {
        this.idmed = idmed;
    }

    /**
     * getter idpat
     * @return identifiant du patient qui a la prescription
     */
    public int getIdpat() {
        return idpat;
    }

    /**
     * setter idpat
     * @param idpat identifiant du patient qui a la prescription
     */
    public void setIdpat(int idpat) {
        this.idpat = idpat;
    }
    
    /**
    * méthode toString
    * @return informations complètes
    */
    @Override
    public String toString() {
        return "Infos{" + "id=" + id + ", date=" + date + '}';
    }
}
