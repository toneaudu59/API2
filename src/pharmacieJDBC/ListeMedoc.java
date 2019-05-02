package pharmacieJDBC;

import java.sql.*;
import myconnection.DBConnection;

public class ListeMedoc {

    public void demo(){

        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");
        String query = "select * from API_medicament where description like '%Sirop%'";
        try (Statement stmt = dbConnect.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("DESCRIPTION");
                String n = "" + rs.getInt("IDMEDOC");
                System.out.println(nom + " " + prenom + " " + n);
            }
        } catch (SQLException e) {
            System.out.println("erreur SQL " + e);
        }
        
        DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        ListeMedoc pgm = new ListeMedoc();
        pgm.demo();
    }
}
