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
    
    private void setMetin(String p_metin){
        metin = p_metin.trim();
    }
    
    private String getMetin(){
        return metin;
    }
    
    public void DosyaYukle(String dosyaYolu) throws FileNotFoundException{
       Scanner fileIn = new Scanner(new File("icerik.txt"));

       String okunanMetin = "";
       while(fileIn.hasNext()){
           okunanMetin += fileIn.next() + " ";
       }
       
       setMetin(okunanMetin);
    }
    
    public void AnaliziBaslat(){
        System.out.println("Toplam Sesli Harf Sayısı : " + sesliHarfBul());
        System.out.println("Toplam Kelime Sayısı     : " + kelimeBul());
        System.out.println("Toplam Cümle Sayısı      : " + cumleBul());
        System.out.println("Toplam Mail Sayısı       : " + mailBul());
        System.out.println("Toplam Web Sitesi Sayısı : " + webSitesiBul());
    }
    
    private int webSitesiBul(){
        String p_metin = getMetin();
        
        Pattern urlRegex = Pattern.compile("((http?|https|ftp|file)://)?((W|w){3}.)?[a-zA-Z0-9]+\\.(com|edu|net|org)(\\.)?(tr)?");  
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
        Pattern sesliHarfRegex = Pattern.compile("[aeıiuüoö]", Pattern.CASE_INSENSITIVE);
        Matcher SesliHarfMatcher = sesliHarfRegex.matcher(p_metin);
        int sesliHarfSayisi = 0;
        
        while (SesliHarfMatcher.find()) {
            sesliHarfSayisi++;
        }
        
        return sesliHarfSayisi;
    }
    
    private int mailBul(){
        String p_metin = getMetin();
        
        Pattern mailRegex = Pattern.compile("[a-zA-Z0-9]+\\@[a-zA-Z]+(\\.)([a-zA-Z]+)(\\.)?([a-zA-Z]+)?");  
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
     
        return p_metin.split("\\.").length - 1;
    }
    
    private String mailTemizle(String p_metin){
        Pattern mailRegex = Pattern.compile("[a-zA-Z0-9]+\\@[a-zA-Z]+(\\.)([a-zA-Z]+)(\\.)?([a-zA-Z]+)?");  
        Matcher mailMatcher;
        
        String[] kelimeListesi = p_metin.split(" ");
        
        for (String mail : kelimeListesi) {
            mailMatcher = mailRegex.matcher(mail);
            
            if(mailMatcher.find()){
                if(mail == null ? mailMatcher.group() == null : mail.equals(mailMatcher.group())){
                    p_metin = p_metin.replace(mailMatcher.group(),"");
                }
            }
        }
        
        return p_metin;
    }
    
    private String webSitesiTemizle(String p_metin){
        Pattern urlRegex = Pattern.compile("((http?|https|ftp|file)://)?((W|w){3}.)?[a-zA-Z0-9]+\\.[a-zA-Z]+(\\.)?([a-zA-Z]+)?");  
        Matcher urlMatcher;
        
        String[] kelimeListesi = p_metin.split(" ");
        
        for (String URL : kelimeListesi) {
            urlMatcher = urlRegex.matcher(URL);
            
            if(urlMatcher.find()){
                if(URL == null ? urlMatcher.group() == null : URL.equals(urlMatcher.group())){
                    p_metin = p_metin.replace(urlMatcher.group(), "");
                }
            }
        }
        
        return p_metin;
    }
}
