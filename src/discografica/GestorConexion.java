/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discografica;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author fptar
 */
public class GestorConexion {

    Connection conn1 = null;

    public GestorConexion() {
       
        

    }

    public int abrirConexion() {

        String urlBBDD = "jdbc:mysql://localhost:3306/discografica?serverTimezone=UTC";
        String user = "root";
        String password = "";

        try {
            conn1 = DriverManager.getConnection(urlBBDD, user, password);

            if (conn1 != null) {
                System.out.println("Conectado a la BBDD discografica");
            }
            
            return 1;

        } catch (SQLException ex) {
            System.out.println("Error al conectar la BBDD");
            return 0;

        }

    }

    public int cerrarConexion() {

        try {
            conn1.close();
            if (conn1 != null) {
                System.out.println("Desconectado de la BBDD");
           return 1;
            }
            System.out.println("Cerrar conexión");
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexion");
           
        }
        return 0;

    }


    public int annadirColumnaAlbum() {

        Statement sta;
        try {
            
            sta = conn1.createStatement();
            sta.executeUpdate("ALTER table album ADD autor VARCHAR(20); ");
            sta.close();
            return 1;
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return 0;
    }
  
    
       public int annadirColumnaCancion() {

        Statement sta;
        try {
            
            sta = conn1.createStatement();
            sta.executeUpdate("ALTER table cancion ADD Colaboracion VARCHAR(20);");
            sta.close();
            return 1;
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return 0;
    }

    public int borrarTabla() {

        Statement sta;
        try {
            sta = conn1.createStatement();
            sta.executeUpdate("DROP table autor;");
            sta.close();
            return 1;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return 0;

    }

    public void instertarAlbum(String id, String titulo, String grupo, String publicacion) {

        Statement sta;
        try {
            sta = conn1.createStatement();
            sta.executeUpdate("insert into album VALUE ('"+ id +"','"+ titulo +"', '"+ grupo +"','"+ publicacion +"');");
            sta.close();
            if (conn1 != null) {
                System.out.println("Añadido a Album!");
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }
    
    
    public ArrayList<Album> consulta_sta(){
        
        Statement sta;
        
        try {
            sta = conn1.createStatement();
            String query = "SELECT * FROM album;";
            ResultSet rs = sta.executeQuery(query);
            
            ArrayList<Album> albumes = new ArrayList<Album>();
            
            while(rs.next()){
                //Obtenemos el año en el que se publicó el disco
                Date fechaPublicacion = rs.getDate("Publicacion");
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                cal.setTime(fechaPublicacion);
                int year = cal.get(Calendar.YEAR);
                albumes.add(new Album(rs.getString("id"), rs.getString("Titulo"), rs.getString("Grupo"), year));
            
                       }    
            
            return albumes;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

    
    }

}
