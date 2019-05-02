package pharmacieJDBC;

import java.sql.*;
import myconnection.DBConnection;
import java.util.*;

public class InsertDoc {

    public void demo() {

        Scanner sc = new Scanner(System.in);
        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");

        String query1 = "insert into api_medecin (idmed,matricule,nom,prenom,tel) values(?,?,?,?,?)";
        String query2 = "select idmed from api_medecin where nom=? and prenom=? and tel=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            int numcli = 0;
            System.out.println("Ajout d'un médecin :");

            System.out.print("Nom :");
            String nom = sc.nextLine();
            System.out.print("Prenom :");
            String prenom = sc.nextLine();
            System.out.print("Matricule :");
            String mat = sc.nextLine();
            System.out.print("Telephone :");
            String tel = sc.nextLine();
            System.out.print("Identifiant :");
            int id = sc.nextInt();
            sc.skip("\n");
            pstm1.setInt(1, id);
            pstm1.setString(2, mat);
            pstm1.setString(3, nom);
            pstm1.setString(4, prenom);
            pstm1.setString(5, tel);
            int nl = pstm1.executeUpdate();//ici
            System.out.println(nl + "ligne insérée");
            pstm2.setString(1, nom);
            pstm2.setString(2, prenom);
            pstm2.setString(3, tel);
            try (ResultSet rs = pstm2.executeQuery()) {

                if (rs.next()) {
                    int nc = rs.getInt(1);
                    System.out.println("numero du médecin =" + nc);

                } else {
                    System.out.println("erreur lors de l'insertion ,numero du médecin introuvable");
                }

            }
        } catch (SQLException e) {
            System.out.println("erreur SQL =" + e);
        }
         DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        InsertDoc pgm = new InsertDoc();
        pgm.demo();
    }
}
