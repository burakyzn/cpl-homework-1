/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package odev1;

import java.io.FileNotFoundException;

/**
*
* @author Burak Yazan <burak.yazan@sakarya.edu.tr>
* @since 2/27/2020
* <p>
* Odev1 sinifi projenin ana sinifidir.
* </p>
*/
public class Odev1 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        Analiz MetinDosyam = new Analiz();
        MetinDosyam.DosyaYukle("icerik.txt");
        MetinDosyam.AnaliziBaslat();
    }
}
