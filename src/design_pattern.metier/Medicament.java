package design_pattern.metier;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import pharmacie.metier.Prescription;

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
     * code du medicament
     */
    private String code;
    
    private Set<Prescription> prescription=new HashSet<>();
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
     * @param code code du medicament
     */
    public Medicament(int id, String nom, String description, String code) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.code = code;
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
     * getter code
     * @return code du medicament
     */
    public String getCode() {
        return code;
    }

    /**
     * setter code
     * @param code code du medicament
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    public Set getPrescription(){
        return prescription;
    }
    /**
    * méthode toString
    * @return informations 
    */
    @Override
    public String toString() {
      //  return "Medicament{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", stock=" + stock + '}';
        return "Médicament "+code+":"+"\n"+nom+" "+description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Medicament other = (Medicament) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }
    
}
