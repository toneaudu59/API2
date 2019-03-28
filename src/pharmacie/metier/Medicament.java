package pharmacie.metier;
/**
 * classe métier de gestion d'un medicament
 *
 * @author Arnaud Laffineur
 * @version 1.0
 *
 */
public class Medicament {
    /**
     * identifiant unique du medicament
     */
    private int id;
    /**
     * nom du medicament
     */
    private String nom;
    /**
     * description du medicament
     */
    private String description;
    /**
     * stock et unite du medicament
     */
    private String stock;
    
    /**
     * constructeur par défaut
     */
    public Medicament() {
    }

    /**
     * constructeur paramétré
     *
     * @param id identifiant unique du medicament, affecté par la base de données
     * @param nom nom du medicament
     * @param description description du medicament
     * @param stock stock du medicament
     */
    public Medicament(int id, String nom, String description, String stock) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.stock = stock;
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
     * setter description 
     * @param description description du medicament
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * getter stock 
     * @return stock du medicament
     */
    public String getStock() {
        return stock;
    }
    /**
     * setter stock 
     * @param stock stock du medicament
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
    * méthode toString
    * @return informations complètes
    */
    @Override
    public String toString() {
      //  return "Medicament{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", stock=" + stock + '}';
        return "Médicament n°"+id+":"+"\n"+nom+" "+description;
    }
    
}
