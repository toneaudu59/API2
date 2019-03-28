package pharmacie.menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pharmacie.DAO.MedecinDAO;
import pharmacie.metier.Medecin;
import demopharmacie.View;
import java.util.Scanner;
import pharmacie.DAO.InfoDAO;
import pharmacie.DAO.PrescriptionDAO;
import pharmacie.metier.Info;
import pharmacie.metier.Prescription;

public class MenuMedecin {

    MedecinDAO m = new MedecinDAO();
    Scanner sc = new Scanner(System.in);
    Medecin me = new Medecin();
    View v = new View();

    public void menuM() {
        int ch = 10;
        while (ch != 0) {
            v.print("\t0.Retour\n\t1.Ajouter un médecin\n\t2.Modifier un médecin\n\t3.Supprimer un médecin\n\t4.Recherche de médecins");
            ch = v.verifInt("[0-4]");
            switch (ch) {
                case 0:
                    v.print("Retour");
                    break;
                case 1:
                    ajoutM();
                    break;
                case 2:
                    ModifM();
                    break;
                case 3:
                    SuppM();
                    break;
                case 4:
                    RechM();
                    break;
                default:
                    v.print("Erreur de saisie entrez un chiffre entre 0 et 4");
            }
        }
    }

    public void RechM() {
        v.print("Rechercher de médecins :");
        v.print("Nom à rechercher :");
        String nom = sc.nextLine();
        try {
            List<Medecin> plusieurs = m.rech(nom);
            for (Medecin p : plusieurs) {
                System.out.println(p);
            }
        } catch (Exception e) {
            v.print("Recherche Impossible");
        }
    }

    public void SuppM() {
        v.print("Supprimer un médecin :");
        v.print("Nom (0 pour annuler):");
        String nom = sc.nextLine();
        if (!nom.equalsIgnoreCase("0")) {
            v.print("Prénom :");
            String prenom = sc.nextLine();
            v.print("Téléphone :");
            String tel = sc.nextLine();
            me = new Medecin(0, "", nom, prenom, tel);
            try {
                me = m.read(me);
                v.print(me);

                Boolean flag = false;
                PrescriptionDAO pd = new PrescriptionDAO();
                Prescription pr = new Prescription();
                List<String> plusieurs = new ArrayList<>();
                List<String> plusieurs2 = new ArrayList<>();
                InfoDAO in = new InfoDAO();
                List<Info> pli = new ArrayList<>();
                List<Info> pl = new ArrayList<>();
                try {
                    plusieurs = pd.rechm("ID", me.getId());
                    plusieurs2 = pd.rechm("PRES", me.getId());
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
                v.print("Attention, si vous supprimez ce médecin vous supprimez les prescriptions correspondantes");
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
                                    m.delete(me);
                                    System.out.println("Médecin supprimé");
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
                System.out.println("Médecin introuvable :" + e);
            }
        }
    }

    public void ajoutM() {

        v.print("Nouveau patient :");
        v.print("Nom (0 pour annuler):");
        String nom = sc.nextLine();
        if (!nom.equalsIgnoreCase("0")) {
            v.print("Prénom :");
            String prenom = sc.nextLine();
            v.print("Téléphone :");
            String tel = sc.nextLine();
            v.print("Matricule :");
            String matricule = sc.nextLine();
            me = new Medecin(0, matricule, nom, prenom, tel);
            try {
                me = m.create(me);
                v.print("Médecin : " + me);
            } catch (SQLException e) {
                v.print("erreur :" + e);
            }
        }

    }

    public void ModifM() {
        v.print("Modification d'un patient :");
        v.print("Nom(0 pour annuler) :");
        String nom = sc.nextLine();
        if (!nom.equalsIgnoreCase("0")) {
            v.print("Prenom :");
            String prenom = sc.nextLine();
            v.print("Téléphone :");
            String tel = sc.nextLine();
            me = new Medecin(0, "", nom, prenom, tel);
            try {
                me = m.read(me);
            } catch (Exception e) {
                v.print("Client introuvable");
            }
            v.print("Modification (Enter pour ne pas modifier) :");
            v.print("-Ancien nom :" + nom);
            v.print("Nouveau nom :");
            String nvnom = sc.nextLine();
            if (nvnom.length() == 0) {
                nvnom = nom;
            }
            me.setNom(nvnom);
            v.print("-Ancien prenom :" + nom);
            v.print("Nouveau prenom :");
            String nvprenom = sc.nextLine();
            if (nvprenom.length() == 0) {
                nvprenom = prenom;
            }
            me.setPrenom(nvprenom);
            v.print("-Ancien téléphone :" + nom);
            v.print("Nouveau téléphone :");
            String nvtel = sc.nextLine();
            if (nvtel.length() == 0) {
                nvtel = tel;
            }
            me.setTel(nvtel);
            v.print("-Ancien matricule :" + me.getMatricule());
            v.print("Nouveau matricule :");
            String nvmat = sc.nextLine();
            if (nvmat.length() == 0) {
                nvmat = me.getMatricule();
            }
            me.setTel(nvtel);
            try {
                m.update(me);
            } catch (SQLException e) {
            }
        }
    }

    public static void main(String[] args) {
        MenuMedecin m = new MenuMedecin();
        m.menuM();
    }
}
