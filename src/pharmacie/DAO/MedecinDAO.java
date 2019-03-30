package pharmacie.DAO;

/**
 * classe de mappage poo-relationnel client
 *
 * @author Arnaud Laffineur
 * @version 1.0
 * @see Medecin
 */
import java.sql.*;
import java.util.*;
import myconnection.DBConnection;
import pharmacie.metier.Medecin;

public class MedecinDAO extends DAO<Medecin> {

    /**
     * création d'un medecin sur base des valeurs de son objet métier
     *
     * @throws SQLException erreur de création
     * @param obj medecin à créer
     * @return medecin créé
     */
    @Override
    public Medecin create(Medecin obj) throws SQLException {

        String req1 = "insert into api_medecin(idmed,matricule,nom,prenom,tel) values(API_MEDECIN_SEQ1.nextval,?,?,?,?)";
        String req2 = "select idmed from api_medecin where nom=? and prenom=? and tel=?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm1.setString(1, obj.getMatricule());
            pstm1.setString(2, obj.getNom());
            pstm1.setString(3, obj.getPrenom());;
            pstm1.setString(4, obj.getTel());
            int n = pstm1.executeUpdate();
            
            if (n == 0) {
                throw new SQLException("erreur de creation du médecin, aucune ligne créée");
            }
            pstm2.setString(1, obj.getNom());
            pstm2.setString(2, obj.getPrenom());
            pstm2.setString(3, obj.getTel());
            try (ResultSet rs = pstm2.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                    return read(id);
                } else {
                    throw new SQLException("erreur de création du médecin, record introuvable");
                }
            }
        }
    }

    /**
     * récupération des données d'un medecin sur base de son identifiant
     *
     * @throws SQLException code inconnu
     * @param id identifiant du medecin
     * @return medecin trouvé
     */
    @Override
    public Medecin read(int id) throws SQLException {

        String req = "select * from api_medecin where idmed = ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String matricule = rs.getString("MATRICULE");
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    String tel = rs.getString("TEL");
                    return new Medecin(id, matricule, nom, prenom, tel);

                } else {
                    throw new SQLException("Id inconnu");
                }

            }
        }
    }

    /**
     * récupération des données d'un medecin sur base de son nom,prénom et telephone
     *
     * @throws SQLException code inconnu
     * @param obj objet de type Medecin
     * @return medecin trouvé
     */
    public Medecin read(Medecin obj) throws SQLException {

        String req = "select * from api_medecin where nom = ? and prenom= ? and tel= ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setString(1, obj.getNom());
            pstm.setString(2, obj.getPrenom());
            pstm.setString(3, obj.getTel());
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("IDMED");
                    String matricule = rs.getString("MATRICULE");
                    return new Medecin(id,matricule, obj.getNom(), obj.getPrenom(), obj.getTel());

                } else {
                    throw new SQLException("Médecin inconnu");
                }

            }
        }
    }
    
    /**
     * récupération des données d'un medecin sur base de son matricule
     *
     * @throws SQLException code inconnu
     * @param matricule Matricule du medecin
     * @return medecin trouvé
     */
    public Medecin read(String matricule) throws SQLException {

        String req = "select * from api_medecin where matricule=?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setString(1, matricule);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("IDMED");
                    String nom=rs.getString("NOM");
                    String prenom=rs.getString("PRENOM");
                    String tel=rs.getString("tel");
                    return new Medecin(id, matricule, nom, prenom, tel);

                } else {
                    throw new SQLException("Matricule du médecin inconnu");
                }

            }
        }
    }
    
    /**
     * mise à jour des données d'un medecin sur base de son identifiant
     *
     * @return Medecin
     * @param obj medecin à mettre à jour
     * @throws SQLException erreur de mise à jour
     */
    @Override
    public Medecin update(Medecin obj) throws SQLException {
        String req = "update api_medecin set matricule=?,nom=?,prenom=?,tel=? where idmed= ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(5, obj.getId());
            pstm.setString(1, obj.getMatricule());
            pstm.setString(2, obj.getNom());
            pstm.setString(3, obj.getPrenom());
            pstm.setString(4, obj.getTel());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new SQLException("Aucune ligne médecin mise à jour");
            }
            return read(obj.getId());
        }
    }

    /**
     * effacement d'un medecin sur base de son identifiant
     *
     * @throws SQLException erreur d'effacement
     * @param obj medecin à effacer
     */
    @Override
    public void delete(Medecin obj) throws SQLException {

        String req = "delete from api_medecin where idmed= ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            
            pstm.setInt(1, obj.getId());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new SQLException("Aucune ligne médecin effacée");
            }

        }
    }

    /**
     * méthode permettant de récupérer tous les medecin portant un
     * certain nom
     * @param nomrech nom recherché
     * @return liste de medecins
     * @throws SQLException nom inconnu
     */
    public List<Medecin> rech(String nomrech) throws SQLException {
        List<Medecin> plusieurs = new ArrayList<>();
        String req = "select * from api_medecin where nom = ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, nomrech);
            try (ResultSet rs = pstm.executeQuery()) {
                boolean trouve = false;
                while (rs.next()) {
                    trouve = true;
                    int id = rs.getInt("IDMED");
                    String matricule = rs.getString("MATRICULE");
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    String tel = rs.getString("TEL");
                    plusieurs.add(new Medecin(id, matricule, nom, prenom, tel));
                }

                if (!trouve) {
                    throw new SQLException("Nom inconnu");
                } else {
                    return plusieurs;
                }
            }
        }
        
        
    }

}
