package pharmacie.DAO;

/**
 * classe de mappage poo-relationnel client
 *
 * @author Arnaud Laffineur
 * @version 1.0
 * @see Medecin
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import myconnection.DBConnection;
import pharmacie.metier.Info;

public class InfoDAO extends DAO<Info> {

    /**
     * création d'une info sur base des valeurs de son objet métier
     *
     * @throws SQLException erreur de création
     * @param obj info à créer
     * @return info créée
     */
    @Override
    public Info create(Info obj) throws SQLException {

        String req1 = "insert into api_info(quantite,unite,idmedoc,idpres) values(?,?,?,?)";
        String req2 = "select idinfo from api_info where idpres=? and unite=?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm1.setInt(1, obj.getQuantite());
            pstm1.setString(2, obj.getUnite());
            pstm1.setInt(3, obj.getIdmedoc());;
            pstm1.setInt(4, obj.getIdpres());
            int n = pstm1.executeUpdate();
            
            if (n == 0) {
                throw new SQLException("erreur de creation de l'info, aucune ligne créée");
            }
            pstm2.setInt(1, obj.getIdpres());
            pstm2.setString(2, obj.getUnite());
            try (ResultSet rs = pstm2.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("IDINFO");
                    obj.setId(id);
                    return read(id);
                } else {
                    throw new SQLException("erreur de création de l'info, record introuvable");
                }
            }
        }
    }

    /**
     * récupération des données d'une info sur base de son identifiant
     *
     * @throws SQLException code inconnu
     * @param id identifiant de l'info
     * @return info trouvé
     */
    @Override
    public Info read(int id) throws SQLException {

        String req = "select * from api_info where idinfo = ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    int quantite = rs.getInt("QUANTITE");
                    String unite = rs.getString("UNITE");
                    int idmedoc = rs.getInt("IDMEDOC");
                    int idpres = rs.getInt("IDPRES");
                    return new Info(id, quantite,unite,idmedoc,idpres);

                } else {
                    throw new SQLException("Code inconnu");
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
    public Info update(Info obj) throws SQLException {
        String req = "update api_info set quantite=?,unite=?,idmedoc=?,idpres=? where idinfo= ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(5, obj.getId());
            pstm.setInt(1, obj.getQuantite());
            pstm.setString(2, obj.getUnite());
            pstm.setInt(3, obj.getIdmedoc());
            pstm.setInt(4, obj.getIdpres());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new SQLException("aucune ligne info mise à jour");
            }
            return read(obj.getId());
        }
    }

    /**
     * effacement d'une info sur base de son identifiant
     *
     * @throws SQLException erreur d'effacement
     * @param obj info à effacer
     */
    @Override
    public void delete(Info obj) throws SQLException {

        String req = "delete from api_info where idinfo= ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            
            pstm.setInt(1, obj.getId());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new SQLException("aucune ligne info effacée");
            }

        }
    }
    
    /**
     * méthode permettant de récupérer toutes les infos en fonction de l'id de la prescription
     * @param id id recherché
     * @return liste d'infos
     * @throws SQLException id inconnu
     */
    public List<Info> rech(int id) throws SQLException {
        List<Info> plusieurs = new ArrayList<>();
        String req = "select * from api_info where idpres = ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                boolean trouve = false;
                while (rs.next()) {
                    trouve = true;
                    int idi = rs.getInt("IDINFO");
                    int quantite = rs.getInt("QUANTITE");
                    String unite = rs.getString("UNITE");
                    int idmedoc = rs.getInt("IDMEDOC");
                    int idpres = rs.getInt("IDPRES");
                    plusieurs.add(new Info(idi, quantite, unite, idmedoc, idpres));
                }

                if (!trouve) {
                    throw new SQLException("id prescription inconnu");
                } else {
                    return plusieurs;
                }
            }
        }
        
        
    }
    
    /**
     * méthode permettant de récupérer toutes les infos en fonction de l'id de la prescription
     * @param id id recherché
     * @return liste d'infos
     * @throws SQLException id inconnu
     */
    public List<Info> rech(String id) throws SQLException {
        List<Info> plusieurs = new ArrayList<>();
        String req = "select * from api_info where idmedoc = ?";
        dbConnect=DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = pstm.executeQuery()) {
                boolean trouve = false;
                while (rs.next()) {
                    trouve = true;
                    int idi = rs.getInt("IDINFO");
                    int quantite = rs.getInt("QUANTITE");
                    String unite = rs.getString("UNITE");
                    int idmedoc = rs.getInt("IDMEDOC");
                    int idpres = rs.getInt("IDPRES");
                    plusieurs.add(new Info(idi, quantite, unite, idmedoc, idpres));
                }

                if (!trouve) {
                    throw new SQLException("id prescription inconnu");
                } else {
                    return plusieurs;
                }
            }
        }
        
        
    }
}
