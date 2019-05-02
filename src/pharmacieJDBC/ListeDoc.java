package pharmacieJDBC;

import java.sql.*;
import myconnection.DBConnection;

public class ListeDoc {

    public void demo(){

        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");
        String query = "select * from API_medecin";
        try (Statement stmt = dbConnect.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String n = "" + rs.getInt("IDMED");
                System.out.println(nom + " " + prenom + " " + n);
            }
        } catch (SQLException e) {
            System.out.println("erreur SQL " + e);
        }
        
        DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        ListeDoc pgm = new ListeDoc();
        pgm.demo();
    }
}
