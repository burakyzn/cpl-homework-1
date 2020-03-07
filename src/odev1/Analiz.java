/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package odev1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
*
* @author Burak Yazan <burak.yazan@sakarya.edu.tr>
* @since 2/27/2020
* <p>
* Analiz sinifi aldigi dosyanin sesli harf 
* kelime cumle mail web sitesi analizini yapar.
* </p>
*/

public class Analiz {
    private String metin;
    private final String UrlRegexMetni = "((http?|https|ftp|file)://)?((W|w){3}.)?[a-zA-Z0-9]+\\.(com|edu|net|org)(\\.)?(tr)?";
    private final String HarfRegexMetni = "[aeıiuüoö]";
    private final String MailRegexMetni = "[a-zA-Z0-9_.]+\\@[a-zA-Z]+(\\.)([a-zA-Z]+)(\\.)?([a-zA-Z]+)?";
    
    private void setMetin(String p_metin){
        metin = p_metin.trim();
    }
    
    private String getMetin(){
        return metin;
    }
    
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
    
    private int kelimeBul(){
        String p_metin = getMetin();
        return p_metin.split(" ").length;
    }
    
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
