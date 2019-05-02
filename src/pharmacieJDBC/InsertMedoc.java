package pharmacieJDBC;

import java.sql.*;
import myconnection.DBConnection;
import java.util.*;

public class InsertMedoc {

    public void demo() {

        Scanner sc = new Scanner(System.in);
        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");

        String query1 = "insert into api_medicament (idmedoc,nom,description,stock) values(?,?,?,?)";
        String query2 = "select idmedoc from api_medicament where nom=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            System.out.println("Ajout d'un médicament :");

            System.out.print("Nom :");
            String nom = sc.nextLine();
            System.out.print("Description :");
            String desc = sc.nextLine();
            System.out.print("Stock :");
            String stk = sc.nextLine();
            System.out.print("Identifiant :");
            int id = sc.nextInt();
            System.out.println(nom+" "+desc+" "+stk+" "+id);
            sc.skip("\n");
            pstm1.setInt(1, id);
            pstm1.setString(2, nom);
            pstm1.setString(3, desc);
            pstm1.setString(4, stk);
            int nl = pstm1.executeUpdate();
            System.out.println(nl + "ligne insérée");
            pstm2.setString(1, nom);
            try (ResultSet rs = pstm2.executeQuery()) {

                if (rs.next()) {
                    int nc = rs.getInt(1);
                    System.out.println("numero du médicament =" + nc);

                } else {
                    System.out.println("erreur lors de l'insertion ,numero du médicament introuvable");
                }

            }
        } catch (SQLException e) {
            System.out.println("erreur SQL =" + e);
        }
         DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        InsertMedoc pgm = new InsertMedoc();
        pgm.demo();
    }
}
