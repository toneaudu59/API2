/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demopharmacie;

import pharmacie.menu.MenuMedecin;
import pharmacie.menu.MenuPatient;
import pharmacie.menu.MenuPrescription;
import pharmacie.menu.MenuMedicament;
import java.util.Scanner;
public class Pharmacie {

    Scanner sc = new Scanner(System.in);
    
    public void menu() {
        int ch = 10;
        while (ch != 0) {
            System.out.println("1.Patient\n2.Médecin\n3.Médicament\n4.Prescription");
            ch = sc.nextInt();
            switch (ch) {
                case 0:
                    ch=10;
                    break;
                case 1:
                    menuPat();
                    break;
                case 2:
                    menuMede();
                    break;
                case 3:
                    menuMed();
                    break;
                case 4:
                    menuPre();
                    break;
                default :
                    System.out.println("Chiffre entre 1 et 4");
            }
        }

    }

    public void menuPat() {
        MenuPatient mp = new MenuPatient();
        mp.menuP();
    }

    public void menuMed() {
        MenuMedicament med = new MenuMedicament();
        med.menuM();
    }

    public void menuPre(){
        MenuPrescription pre=new MenuPrescription();
        pre.menuP();
    }
    
    public void menuMede(){
        MenuMedecin med=new MenuMedecin();
        med.menuM();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pharmacie p = new Pharmacie();
        p.menu();
    }
}
