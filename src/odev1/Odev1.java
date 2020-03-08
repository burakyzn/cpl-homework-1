/**
 * @author Burak Yazan <burak.yazan@ogr.sakarya.edu.tr>
 * @since 08/03/2020
 * <p> Odev sinifi projenin ana baslangic sinifir. </p>
 */
package odev1;

import java.io.FileNotFoundException;

public class Odev1 {


    public static void main(String[] args) throws FileNotFoundException {
        Analiz MetinDosyam = new Analiz(); // analiz sinifindan bir nesne uretiyorum.
        MetinDosyam.DosyaYukle("icerik.txt"); // urettigim bu nesneye analiz etmek istedigim dosyanin yolunu gonderiyorum.
        MetinDosyam.AnaliziBaslat(); // burada nesne uzerinde analizi baslatiyorum.
    }
}
