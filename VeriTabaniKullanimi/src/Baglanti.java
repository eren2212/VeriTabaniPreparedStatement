
import com.mysql.cj.jdbc.Driver;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;



public class Baglanti {
    private String kullanici_adi = "root";
    private String parola = "";
    private String host = "localhost";
    private int port = 3306;
    private String db_ismi = "demo";
    private Connection con = null;
    
    private PreparedStatement preparedStatement =null;
    private Statement statement = null;
    
    
    public void preparedStatement(int id){
        /*try {
            statement = con.createStatement();
            String sorgu = "Select * From calisanlar where ad like 'M%'";
            
           ResultSet rs = statement.executeQuery(sorgu);
           
           while(rs.next()){
               System.out.println("ad:"+rs.getString("ad")+" Soyad: "+rs.getString("soyad"));
           }
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }*/ 
        
        String sorgu = "Select * From calisanlar where id > ? and ad like ?";
        
        try {
            
            preparedStatement = con.prepareStatement(sorgu);
            
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "M%");
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String email = rs.getString("email");
                
                System.out.println("Ad:"+ad+" soyad:"+soyad+ " email: "+email);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void calisanlariGetir(){
        
        String sorgu = "Select * from calisanlar";
        try {
            
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            
            while (rs.next()) {
                
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String mail = rs.getString("email");
                
                System.out.println("İd: "+ id +" Ad: "+ad+" Soyad: "+soyad +" Email: "+mail);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void calisanEkle(){
        Scanner scanner = new Scanner(System.in);
        
        try{
            statement = con.createStatement();
            System.out.print("Adinizi giriniz:");
            String ad =scanner.nextLine();
            System.out.print("Soyadinizi giriniz:");
            String soyad = scanner.nextLine();
            System.out.print("Emailinizi giriniz:");
            String email = scanner.nextLine();
            
            String sorgu = "Insert Into calisanlar (ad,soyad,email) VALUES (" +"'"+ ad+"',"+"'"+soyad+"',"+"'"+email+"')";
            statement.executeUpdate(sorgu);
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void calisanlariGuncelle(){
        
        try{
            statement = con.createStatement();
            
            String sorgu = "Update calisanlar Set email  = 'example@gmail.com' where id > 3 ";
            
            statement.executeUpdate(sorgu);
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void calisanlariSil(){
        try {
            statement = con.createStatement();
            String sorgu = "Delete from calisanlar where id >3";
            
            int deger = statement.executeUpdate(sorgu);
            System.out.println(deger+" kadar veri silindi");
                    
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    
    public Baglanti(){
        //jdbc:mysql://:localhost:3306/demo
        String url = "jdbc:mysql://"+ host + ":" + port + "/" +db_ismi+"?useUnicode=true&characterEncoding=utf8";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadi");
        }
        
        try {
            con = DriverManager.getConnection(url, kullanici_adi, parola);
            System.out.println("Bağlantı Başarılı");
        } catch (SQLException ex) {
            System.out.println("Bağlantı başarısız");
        }
        
        
    }
    public static void main(String[] args) {
        Baglanti baglanti = new Baglanti();
        /*System.out.println("Calisan eklenmeden önce");
        baglanti.calisanlariGetir();
        System.out.println("*******************");
      
        baglanti.calisanEkle();
        System.out.println("Calisan eklendikten sonra");
        baglanti.calisanlariGetir();*/
        
        /*baglanti.calisanlariGuncelle();
        baglanti.calisanlariGetir();*/
        
        //baglanti.calisanlariSil();
        //baglanti.calisanlariGetir();
        
        baglanti.preparedStatement(3);
    }
}
