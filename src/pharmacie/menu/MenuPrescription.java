package pharmacie.menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pharmacie.DAO.PrescriptionDAO;
import pharmacie.metier.Prescription;
import java.util.Scanner;
import java.time.LocalDate;
import pharmacie.DAO.PatientDAO;
import pharmacie.metier.Patient;
import demopharmacie.View;
import pharmacie.DAO.InfoDAO;
import pharmacie.DAO.MedecinDAO;
import pharmacie.DAO.MedicamentDAO;
import pharmacie.metier.Info;
import pharmacie.metier.Medecin;
import pharmacie.metier.Medicament;
public class MenuPrescription {

    PrescriptionDAO p = new PrescriptionDAO();
    Scanner sc = new Scanner(System.in);
    Prescription pr = new Prescription();
    View v=new View();
    public void menuP() {
        int ch = 10;
        while (ch != 0) {
            v.print("\t0.Retour\n\t1.Rechercher une prescription sur son patient\n\t2.Rechercher une prescription sur son id\n\t3.Nouvelle prescription(réservé médecin)");
            ch = v.verifInt("[0-3]");
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
                case 3:
                    AjoutP();
                    break;
                default:
                    v.print("Erreur de saisie entrez un chiffre entre 0 et 3");
            }
        }
    }

    public void AjoutP(){
        v.print("Ajouter une prescription");
        v.print("Votre matricule(0 pour annuler) :");
        String mat=sc.nextLine();
        if(!mat.equalsIgnoreCase("0")){
            MedecinDAO md=new MedecinDAO();
            Medecin m=new Medecin();
            boolean flag=false;
            try{
                m=md.read(mat); 
                flag=true;
            }catch(Exception e){
                v.print(e);
            }
            if(flag){
                v.print(m);
                v.print("Pour quel patient ?");
                v.print("Nom :");
                String nomp=sc.nextLine();
                v.print("Prénom :");
                String prenomp=sc.nextLine();
                v.print("Téléphone :");
                String tel=sc.nextLine();
                Patient pt=new Patient(0,nomp,prenomp,tel);
                PatientDAO ptd=new PatientDAO();
                flag=false;
                try{
                    pt=ptd.read(pt);
                    flag=true;
                }catch(Exception e){
                    v.print(e);
                }
                if(flag){//apres ici
                    v.print(pt);
                    pr=new Prescription(0,LocalDate.now(),m.getId(),pt.getId());
                    flag=false;
                    try{
                        p.create(pr);
                        flag=true;
                    }catch(Exception e){
                        v.print(e);
                    }
                     if(flag){
                        v.print("Médicament prescrit :");
                        String nomdoc=sc.nextLine();
                        Medicament mdoc=new Medicament();
                        MedicamentDAO mddoc=new MedicamentDAO();
                        flag=false;
                        try{
                            mdoc=mddoc.read(nomdoc);
                            flag=true;
                        }catch(Exception e){
                            v.print(e);
                        }
                        if(flag){
                            v.print("Quantité et unité de "+mdoc.getNom()+" prescrite :");
                            int qte=sc.nextInt();
                            String unite=sc.nextLine();
                            Info inf=new Info(0,qte,unite,mdoc.getId(),pr.getId());
                            InfoDAO ind=new InfoDAO();
                            flag=false;
                            try{
                                inf=ind.create(inf);
                                flag=true;
                            }catch(Exception e){
                                v.print(e);
                            } 
                            if(flag){
                                v.print("   Prescription :");
                            v.print("Médecin :"+m.getNom()+" "+m.getPrenom()+"          Téléphone :"+m.getTel());
                            v.print("Patient :"+pt.getNom()+" "+pt.getPrenom());
                            v.print(mdoc.getNom()+" "+inf.getQuantite()+inf.getUnite()+" : "+mdoc.getDescription());
                            }else{
                                try{
                                    p.delete(pr);
                                }catch(Exception e){
                                    v.print(e);
                                }
                            } 
                        }
                    }
                }
            }
        }else{
            v.print("Ajout supprimé");
        }
    }
    
    public void RechPi() {
        v.print("Rechercher une prescription");
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
                List<String> plusieurs = p.rechp(pa.getId(),"PRES");//TODO if(prescription!=NULL)
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

