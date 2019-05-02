package design_pattern.metier;

import java.util.Objects;
import pharmacie.metier.Medicament;
import pharmacie.metier.Prescription;
/**
 * classe métier de gestion d'une info
 *
 * @author Arnaud Laffineur
 * @version 1.0
 *
 */

public class Info {
    /**
     * identifiant unique de l'info
     */
    private int id;
    
    /**
     * quantite de l'info
     */
    private int quantite;
    
    /**
     * unite de la quantite de l'info
     */
    private String unite;
    
    private Medicament medicament;
    private Prescription prescription;
    /**
     * constructeur par défaut
     */
    public Info() {
    }

    /**
     * constructeur paramétré
     *
     * @param id identifiant unique de l'info, affecté par la base de données
     * @param quantite quantite du medicament de l'info
      * @param unite unite du medicament de l'info
     * @param idmedoc identifiant du medicament de l'info
     * @param idpres identifiant de la prescription de l'info

     */
    public Info(int id, int quantite, String unite, Medicament med, Prescription pres) {
        this.id = id;
        this.quantite=quantite;
        this.unite=unite;
        this.medicament = med;
        this.prescription =pres;
    }

    /**
     * getter id
     * @return identifiant de l'info
     */
    public int getId() {
        return id;
    }

    /**
     * setter id
     * @param id identifiant de l'info
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * getter quantite
     * @return quantite du medicament de l'info
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * setter quantite
     * @param quantite quantite du medicament de l'info
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * getter unite
     * @return unite du medicament de l'info
     */
    public String getUnite() {
        return unite;
    }

    /**
     * setter unite
     * @param unite unite du medicament de l'info
     */
    public void setUnite(String unite) {
        this.unite = unite;
    }
    
    public Medicament getMedicament(){
        return medicament;
    }

    public Prescription getPrescription(){
        return prescription;
    }
    /**
    * méthode toString
    * @return informations complètes
    */
    @Override
    public String toString() {
        return "Info{" + "id=" + id + ", quantite=" + quantite + ", unite=" + unite+'}';
    }

}