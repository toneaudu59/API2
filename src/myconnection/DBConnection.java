package myconnection;

import java.sql.*;
import java.util.*;

public class DBConnection {

     private static Connection dbConnect = null;

  private DBConnection(){}   
     
     
     
     public static Connection getConnection() {
        if (dbConnect!=null)return dbConnect;
        PropertyResourceBundle properties = 
                (PropertyResourceBundle) PropertyResourceBundle.getBundle("resources.application");
            //nom du fichier properties à utiliser
        String serverName = properties.getString("DB.server");
        String dbName = properties.getString("DB.database");
        String username = properties.getString("DB.login");
        String password = properties.getString("DB.password");
        String dbPort = properties.getString("DB.port");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String url = "jdbc:oracle:thin:@//" + serverName + ":" + dbPort + "/" + dbName;
            dbConnect = DriverManager.getConnection(url, username, password);
            return dbConnect;

        } catch (Exception e) {
            System.out.println("erreur de connexion " + e);
            e.printStackTrace();
            return null;
        }
    }
    
    public static void closeConnection(){
       
        try{
            
            dbConnect.close();
           
        }
        catch(Exception e){
            System.out.println("erreur de fermeture de connexion "+e);
                    
        }
         dbConnect=null;
     }

}
