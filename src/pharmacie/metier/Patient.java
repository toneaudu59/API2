package pharmacie.metier;
/**
 * classe métier de gestion d'un patient
 *
 * @author Arnaud Laffineur
 * @version 1.0
 *
 */
public class Patient {
    /**
     * identifiant unique du patient
     */
    private int id;
    /**
     * nom du patient
     */
    private String nom;
    /**
     * prenom du patient
     */
    private String prenom;
    /**
     * téléphone du patient
     */
    private String tel;

    /**
     * constructeur par défaut
     */
    public Patient() {
    }

    /**
     * constructeur paramétré
     *
     * @param id identifiant unique du patient, affecté par la base de données
     * @param nom nom du patient
     * @param prenom prénom du patient
     * @param tel téléphone du patient
     */
    public Patient(int id, String nom, String prenom, String tel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    /**
     * getter id
     * @return identifiant du patient
     */
    public int getId() {
        return id;
    }

    /**
     * setter id
     * @param id identifiant du patient
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter nom
     * @return nom du patient
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter nom
     * @param nom nom du patient
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * getter prenom
     * @return prenom du patient
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * setter prenom
     * @param prenom prenom du patient
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * getter tel
     * @return téléphone du patient
     */
    public String getTel() {
        return tel;
    }
    
    /**
     * setter tel
     * @param tel téléphone du patient
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
        return "Patient n°"+id+" :" +"\n"+nom+" "+prenom+"\nTéléphone :"+tel;
    }
    
}
