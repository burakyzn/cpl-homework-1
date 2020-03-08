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
    private String metin; // nesnenin tuttugu ana metindir.
    // websitelerini ayirt etmek icin kullandigim regex cumlesi.
    private final String UrlRegexMetni = "((http?|https|ftp|file)://)?((W|w){3}.)?[a-zA-Z0-9]+\\.(com|edu|net|org)(\\.)?(tr)?";
    // sesli harfleri ayirt etmek icin kullandigim regex cumlesi
    private final String HarfRegexMetni = "[aeıiuüoö]";
    // mail adreslerini ayirt etmek icin kullandigim regex cumlesi
    private final String MailRegexMetni = "[a-zA-Z0-9_.]+\\@[a-zA-Z]+(\\.)([a-zA-Z]+)(\\.)?([a-zA-Z]+)?";
    
    // metin degiskenini ayarlamak icin bu fonksiyon kullanılıyor.
    private void setMetin(String p_metin){
        metin = p_metin.trim();
    }
    
    // metin degiskeni cagrilirken bu fonksiyon kullaniliyor.
    private String getMetin(){
        return metin;
    }
    
    // disaridan cagrilan DosyaYukle methodu aldigi dosya yolundaki dosyanin icindeki metni 
    // nesnin icerisine aktariyor. Eger dosya soylenen yerde yok ise kullaniciya hata donuluyor.
    public void DosyaYukle(String dosyaYolu) throws FileNotFoundException{
        try{
            Scanner fileIn = new Scanner(new File(dosyaYolu));

            String okunanMetin = "";
            while(fileIn.hasNext()){
                okunanMetin += fileIn.next() + " ";
            }
       
            setMetin(okunanMetin);
        }catch(FileNotFoundException e){
            System.out.println("Dosya yolu yanlış girildi.");
            setMetin("");
        }
    }
    
    // disaridan cagrilan analizi baslat sinifi metin uzerindeki analiz islemlerini baslatiyor ve ciktilari yazdiriyor.
    // eger kullanicinin gostermis oldugu dosya bos ise kullaniciya bilgilendirme mesajı donuyor ve islem bitiyor.
    public void AnaliziBaslat(){
        if(!metin.equals("")){
            System.out.println("Toplam Sesli Harf Sayısı : " + sesliHarfBul());
            System.out.println("Toplam Kelime Sayısı     : " + kelimeBul());
            System.out.println("Toplam Cümle Sayısı      : " + cumleBul());
            System.out.println("Toplam Mail Sayısı       : " + mailBul());
            System.out.println("Toplam Web Sitesi Sayısı : " + webSitesiBul());
        } else {
            System.out.println("Analizi yapılacak bir metin girilmedi.");
        }
    }
    
    // UrlRegexMetni ne gore metin icerisindeki web site linklerinin analizini yapiyor.
    private int webSitesiBul(){
        String p_metin = getMetin();
        
        Pattern urlRegex = Pattern.compile(UrlRegexMetni);
        Matcher urlMatcher;
        int toplamURLSayisi = 0;
        
        String[] kelimeListesi = p_metin.split(" ");
        
        for (String URL : kelimeListesi) {
            urlMatcher = urlRegex.matcher(URL);
            
            if(urlMatcher.find()){
                if(URL == null ? urlMatcher.group() == null : URL.equals(urlMatcher.group())){
                    toplamURLSayisi++;
                }
            }
        }
        
        return toplamURLSayisi;
    }
    
    // metnin kelime sayisini bosluklara gore hesaplayıp donduruyor.
    private int kelimeBul(){
        String p_metin = getMetin();
        return p_metin.split(" ").length;
    }
    
    // HarfRegexMetni ne gore metin icerisindeki sesli harflerin analizini yapiyor.
    private int sesliHarfBul(){
        String p_metin = getMetin();
        Pattern sesliHarfRegex = Pattern.compile(HarfRegexMetni, Pattern.CASE_INSENSITIVE);
        Matcher SesliHarfMatcher = sesliHarfRegex.matcher(p_metin);
        int sesliHarfSayisi = 0;
        
        while (SesliHarfMatcher.find()) {
            sesliHarfSayisi++;
        }
        
        return sesliHarfSayisi;
    }
    
    // mailRegexMetni ne gore metin icerisindeki mail adreslerinin analizini yapiyor.
    private int mailBul(){
        String p_metin = getMetin();
        
        Pattern mailRegex = Pattern.compile(MailRegexMetni);  
        Matcher mailMatcher;
        int toplamMailSayisi = 0;
        
        String[] kelimeListesi = p_metin.split(" ");
        
        for (String mail : kelimeListesi) {
            mailMatcher = mailRegex.matcher(mail);
            
            if(mailMatcher.find()){
                if(mail == null ? mailMatcher.group() == null : mail.equals(mailMatcher.group())){
                    toplamMailSayisi++;
                }
            }
        }
        
        return toplamMailSayisi;
    }
    
    // bu fonksiyon cumle icerisindeki web sitelerinin ve mail adreslerinin silinmesinden sonra
    // metindeki cumle sayisini buluyor ve geri donduruyor.
    private int cumleBul(){
        String p_metin = getMetin();
        
        p_metin = mailTemizle(p_metin);
        p_metin = webSitesiTemizle(p_metin);
        int noktaSayisi = 0;
        
        char[] metin_karakterleri = p_metin.toCharArray();
        
        for(int i=0;i<metin_karakterleri.length;i++){
            if(metin_karakterleri[i] == '.'){
                noktaSayisi++;
            }
        }
        
        return noktaSayisi;
    }
    
    // MailRegexMetni ne gore metin icerisindeki mail adreslerini bulup metnin icerisinden siliyor.
    private String mailTemizle(String p_metin){
        Pattern mailRegex = Pattern.compile(MailRegexMetni);  
        Matcher mailMatcher;
        
        String[] kelimeListesi = p_metin.split(" ");
        
        for (String mail : kelimeListesi) {
            mailMatcher = mailRegex.matcher(mail);
            
            if(mailMatcher.find()){
                if(mail == null ? mailMatcher.group() == null : mail.equals(mailMatcher.group())){
                    if(mailMatcher.group().equals(kelimeListesi[kelimeListesi.length - 1]) && kelimeListesi[kelimeListesi.length - 1].toCharArray()[kelimeListesi[kelimeListesi.length - 1].toCharArray().length - 1] == '.'){
                        p_metin = p_metin.replace(mailMatcher.group(), ".");
                    } else {
                        p_metin = p_metin.replace(mailMatcher.group(), "");
                    }
                }
            }
        }
        
        return p_metin;
    }
    
    // UrlRegexMetni ne gore metin icerisindeki web adreslerini bulup metnin icerisinden siliyor.
    private String webSitesiTemizle(String p_metin){
        Pattern urlRegex = Pattern.compile(UrlRegexMetni);  
        Matcher urlMatcher;
        
        String[] kelimeListesi = p_metin.split(" ");
        
        for (String URL : kelimeListesi) {
            urlMatcher = urlRegex.matcher(URL);
            
            if(urlMatcher.find()){
                if(URL == null ? urlMatcher.group() == null : URL.equals(urlMatcher.group())){
                    if(urlMatcher.group().equals(kelimeListesi[kelimeListesi.length - 1]) && kelimeListesi[kelimeListesi.length - 1].toCharArray()[kelimeListesi[kelimeListesi.length - 1].toCharArray().length - 1] == '.'){
                        p_metin = p_metin.replace(urlMatcher.group(), ".");
                    } else {
                        p_metin = p_metin.replace(urlMatcher.group(), "");
                    }
                }
            }
        }
        
        return p_metin;
    }
}
