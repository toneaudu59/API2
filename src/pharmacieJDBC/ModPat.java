package pharmacieJDBC;

import java.util.*;

import java.sql.*;
import myconnection.DBConnection;

public class ModPat {

    public ModPat() {
       PreparedStatement stm=null,Verifcl=null;
       
       Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(0);
        }
        System.out.println("connexion établie");
        try {
            Scanner sc = new Scanner(System.in);
            dbConnect.setAutoCommit(false);
            stm = dbConnect.prepareStatement(
                    "UPDATE api_patient SET nom = ?, prenom = ?, tel = ? WHERE idpat = ?");
            Verifcl = dbConnect.prepareStatement(
                    "SELECT * FROM api_patient WHERE nom=? and prenom=? and tel=? FOR UPDATE WAIT 20 ");//ou nowait
            System.out.println("Nom (0 pour terminer) :");
            String nomp=sc.nextLine();
            while (!nomp.equalsIgnoreCase("0")) {
                try {
                    System.out.println("Prenom :");
                    String prenomp=sc.nextLine();
                    System.out.println("Téléphone :");
                    String telp=sc.nextLine();
                    Verifcl.setString(1, nomp);
                    Verifcl.setString(2, prenomp);
                    Verifcl.setString(3, telp);
                    ResultSet rs = Verifcl.executeQuery();
                    if (rs.next()) {
                        int idpat =rs.getInt("IDPAT");
                        System.out.println("Modification (Enter pour ne pas modifier) :");
                        System.out.println("-Ancien nom :" + nomp);
                        System.out.println("Nouveau nom :");
                        String nvnom = sc.nextLine();
                        if(nvnom.length()==0) nvnom=nomp;
                        System.out.println("-Ancien prenom :" + nomp);
                        System.out.println("Nouveau prenom :");
                        String nvprenom = sc.nextLine();
                        if(nvprenom.length()==0) nvprenom=prenomp;
                        System.out.println("-Ancien téléphone :" + nomp);
                        System.out.println("Nouveau téléphone :");
                        String nvtel = sc.nextLine();
                        if(nvtel.length()==0) nvtel=telp;
                        stm.setString(1, nvnom);
                        stm.setString(2, nvprenom);
                        stm.setString(3, nvtel);
                        stm.setInt(4, idpat);
                        int nl = stm.executeUpdate();
                        System.out.println(nl + "ligne modifiée");
                        System.out.println("Confirmez-vous la mise à jour (o/n) ?");
                        char rep = sc.nextLine().charAt(0);
                        if (rep == 'o') {
                            dbConnect.commit();
                        } else {
                            try {
                                dbConnect.rollback();
                            } catch (Exception e) {
                                System.out.println("erreur lors de l'annulation : " + e);
                            }
                        }
                    } else {
                        System.out.println("Patient introuvable ");
                    }
                } //fin du try interne
                catch (SQLException e) {

                    if (e.getErrorCode() != 30006) {
                        System.out.println("erreur SQL =" + e);
                        break;
                    } else {
                        System.out.println(
                                "Record verrouillé par une autre application ");
                    }
                }//fin du catch SQL
            System.out.println("Nom (0 pour terminer) :");
            nomp=sc.nextLine();
            }//fin du while
        } //fin du try principal
        catch (Exception e) {
            System.out.println("erreur =" + e);
           
        }

        finally {
            //finalement fermer les ressources
           
            try {
                if (stm!= null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.out.println("erreur de fermeture de statement " + e);
            }
            try {
                if (Verifcl != null) {
                    Verifcl.close();
                }
            } catch (SQLException e) {
                System.out.println("erreur de fermeture de statement " + e);
            }

            try {
                if (dbConnect != null) {
                    dbConnect.close();
                }
            } catch (SQLException e) {
                System.out.println("erreur de fermeture de connexion " + e);
            }
        }
    }

    public static void main(String[] args) {

        ModPat pgm = new ModPat();

    }

}
