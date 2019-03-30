package pharmacie.metier;

/**
 * classe métier de gestion d'un medecin
 *
 * @author Arnaud Laffineur
 * @version 1.0
 *
 */
public class Medecin {

    /**
     * identifiant unique du medecin
     */
    private int id;
    /**
     * matricule du medecin
     */
    private String matricule;
    /**
     * nom du medecin
     */
    private String nom;
    /**
     * prenom du medecin
     */
    private String prenom;
    /**
     * téléphone du medecin
     */
    private String tel;

    /**
     * constructeur par défaut
     */
    public Medecin() {
    }

    /**
     * constructeur paramétré
     *
     * @param id identifiant unique du medecin, affecté par la base de données
     * @param matricule matricule du medecin
     * @param nom nom du medecin
     * @param prenom prénom du medecin
     * @param tel téléphone du medecin
     */
    public Medecin(int id, String matricule, String nom, String prenom, String tel) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    /**
     * getter id
     * @return identifiant du medecin
     */
    public int getId() {
        return id;
    }

    /**
     * setter id
     * @param id identifiant du medecin
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter matricule
     * @return matricule du medecin
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * setter matricule
     * @param matricule matricule du medecin
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * getter nom
     * @return nom du medecin
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter nom
     * @param nom nom du medecin
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * getter prenom
     * @return prenom du medecin
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * setter prenom
     * @param prenom prenom du medecin
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * getter tel
     * @return téléphone du medecin
     */
    public String getTel() {
        return tel;
    }

    /**
     * setter tel
     * @param tel téléphone du medecin
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
    * méthode toString
    * @return informations complètes
    */
    @Override
    public String toString() {
        return "Médecin n°"+id+" :" +"\n"+nom+" "+prenom+"\nMatricule :"+matricule+"\nTéléphone :"+tel;
    }

}
