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
import pharmacie.metier.Medicament;

public class MedicamentDAO extends DAO<Medicament> {

    /**
     * création d'un medicament sur base des valeurs de son objet métier
     *
     * @throws SQLException erreur de création
     * @param obj medicament à créer
     * @return medicament créé
     */
    @Override
    public Medicament create(Medicament obj) throws SQLException {

        String req1 = "insert into api_medicament(nom,description,stock) values(?,?,?)";
        String req2 = "select idmedoc from api_medicament where nom=?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm1.setString(1, obj.getNom());
            pstm1.setString(2, obj.getDescription());
            pstm1.setString(3, obj.getStock());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                throw new SQLException("erreur de creation du médicament, aucune ligne créée" + "1");
            }
            pstm2.setString(1, obj.getNom());
            try (ResultSet rs = pstm2.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("IDMEDOC");
                    obj.setId(id);
                    return read(id);
                } else {
                    throw new SQLException("erreur de création du médicament, record introuvable" + "2");
                }
            }
        }
    }

    /**
     * récupération des données d'un medicament sur base de son identifiant
     *
     * @throws SQLException code inconnu
     * @param id identifiant du medicament
     * @return medicament trouvé
     */
    @Override
    public Medicament read(int id) throws SQLException {
        String req = "select * from api_medicament where idmedoc = ?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {

                    String nom = rs.getString("NOM");
                    String description = rs.getString("DESCRIPTION");
                    String stock = rs.getString("STOCK");
                    return new Medicament(id, nom, description, stock);

                } else {
                    throw new SQLException("Code inconnu");
                }

            }
        }
    }

    /**
     * récupération des données d'un medicament sur base de son nom
     *
     * @throws SQLException code inconnu
     * @param nom nom du medicament
     * @return medicament trouvé
     */
    public Medicament read(String nom) throws SQLException {

        String req = "select * from api_medicament where nom = ?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setString(1, nom);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("IDMEDOC");
                    String nomm = rs.getString("NOM");
                    String description = rs.getString("DESCRIPTION");
                    String stock = rs.getString("STOCK");
                    return new Medicament(id, nomm, description, stock);

                } else {
                    throw new SQLException("Code inconnu");
                }

            }
        }
    }

    /**
     * mise à jour des données du medicament sur base de son identifiant
     *
     * @return Medicament
     * @param obj medicament à mettre à jour
     * @throws SQLException erreur de mise à jour
     */
    @Override
    public Medicament update(Medicament obj) throws SQLException {
        String req = "update api_medicament set nom=?,description=?,stock=?where idmedoc= ?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(4, obj.getId());
            pstm.setString(1, obj.getNom());
            pstm.setString(2, obj.getDescription());
            pstm.setString(3, obj.getStock());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new SQLException("aucune ligne médicament mise à jour");
            }
            return read(obj.getId());
        }
    }

    /**
     * effacement du medicament sur base de son identifiant
     *
     * @throws SQLException erreur d'effacement
     * @param obj medicament à effacer
     */
    @Override
    public void delete(Medicament obj) throws SQLException {
        String req = "delete from api_medicament where idmedoc= ?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(1, obj.getId());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new SQLException("aucune ligne médicament effacée");
            }

        }
    }

    /**
     * méthode permettant de récupérer tous les médicaments qui ont une
     * description ressemblante
     *
     * @param desc description partielle recherchée
     * @return liste de medicaments
     * @throws SQLException description inconnu
     */
    public List<Medicament> rech(String desc) throws SQLException {
        List<Medicament> plusieurs = new ArrayList<>();
        String req = "select * from api_medicament where description like ?";
        dbConnect = DBConnection.getConnection();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, "%" + desc + "%");
            try (ResultSet rs = pstm.executeQuery()) {
                boolean trouve = false;
                while (rs.next()) {
                    trouve = true;
                    int id = rs.getInt("IDMEDOC");
                    String nom = rs.getString("NOM");
                    String description = rs.getString("DESCRIPTION");
                    String stock = rs.getString("STOCK");
                    plusieurs.add(new Medicament(id, nom, description, stock));
                }

                if (!trouve) {
                    throw new SQLException("description inconnu");
                } else {
                    return plusieurs;
                }
            }
        }
    }
}
