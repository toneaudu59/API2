package pharmacie.metier;
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
    
    /**
     * identifiant du medicament de l'info
     */
    private int idmedoc;
    
    /**
     * identifiant de la prescription de l'info
     */
    private int idpres;

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
    public Info(int id, int quantite, String unite, int idmedoc, int idpres) {
        this.id = id;
        this.quantite=quantite;
        this.unite=unite;
        this.idmedoc = idmedoc;
        this.idpres = idpres;
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

    /**
     * getter idmedoc
     * @return identifiant du medicament de l'info
     */
    public int getIdmedoc() {
        return idmedoc;
    }

    /**
     * setter idmedoc
     * @param idmedoc identifiant du medicament de l'info
     */
    public void setIdmedoc(int idmedoc) {
        this.idmedoc = idmedoc;
    }

    /**
     * getter idpres
     * @return identifiant de la prescription de l'info
     */
    public int getIdpres() {
        return idpres;
    }

    /**
     * setter idpres
     * @param idpres identifiant de la prescription de l'info
     */
    public void setIdpres(int idpres) {
        this.idpres = idpres;
    }

    /**
    * méthode toString
    * @return informations complètes
    */
    @Override
    public String toString() {
        return "Info{" + "id=" + id + ", quantite=" + quantite + ", unite=" + unite + ", idmedoc=" + idmedoc + ", idpres=" + idpres + '}';
    }

}
