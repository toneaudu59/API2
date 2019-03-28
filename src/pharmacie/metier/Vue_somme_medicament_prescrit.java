package pharmacie.metier;

public class Vue_somme_medicament_prescrit {
    
    /**
     * identifiant unique du medicament
     */
    private int id;
    
    /**
     * nom du medicament
     */
    private String nom;
    
    /**
     * nom du medicament
     */
    private String description;
    
    /**
     * quantite du medicament prescrit
     */
    private int quantite;
    
    /**
     * unite du medicament prescrit
     */
    private String unite;

    /**
     * constructeur par défaut
     */
    public Vue_somme_medicament_prescrit() {
    }

    /**
     * constructeur paramétré
     *
     * @param id identifiant unique du medicament, affecté par la base de données
     * @param nom nom du medicament
     * @param quantite quantite du medicament prescrit
     * @param unite unite du medicament prescrit
     */
    public Vue_somme_medicament_prescrit(int id, String nom, String description, int quantite, String unite) {
        this.id = id;
        this.nom = nom;
        this.description=description;
        this.quantite = quantite;
        this.unite = unite;
    }

    /**
     * getter id
     * @return identifiant du medicament
     */
    public int getId() {
        return id;
    }

    /**
     * setter id
     * @param id identifiant du medicament
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter nom
     * @return nom du medicament
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter nom
     * @param nom nom du medicament
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * getter description
     * @return description du medicament
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter nom
     * @param description description du medicament
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * getter quantite
     * @return quantite du medicament prescrit
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * setter quantite
     * @param quantite quantite du medicament prescrit
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * getter unite
     * @return unite du medicament prescrit
     */
    public String getUnite() {
        return unite;
    }

    /**
     * setter unite
     * @param unite unite du medicament prescrit
     */
    public void setUnite(String unite) {
        this.unite = unite;
    }

    /**
    * méthode toString
    * @return informations complètes
    */
    @Override
    public String toString() {
        return "Médicament n°"+id+":"+"\nNom :" + nom + " "+description+"\nQuantite totale prescrite:" + quantite + " " + unite;
    }
    
    
}
