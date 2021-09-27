package ch.uzh.softcon.zero;

import java.util.Scanner;

/**
 * Contributed to by the authors:
 * Elvio Petillo
 * Jerome Maier
 * Alessandro Stefanelli
 * Mete Polat
 */

public class Main {
    public static void main(String[] args){
        tdoc("", 0);
    }

    public static void tdoc(String newText, int day){
        if (day >= 12) {
            return;
        }
        String prologue = String.format("On the %d. day of Christmas\n" +
                "My true love sent to me:", day);
        System.out.println(prologue + newText);

        Scanner scan = new Scanner(System.in);
        String newLine = "\n" + scan.nextLine();

        tdoc(newLine + newText, day + 1);
    }
}
