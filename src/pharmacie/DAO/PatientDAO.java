package pharmacie.DAO;

/**
 * classe de mappage poo-relationnel client
 *
 * @author Arnaud Laffineur
 * @version 1.0
 * @see Medicament
 */
import java.sql.*;
import java.util.*;
import myconnection.DBConnection;
import pharmacie.metier.Patient;

public class PatientDAO extends DAO<Patient> {

    /**
     * création d'un patient sur base des valeurs de son objet métier
     *
     * @throws SQLException erreur de création
     * @param obj patient à créer
     * @return patient créé
     */
    @Override
    public Patient create(Patient obj) throws SQLException {

        String req1 = "insert into api_patient(nom,prenom,tel) values(?,?,?)";
        String req2 = "select idpat from api_patient where nom=? and prenom=? and tel=?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm1.setString(1, obj.getNom());
            pstm1.setString(2, obj.getPrenom());
            pstm1.setString(3, obj.getTel());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                throw new SQLException("erreur de creation du patient, aucune ligne créée");
            }
            pstm2.setString(1, obj.getNom());
            pstm2.setString(2, obj.getPrenom());
            pstm2.setString(3, obj.getTel());
            try (ResultSet rs = pstm2.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("IDPAT");
                    obj.setId(id);
                    return read(id);
                } else {
                    throw new SQLException("erreur de création du patient record introuvable");
                }
            }
        }
    }

    /**
     * récupération des données d'un patient sur base de son identifiant
     *
     * @throws SQLException code inconnu
     * @param id identifiant du patient
     * @return patient trouvé
     */
    @Override
    public Patient read(int id) throws SQLException {

        String req = "select * from api_patient where idpat = ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    String tel = rs.getString("TEL");
                    return new Patient(id, nom, prenom, tel);

                } else {
                    throw new SQLException("Code inconnu");
                }

            }
        }
    }
    
    /**
     * récupération des données d'un patient sur base de son nom,prénom et telephone
     *
     * @throws SQLException code inconnu
     * @param obj objet de type Patient
     * @return patient trouvé
     */
    public Patient read(Patient obj) throws SQLException {

        String req = "select * from api_patient where nom = ? and prenom= ? and tel= ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setString(1, obj.getNom());
            pstm.setString(2, obj.getPrenom());
            pstm.setString(3, obj.getTel());
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("IDPAT");
                    return new Patient(id, obj.getNom(), obj.getPrenom(), obj.getTel());

                } else {
                    throw new SQLException("Patient inconnu");
                }

            }
        }
    }
    

    /**
     * mise à jour des données du patient sur base de son identifiant
     *
     * @return Patient
     * @param obj patient à mettre à jour
     * @throws SQLException erreur de mise à jour
     */
    @Override
    public Patient update(Patient obj) throws SQLException {
        String req = "update api_patient set nom=?,prenom=?,tel=?where idpat= ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(4, obj.getId());
            pstm.setString(1, obj.getNom());
            pstm.setString(2, obj.getPrenom());
            pstm.setString(3, obj.getTel());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new SQLException("aucune ligne patient mise à jour");
            }
            return read(obj.getId());
        }
    }

    /**
     * effacement du patient sur base de son identifiant
     *
     * @throws SQLException erreur d'effacement
     * @param obj patient à effacer
     */
    @Override
    public void delete(Patient obj) throws SQLException {
        String req = "delete from api_patient where idpat= ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(1, obj.getId());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new SQLException("aucune ligne patient effacée");
            }

        }
    }

    /**
     * méthode permettant de récupérer tous les patient qui ont le même nom
     *
     * @param nom nom recherché
     * @return liste de patients
     * @throws SQLException nom inconnu
     */
    
    public List<Patient> rech(String nom) throws SQLException {
        List<Patient> plusieurs = new ArrayList<>();
        String req = "select * from api_patient where nom=?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, nom);
            try (ResultSet rs = pstm.executeQuery()) {
                boolean trouve = false;
                while (rs.next()) {
                    trouve = true;
                    int id = rs.getInt("IDPAT");
                    String nomp = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    String tel = rs.getString("TEL");
                    plusieurs.add(new Patient(id, nomp, prenom, tel));
                }

                if (!trouve) {
                    throw new SQLException("nom inconnu");
                } else {
                    return plusieurs;
                }
            }
        }

    }
}

