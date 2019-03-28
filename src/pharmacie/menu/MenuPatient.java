package pharmacie.menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pharmacie.DAO.PatientDAO;
import pharmacie.metier.Patient;
import demopharmacie.View;
import java.util.Scanner;
import pharmacie.DAO.InfoDAO;
import pharmacie.DAO.PrescriptionDAO;
import pharmacie.metier.Info;
import pharmacie.metier.Prescription;

public class MenuPatient {

    PatientDAO p = new PatientDAO();
    Scanner sc = new Scanner(System.in);
    Patient pa = new Patient();
    View v = new View();

    public void menuP() {
        int ch = 10;
        while (ch != 0) {
            System.out.println("\t0.Retour\n\t1.Ajouter un patient\n\t2.Modifier un patient\n\t3.Supprimer un patient\n\t4.Recherche de patients");
            ch = v.verifInt("[0-4]");
            switch (ch) {
                case 0:
                    System.out.println("Retour");
                    break;
                case 1:
                    ajoutP();
                    break;
                case 2:
                    ModifP();
                    break;
                case 3:
                    SuppP();
                    break;
                case 4:
                    RechP();
                    break;
                default:
                    System.out.println("Erreur de saisie entrez un chiffre entre 0 et 4");
            }
        }
    }

    public void RechP() {
        System.out.println("Rechercher des patients :");
        System.out.println("Nom à rechercher :");
        String nom = sc.nextLine();
        try {
            List<Patient> plusieurs = p.rech(nom);
            for (Patient p : plusieurs) {
                System.out.println(p);
            }
        } catch (Exception e) {
            System.out.println("Recherche Impossible");
        }
    }

    public void SuppP() {
        System.out.println("Supprimer un patient :");
        System.out.println("Nom (0 pour annuler):");
        String nom = sc.nextLine();
        if (!nom.equalsIgnoreCase("0")) {
            System.out.println("Prénom :");
            String prenom = sc.nextLine();
            System.out.println("Téléphone :");
            String tel = sc.nextLine();
            pa = new Patient(0, nom, prenom, tel);
            try {
                pa = p.read(pa);
                v.print(pa);

                Boolean flag = false;
                PrescriptionDAO pd = new PrescriptionDAO();
                Prescription pr = new Prescription();
                List<String> plusieurs = new ArrayList<>();
                List<String> plusieurs2 = new ArrayList<>();
                InfoDAO in = new InfoDAO();
                List<Info> pli = new ArrayList<>();
                List<Info> pl = new ArrayList<>();
                try {
                    plusieurs = pd.rechp(pa.getId(), "ID");
                    plusieurs2 = pd.rechp(pa.getId(), "PRES");
                    for (int i = 0; i < plusieurs.size(); i++) {
                        v.print("Prescriptions :");
                        v.print(plusieurs2.get(i));
                        try {
                            pl = in.rech(Integer.parseInt(plusieurs.get(i)));
                            for (Info j : pl) {
                                pli.add(j);
                                flag = true;
                            }
                        } catch (Exception e) {
                            v.print("Erreur lors de la recherche des infos :" + e);
                        }
                    }
                } catch (Exception e) {
                    v.print("Erreur lors de la récupération des prescriptions:" + e);
                }
                v.print("Attention, si vous supprimez ce patient vous supprimez les prescriptions correspondantes");
                v.print("Continuer ?o/n");
                String rep = v.verif("o||n");
                if (rep.equalsIgnoreCase("o")) {
                    if (flag) {
                        flag = false;
                        try {
                            for (Info j : pl) {
                                in.delete(j);
                            }
                            flag = true;
                        } catch (Exception e) {
                            v.print("Erreur lors de la suppression des infos :" + e);
                        }
                        if (flag) {
                            flag = false;
                            try {
                                for (int i = 0; i < plusieurs.size(); i++) {
                                    pr = new Prescription(Integer.parseInt(plusieurs.get(i)), 0, 0);
                                    pd.delete(pr);
                                }
                                flag = true;
                            } catch (Exception e) {
                                v.print("Erreur dans la suppression des prescriptions :" + e);
                            }
                            if (flag) {
                                try {
                                    p.delete(pa);
                                    System.out.println("Patient supprimé");
                                } catch (Exception e) {
                                    System.out.println("Suppression impossible :" + e);
                                }
                            }

                        }

                    } else {
                        v.print("Erreur lors de la suppression");
                    }

                } else {
                    v.print("Suppression annulée");
                }
            } catch (Exception e) {
                System.out.println("Patient introuvable :" + e);
            }
        }

    }

    public void ajoutP() {

        System.out.println("Nouveau patient :");
        System.out.println("Nom (0 pour annuler):");
        String nom = sc.nextLine();
        if (!nom.equalsIgnoreCase("0")) {
            System.out.println("Prénom :");
            String prenom = sc.nextLine();
            System.out.println("Téléphone :");
            String tel = sc.nextLine();
            pa = new Patient(0, nom, prenom, tel);
            try {
                pa = p.create(pa);
                System.out.println("patient : " + pa);
            } catch (SQLException e) {
                System.out.println("erreur :" + e);
            }
        }

    }

    public void ModifP() {
        v.print("Modification d'un patient :");
        v.print("Nom (0 pour annuler):");
        String nomp = sc.nextLine();
        if (!nomp.equalsIgnoreCase("0")) {
            v.print("Prenom :");
            String prenomp = sc.nextLine();
            v.print("Téléphone :");
            String telp = sc.nextLine();
            pa = new Patient(0, nomp, prenomp, telp);
            try {
                pa = p.read(pa);
            } catch (Exception e) {
                v.print("Client introuvable");
            }
            v.print("Modification (Enter pour ne pas modifier) :");
            v.print("-Ancien nom :" + nomp);
            v.print("Nouveau nom :");
            String nvnom = sc.nextLine();
            if (nvnom.length() == 0) {
                nvnom = nomp;
            }
            pa.setNom(nvnom);
            v.print("-Ancien prenom :" + nomp);
            v.print("Nouveau prenom :");
            String nvprenom = sc.nextLine();
            if (nvprenom.length() == 0) {
                nvprenom = prenomp;
            }
            pa.setPrenom(nvprenom);
            v.print("-Ancien téléphone :" + nomp);
            v.print("Nouveau téléphone :");
            String nvtel = sc.nextLine();
            if (nvtel.length() == 0) {
                nvtel = telp;
            }
            pa.setTel(nvtel);
            try {
                p.update(pa);
            } catch (SQLException e) {
            }
        }
    }

    public static void main(String[] args) {
        MenuPatient p = new MenuPatient();
        p.menuP();
    }
}
