package pharmacie.DAO;

/**
 * classe de mappage poo-relationnel ^prescription
 *
 * @author Arnaud Laffineur
 * @version 1.0
 * @see Prescription
 */
import java.sql.*;
//import java.util.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import myconnection.DBConnection;
import pharmacie.metier.Prescription;

public class PrescriptionDAO extends DAO<Prescription> {

    /**
     * création d'une prescription sur base des valeurs de son objet métier
     *
     * @throws SQLException erreur de création
     * @param obj prescription à créer
     * @return prescription créé
     */
    @Override
    public Prescription create(Prescription obj) throws SQLException {

        String req1 = "insert into api_prescription(dateprescription,idmed,idpat) values(?,?,?)";
        String req2 = "select idpres from api_prescription where dateprescription=? and idmed=? and idpat=?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm1.setDate(1, java.sql.Date.valueOf(obj.getDate()));
            pstm1.setInt(2, obj.getIdmed());
            pstm1.setInt(3, obj.getIdpat());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                throw new SQLException("erreur de creation de la prescription, aucune ligne créée");
            }
            pstm2.setDate(1, java.sql.Date.valueOf(obj.getDate()));
            pstm2.setInt(2, obj.getIdmed());
            pstm2.setInt(3, obj.getIdpat());
            try (ResultSet rs = pstm2.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                    return read(id);
                } else {
                    throw new SQLException("erreur de création de la prescription record introuvable");
                }
            }
        }
    }

    /**
     * récupération des données d'une prescription sur base de son identifiant
     *
     * @throws SQLException code inconnu
     * @param id identifiant de la prescription
     * @return prescription trouvé
     */
    @Override
    public Prescription read(int id) throws SQLException {

        String req = "select * from api_prescription where idpres = ?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {            
                if (rs.next()) {
                    LocalDate date = rs.getDate("DATEPRESCRIPTION").toLocalDate();
                    int idmed = rs.getInt("IDMED");
                    int idpat = rs.getInt("IDPAT");
                    return new Prescription(id, date, idmed, idpat);

                } else {
                    throw new SQLException("id de la prescription inconnu");
                }

            }
        }
    }

    /**
     * mise à jour des données d'une prescription sur base de son identifiant
     *
     * @return Prescription
     * @param obj prescription à mettre à jour
     * @throws SQLException erreur de mise à jour
     */
    @Override
    public Prescription update(Prescription obj) throws SQLException {
        String req = "update api_prescription set dateprescription=?,idmed=?,idpat=? where idpres= ?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(4, obj.getId());
            pstm.setDate(1, java.sql.Date.valueOf(obj.getDate()));
            pstm.setInt(2, obj.getIdmed());
            pstm.setInt(3, obj.getIdpat());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new SQLException("aucune ligne prescription mise à jour");
            }
            return read(obj.getId());
        }
    }

    /**
     * effacement d'une prescription sur base de son identifiant
     *
     * @throws SQLException erreur d'effacement
     * @param obj prescription à effacer
     */
    @Override
    public void delete(Prescription obj) throws SQLException {
        String req = "delete from api_prescription where idpres= ?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(1, obj.getId());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new SQLException("aucune ligne prescription effacée");
            }
        }
    }

    /**
     * méthode permettant de récupérer une prescription en fonction de son id
     *
     * @param id id recherché
     * @return description d'une prescription
     * @throws SQLException id inconnu
     */
    public String rech(int id) throws SQLException {
        String req = "select * from prescription2 where id=?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String prescription = rs.getString("PRES");
                    return prescription;
                } else {
                    throw new SQLException("id de la prescription inconnu");
                }
            }
        }

    }

    /**
     * méthode permettant de récupérer une prescription en fonction de l'id de
     * son patient
     *
     * @param id id recherché
     * @param ch liste à retourner
     * @return description ou id d'une prescription
     * @throws SQLException id inconnu
     */
    public List<String> rechp(int id, String ch) throws SQLException {
        List<String> plusieurs = new ArrayList<>();
        String req = "select api_prescription.idpres as id,api_patient.idpat as idpat,dateprescription||' '||api_medicament.nom||' '||api_info.quantite||' '||api_info.unite||' '||description as PRES\n"
                + "from api_patient\n"
                + "join api_prescription\n"
                + "on api_patient.idpat=api_prescription.idpat\n"
                + "join api_info\n"
                + "on api_prescription.idpres=api_info.idpres\n"
                + "join api_medicament\n"
                + "on api_info.idmedoc=api_medicament.idmedoc\n"
                + "where api_patient.idpat=?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                boolean trouve = false;
                if (ch.equals("PRES")) {
                    while (rs.next()) {
                        trouve = true;
                        plusieurs.add(rs.getString("PRES"));
                    }
                } else {
                    while (rs.next()) {
                        trouve = true;
                        plusieurs.add("" + rs.getInt("ID"));
                    }
                }

                if (!trouve) {
                    throw new SQLException("id de la prescription inconnu");
                } else {
                    return plusieurs;
                }
            }
        }

    }

//TODO recherche idmed
    /**
     * méthode permettant de récupérer une prescription en fonction de l'id de
     * son patient
     *
     * @param ch liste à retourner
     * @param id id recherché
     * @return description ou id d'une prescription
     * @throws SQLException id inconnu
     */
    public List<String> rechm(String ch, int id) throws SQLException {
        List<String> plusieurs = new ArrayList<>();
        String req = "select api_prescription.idpres as id,api_medecin.idmed as idmed,dateprescription||' '||api_medicament.nom||' '||api_info.quantite||' '||api_info.unite||' '||description as PRES\n"
                + "from api_medecin\n"
                + "join api_prescription\n"
                + "on api_medecin.idmed=api_prescription.idmed\n"
                + "join api_info\n"
                + "on api_prescription.idpres=api_info.idpres\n"
                + "join api_medicament\n"
                + "on api_info.idmedoc=api_medicament.idmedoc\n"
                + "where api_medecin.idmed=?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                boolean trouve = false;
                if (ch.equals("PRES")) {
                    while (rs.next()) {
                        trouve = true;
                        plusieurs.add(rs.getString("PRES"));
                    }
                } else {
                    while (rs.next()) {
                        trouve = true;
                        plusieurs.add("" + rs.getInt("ID"));
                    }
                }

                if (!trouve) {
                    throw new SQLException("id inconnu");
                } else {
                    return plusieurs;
                }
            }
        }
    }

    /**
     * méthode permettant de récupérer une prescription en fonction de l'id du médicament
     *
     * @param id id recherché
     * @return description d'une prescription
     * @throws SQLException id inconnu
     */
    public List<Integer> rechmed(int id) throws SQLException {
        List<Integer> plusieurs = new ArrayList<>();
        String req = "select api_prescription.idpres as id,api_medecin.idmed as idmed,dateprescription||' '||api_medicament.nom||' '||api_info.quantite||' '||api_info.unite||' '||description as PRES\n"
                + "from api_medecin\n"
                + "join api_prescription\n"
                + "on api_medecin.idmed=api_prescription.idmed\n"
                + "join api_info\n"
                + "on api_prescription.idpres=api_info.idpres\n"
                + "join api_medicament\n"
                + "on api_info.idmedoc=api_medicament.idmedoc\n"
                + "where api_medicament.idmedoc=?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                boolean trouve = false;
                while (rs.next()) {
                    trouve = true;
                    plusieurs.add(rs.getInt("ID"));
                }
                if (trouve) {
                    return plusieurs;
                } else {
                    throw new SQLException("id du médicament inconnu");
                }
            }
        }

    }
}
