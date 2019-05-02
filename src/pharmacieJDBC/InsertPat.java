package pharmacieJDBC;

import java.sql.*;
import myconnection.DBConnection;
import java.util.*;

public class InsertPat {

    public void demo() {

        Scanner sc = new Scanner(System.in);
        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");

        String query1 = "insert into api_patient (idpat,nom,prenom,tel) values(?,?,?,?)";
        String query2 = "select idpat from api_patient where nom=? and prenom=? and tel=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            System.out.println("Ajout d'un patient :");

            System.out.print("Nom :");
            String nom = sc.nextLine();
            System.out.print("Prenom :");
            String prenom = sc.nextLine();
            System.out.print("Telephone :");
            String tel = sc.nextLine();
            System.out.print("Identifiant :");
            int id = sc.nextInt();
            sc.skip("\n");
            pstm1.setInt(1, id);
            pstm1.setString(2, nom);
            pstm1.setString(3, prenom);
            pstm1.setString(4, tel);
            int nl = pstm1.executeUpdate();
            System.out.println(nl + "ligne insérée");
            pstm2.setString(1, nom);
            pstm2.setString(2, prenom);
            pstm2.setString(3, tel);
            try (ResultSet rs = pstm2.executeQuery()) {

                if (rs.next()) {
                    int nc = rs.getInt(1);
                    System.out.println("numero du patient =" + nc);

                } else {
                    System.out.println("erreur lors de l'insertion ,numero du patient introuvable");
                }

            }
        } catch (SQLException e) {
            System.out.println("erreur SQL =" + e);
        }
         DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        InsertPat pgm = new InsertPat();
        pgm.demo();
    }
}
