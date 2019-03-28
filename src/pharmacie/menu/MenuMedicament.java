package pharmacie.menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pharmacie.DAO.MedicamentDAO;
import pharmacie.metier.Medicament;
import demopharmacie.View;
import java.util.Scanner;
import pharmacie.DAO.InfoDAO;
import pharmacie.DAO.PrescriptionDAO;
import pharmacie.metier.Info;
import pharmacie.metier.Prescription;
import pharmacie.metier.Vue_somme_medicament_prescrit;

public class MenuMedicament {

    MedicamentDAO m = new MedicamentDAO();
    Scanner sc = new Scanner(System.in);
    Medicament me = new Medicament();
    View v = new View();

    public void menuM() {
        int ch = 10;
        while (ch != 0) {
            v.print("\t0.Retour\n\t1.Ajouter un médicament\n\t2.Modifier un médicament\n\t3.Supprimer un médicament\n\t4.Rechercher des médicaments");
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
        v.print("Rechercher des médicaments :");
        v.print("Description à rechercher :");
        String desc = sc.nextLine();
        try {
            List<Vue_somme_medicament_prescrit> plusieurs = m.rech(desc);
            for (Vue_somme_medicament_prescrit m : plusieurs) {
                System.out.println(m);
            }
        } catch (Exception e) {
            v.print("Recherche Impossible" + e);
        }
    }

    public void SuppM() {
        v.print("Supprimer un médicament :");
        v.print("Nom (0 pour annuler):");
        String nom = sc.nextLine();
        if (!nom.equalsIgnoreCase("0")) {
            try {
                me = m.read(nom);
                InfoDAO in = new InfoDAO();
                List<Info> pli = new ArrayList<>();
                try {
                    pli = in.rech("" + me.getId());
                } catch (Exception e) {
                    v.print("Impossible de retrouver les infos :" + e);
                }
                List<Integer> pres = new ArrayList<>();
                PrescriptionDAO pda = new PrescriptionDAO();
                List<Prescription> pr = new ArrayList<>();
                v.print("Prescriptions :");
                boolean flag = false;
                try {                   
                    pres = pda.rechmed(me.getId());
                    for (int i = 0; i < pres.size(); i++) {
                        v.print(pres.get(i).intValue());
                        pr.add(pda.read(pres.get(i).intValue()));
                        v.print(pr.get(i));
                    }
                    for (Prescription p : pr) {
                        v.print(p);
                    }
                    flag = true;
                } catch (Exception e) {
                    v.print("Prescription introuvable :"+e);
                }
                v.print("Attention si vous supprimez ce médicament ça supprimera les prescriptions liées");
                v.print("Continuer ?o/n");
                String rep2 = sc.nextLine();
                if (rep2.equalsIgnoreCase("n")) {
                    v.print("Suppression annulée");
                } else {
                    if (flag) {
                        flag = false;
                        try {
                            for (Info inf : pli) {
                                in.delete(inf);
                            }
                            flag = true;
                        } catch (Exception e) {
                            v.print("Suppression des infos impossible :" + e);
                        }
                        if (flag) { 
                            flag = false;
                            try {
                                for (Prescription p : pr) {
                                    pda.delete(p);
                                }
                                flag = true;
                            } catch (Exception e) {
                                v.print("Impossible de supprimer les prescriptions :" + e);
                            }
                        }
                        if (flag) {
                            try {
                                m.delete(me);
                                v.print("Médicament supprimé");
                            } catch (Exception e) {
                                v.print("Suppression impossible");
                            }
                        }

                    }

                }
            } catch (Exception e) {
                v.print("Médicament introuvable");
            }
        }

    }

    public void ajoutM() {

        v.print("Nouveau médicament :");
        v.print("Nom (0 pour annuler):");
        String nom = sc.nextLine();
        if (!nom.equalsIgnoreCase("0")) {
            v.print("Description :");
            String desc = sc.nextLine();
            v.print("Stock :");
            String stk = sc.nextLine();
            me = new Medicament(0, nom, desc, stk);
            try {
                me = m.create(me);
                v.print(me);
            } catch (SQLException e) {
                v.print("erreur :" + e + " ok");
            }
        }

    }

    public void ModifM() {
        v.print("Modification d'un médicament :");
        v.print("Nom (0 pour annuler):");//TODO 0 pour annuler
        String nom = sc.nextLine();
        if (!nom.equalsIgnoreCase("0")) {
            try {
                me = m.read(nom);
                v.print("Modification (Enter pour ne pas modifier) :");
                v.print("-Ancien nom :" + me.getNom());
                v.print("Nouveau nom :");
                String nvnom = sc.nextLine();
                if (nvnom.length() == 0) {
                    nvnom = me.getNom();
                }
                me.setNom(nvnom);
                v.print("-Ancienne description :" + me.getDescription());
                v.print("Nouvele description :");
                String nvdesc = sc.nextLine();
                if (nvdesc.length() == 0) {
                    nvdesc = me.getDescription();
                }
                me.setDescription(nvdesc);
                v.print("-Ancien stock :" + me.getStock());
                v.print("Nouveau stock :");
                String nvstk = sc.nextLine();
                if (nvstk.length() == 0) {
                    nvstk = me.getStock();
                }
                me.setStock(nvstk);
                try {
                    m.update(me);
                    v.print("Médicament modifié");
                    v.print("Médicament :" + me);
                } catch (SQLException e) {
                    v.print("erreur :" + e);
                }
            } catch (Exception e) {
                v.print("Médicament introuvable");
            }
        }
    }

    public static void main(String[] args) {
        MenuMedicament m = new MenuMedicament();
        m.menuM();
    }
}
