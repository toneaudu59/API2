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
            v.print("\t0.Retour\n\t1.Ajouter un médicament\n\t2.Modifier un médicament\n\t3.Supprimer un médicament\n\t4.Recherche sur la description\n\t5.Recherche sur le code");
            ch = v.verifInt("[0-5]");
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
                case 5:
                    RechC();
                    break;
                default:
                    v.print("Erreur de saisie entrez un chiffre entre 0 et 5");
            }
        }
    }

    public void RechM() {
        v.print("Recherche sur la description :");
        v.print("Description à rechercher :");
        String desc = sc.nextLine();
        try {
            List<Medicament> plusieurs = m.rechmd(desc);
            for (Medicament m : plusieurs) {
                System.out.println(m);
            }
        } catch (Exception e) {
            v.print("Description inconnue");
        }
    }
    
    public void RechC() {
        v.print("Recherche sur le code :");
        v.print("Code à rechercher :");
        String code = sc.nextLine();
        try {
            me = m.rechcode(code);
                System.out.println(me);
        } catch (Exception e) {
            v.print("Code inconnu");
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
                    pli = in.rech("" + me.getId());//récupère une liste d'info
                    
                } catch (Exception e) {
                    v.print("Pas d'info");
                }
                List<Integer> pres = new ArrayList<>();
                PrescriptionDAO pda = new PrescriptionDAO();
                List<Prescription> pr = new ArrayList<>();
                boolean flag=true;
                try {                   
                    pres = pda.rechmed(me.getId());//Récupère une liste d'id de prescription
                    v.print("Prescriptions :");
                    for (int i = 0; i < pres.size(); i++) {
                        v.print(pres.get(i).intValue());
                        pr.add(pda.read(pres.get(i).intValue()));
                        v.print(pr.get(i));
                    }
                    for (Prescription p : pr) {
                        v.print(p);
                    }
                } catch (Exception e) {
                    v.print("Pas de prescription");
                    flag=false;
                }
                String rep2;
                if(flag){
                    v.print("Attention si vous supprimez ce médicament ça supprimera les prescriptions liées");
                    v.print("Continuer ?o/n");
                    rep2 = sc.nextLine();
                }else rep2="o";
                
                if (rep2.equalsIgnoreCase("n")) {
                    v.print("Suppression annulée");
                } else {
                    if (pli!=null) {
                        try {
                            for (Info inf : pli) {
                                in.delete(inf);
                            }
                        } catch (Exception e) {
                            v.print("Suppression des infos impossible :" + e);
                        }
                        if (pr!=null) { 
                            try {
                                for (Prescription p : pr) {
                                    pda.delete(p);
                                }
                            } catch (Exception e) {
                                v.print("Impossible de supprimer les prescriptions :" + e);
                            }
                        }
                            try {
                                m.delete(me);
                                v.print("Médicament supprimé");
                            } catch (Exception e) {
                                v.print("Suppression impossible");
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
            v.print("Code :");
            String code = sc.nextLine();
            me = new Medicament(0, nom, desc, code);
            try {
                me = m.create(me);
                v.print(me);
            } catch (SQLException e) {
                v.print("erreur :" + e);
            }
        }

    }

    public void ModifM() {
        v.print("Modification d'un médicament :");
        v.print("Nom (0 pour annuler):");
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
                v.print("-Ancien code :" + me.getCode());
                v.print("Nouveau code :");
                String nvcode = sc.nextLine();
                if (nvcode.length() == 0) {
                    nvcode = me.getCode();
                }
                me.setCode(nvcode);
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
