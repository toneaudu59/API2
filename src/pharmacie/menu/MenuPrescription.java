package pharmacie.menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pharmacie.DAO.PrescriptionDAO;
import pharmacie.metier.Prescription;
import java.util.Scanner;
import pharmacie.DAO.PatientDAO;
import pharmacie.metier.Patient;
import demopharmacie.View;
public class MenuPrescription {

    PrescriptionDAO p = new PrescriptionDAO();
    Scanner sc = new Scanner(System.in);
    Prescription pr = new Prescription();
    View v=new View();
    public void menuP() {
        int ch = 10;
        while (ch != 0) {
            v.print("\t0.Retour\n\t1.Rechercher une prescription sur son patient\n\t2.Rechercher une prescription sur son id");
            ch = v.verifInt("[0-2]");
            switch (ch) {
                case 0:
                    v.print("Retour");
                    break;
                case 1:
                    RechPp();
                    break;
                case 2:
                    RechPi();
                    break;
                default:
                    v.print("Erreur de saisie entrez un chiffre entre 0 et 2");
            }
        }
    }

    public void RechPi() {
        v.print("Rechercher une prescription :");
        v.print("Id à rechercher :");
        int id = sc.nextInt();
        sc.skip("\n");
        try {
            String pres = p.rech(id);
            System.out.println(pres);
        } catch (Exception e) {
            v.print("Recherche Impossible");
        }
    }

    public void RechPp() {
        v.print("Rechercher une prescription :");
        v.print("Nom du patient (0 pour annuler):");
        String nom = sc.nextLine();
        if (!nom.equalsIgnoreCase("0")) {
            v.print("Prénom :");
            String prenom = sc.nextLine();
            v.print("Téléphone :");
            String tel = sc.nextLine();
            Patient pa = new Patient(0, nom, prenom, tel);
            try {
                PatientDAO pad = new PatientDAO();
                pa = pad.read(pa);
                System.out.println(pa);
                List<String> plusieurs = p.rechp(pa.getId(),"PRES");
                for (String ps : plusieurs) {
                    System.out.println(ps);
                }
            } catch (SQLException e) {
                v.print("erreur :" + e);
            }
        }
    }
    public static void main(String[] args) {
        MenuPrescription p = new MenuPrescription();
        p.menuP();
    }
}

