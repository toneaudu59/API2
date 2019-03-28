package demopharmacie;

import java.util.Scanner;
import java.util.ArrayList;

public class View {

    Scanner sc = new Scanner(System.in);

    public String verif(String regex) {
        String ch;
        do {
            ch = sc.nextLine();
            if (ch.matches(regex) == false) {
                print("Mauvaise syntaxe (" + regex + ")");
            }
        } while (ch.matches(regex) == false);
        return ch;
    }

    public int verifInt(String regex) {
        int i = Integer.parseInt(verif(regex));
        return i;
    }

    public void print(Object print) {
        System.out.println(print);
    }

    public void printArr(ArrayList A) {
        for (int i = 0; i < A.size(); i++) {
            print(A.get(i));
        }
    }
}
