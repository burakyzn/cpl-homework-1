/**
 * @author Burak Yazan <burak.yazan@ogr.sakarya.edu.tr>
 * @since 08/03/2020
 * <p> Analiz sinifi disaridan aldigi dosya yolundaki dosyayi okur icerigindeki metni analiz eder ve cikti uretir. </p>
 */
package odev1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analiz {
    // websitelerini ayirt etmek icin kullandigim regex cumlesi.
    private final String UrlRegexMetni = "((http?|https|ftp|file)://)?((W|w){3}.)?[a-zA-Z0-9]+\\.(com|edu|net|org)(\\.)?(tr)?";
    // sesli harfleri ayirt etmek icin kullandigim regex cumlesi
    private final String HarfRegexMetni = "[aeıiuüoö]";
    // mail adreslerini ayirt etmek icin kullandigim regex cumlesi
    private final String MailRegexMetni = "[a-zA-Z0-9_.]+\\@[a-zA-Z]+(\\.)([a-zA-Z]+)(\\.)?([a-zA-Z]+)?";
    
    String[] kelimeListesi;
        
    // disaridan cagrilan DosyaYukle methodu aldigi dosya yolundaki dosyanin icindeki metni 
    // nesnin icerisine aktariyor. Eger dosya soylenen yerde yok ise kullaniciya hata donuluyor.
    public void DosyaYukle(String dosyaYolu) throws FileNotFoundException{
        String okunanMetin = "";
        
        try{
            Scanner fileIn = new Scanner(new File(dosyaYolu));
            
            while(fileIn.hasNext()){
                okunanMetin += fileIn.next() + " ";
            }
       
            kelimeListesi = okunanMetin.split(" ");
        }catch(FileNotFoundException e){
            System.out.println("Dosya yolu yanlış girildi.");
            kelimeListesi = okunanMetin.split(" ");
        }
    }
    
    // disaridan cagrilan analizi baslat sinifi metin uzerindeki analiz islemlerini baslatiyor ve ciktilari yazdiriyor.
    // eger kullanicinin gostermis oldugu dosya bos ise kullaniciya bilgilendirme mesajı donuyor ve islem bitiyor.
    public void AnaliziBaslat(){
        if(kelimeListesi.equals("") || ((kelimeListesi.length - 1)  == 0 && kelimeListesi[0] == "")){
            System.out.println("Analizi yapılacak bir metin girilmedi.");
        } else {
            
            for(int i=0;i<kelimeListesi.length;i++){
                System.out.println(kelimeListesi[i]);
            }
            
            int sesliHarfSayisi = sesliHarfBul();
            int kelimeSayisi = kelimeBul();
            int mailSayisi = mailBul();
            int webSitesiSayisi = webSitesiBul();
            int cumleSayisi = cumleBul();
            
            System.out.println("Toplam Sesli Harf Sayısı : " + sesliHarfSayisi);
            System.out.println("Toplam Kelime Sayısı     : " + kelimeSayisi);
            System.out.println("Toplam Cümle Sayısı      : " + cumleSayisi);
            System.out.println("Toplam Mail Sayısı       : " + mailSayisi);
            System.out.println("Toplam Web Sitesi Sayısı : " + webSitesiSayisi);
        }
    }
    
    // metnin kelime sayisini bosluklara gore hesaplayıp donduruyor.
    private int kelimeBul(){
        return kelimeListesi.length;
    }
    
    // HarfRegexMetni ne gore metin icerisindeki sesli harflerin analizini yapiyor.
    private int sesliHarfBul(){
        Pattern sesliHarfRegex = Pattern.compile(HarfRegexMetni, Pattern.CASE_INSENSITIVE);
        
        Matcher SesliHarfMatcher;
        int sesliHarfSayisi = 0;
        
        for (String kelimeListesi1 : kelimeListesi) {
            SesliHarfMatcher = sesliHarfRegex.matcher(kelimeListesi1);
            while(SesliHarfMatcher.find()){
                sesliHarfSayisi++;
            }
        }
        
        return sesliHarfSayisi;
    }
 
    // bu fonksiyon cumle icerisindeki web sitelerinin ve mail adreslerinin silinmesinden sonra
    // metindeki cumle sayisini buluyor ve geri donduruyor.
    private int cumleBul(){
        int noktaSayisi = 0;
        
        for(int i=0;i<kelimeListesi.length;i++){
            char[] metin_karakterleri = kelimeListesi[i].toCharArray();
            
            for(int j=0;j<metin_karakterleri.length;j++){
                if(metin_karakterleri[j] == '.'){
                    noktaSayisi++;
                }
            }
        }
       
        return noktaSayisi;
    }
    
    // MailRegexMetni ne gore mail icindeki mail adreslerinin analizini yapiyor ve metnin icinden kaldiriyor.
    private int mailBul(){
        Pattern mailRegex = Pattern.compile(MailRegexMetni);  
        Matcher mailMatcher;
        int toplamMailSayisi = 0;
        
        for (int i=0;i<kelimeListesi.length;i++) {
            mailMatcher = mailRegex.matcher(kelimeListesi[i]);
            
            if(mailMatcher.find()){
                if(kelimeListesi[i] == null ? mailMatcher.group() == null : kelimeListesi[i].equals(mailMatcher.group())){
                    if(mailMatcher.group().equals(kelimeListesi[kelimeListesi.length - 1]) && kelimeListesi[kelimeListesi.length - 1].toCharArray()[kelimeListesi[kelimeListesi.length - 1].toCharArray().length - 1] == '.'){
                        kelimeListesi[i] = ".";
                        toplamMailSayisi++;
                    } else {
                        kelimeListesi[i] = "";
                        toplamMailSayisi++;
                    }
                }
            }
        }
        
        return toplamMailSayisi;
    }
    
    // UrlRegexMetni ne gore metin icindeki web sitelerinin analizini yapiyor ve metnin icinden kaldiriyor.
    private int webSitesiBul(){
        Pattern urlRegex = Pattern.compile(UrlRegexMetni);  
        Matcher urlMatcher;
        
        int toplamURLSayisi = 0;
        
        for (int i=0;i<kelimeListesi.length;i++) {
            urlMatcher = urlRegex.matcher(kelimeListesi[i]);
            
            if(urlMatcher.find()){
                if(kelimeListesi[i] == null ? urlMatcher.group() == null : kelimeListesi[i].equals(urlMatcher.group())){
                    if(urlMatcher.group().equals(kelimeListesi[kelimeListesi.length - 1]) && kelimeListesi[kelimeListesi.length - 1].toCharArray()[kelimeListesi[kelimeListesi.length - 1].toCharArray().length - 1] == '.'){
                        kelimeListesi[i] = ".";
                        toplamURLSayisi++;
                    } else {
                        kelimeListesi[i] = "";
                        toplamURLSayisi++;
                    }
                }
            }
        }
        
        return toplamURLSayisi;
    }

}
