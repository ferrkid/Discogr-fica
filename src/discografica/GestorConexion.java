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

    public int borrarCancion(String nombre_cancion) {

        Statement sta;
        try {
            sta = conn1.createStatement();
            sta.executeUpdate("DELETE  FROM cancion WHERE nombre = '" + nombre_cancion + "'");
            sta.close();
            return 1;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return 0;

    }
    public int borraAlbum(String titulo_album) {

        Statement sta;
        try {
            sta = conn1.createStatement();
            sta.executeUpdate("DELETE  FROM album WHERE titulo = '" + titulo_album + "'");
            sta.close();
            return 1;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return 0;

    }

    public void instertarAlbum(String titulo, String grupo, String publicacion) {

        Statement sta;
        try {
            sta = conn1.createStatement();
            sta.executeUpdate("insert into album(Titulo,Grupo,Publicacion) VALUE ('" + titulo + "', '" + grupo + "','" + publicacion + "');");
            sta.close();
            if (conn1 != null) {
                System.out.println("Añadido a Album!");
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    public ArrayList<Album> datosAlbum() {

        Statement sta;

        try {
            sta = conn1.createStatement();
            String query = "SELECT * FROM album;";
            ResultSet rs = sta.executeQuery(query);

            ArrayList<Album> albumes = new ArrayList<Album>();

            while (rs.next()) {
                //Obtenemos el año en el que se publicó el disco
                Date fechaPublicacion = rs.getDate("Publicacion");
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                cal.setTime(fechaPublicacion);
                int year = cal.get(Calendar.YEAR);
                albumes.add(new Album(rs.getString("Titulo"), rs.getString("Grupo"), year));

            }

            return albumes;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

    }

    public void instertarCancion( String nombre, int duracion, String grupo, int album) {

        Statement sta;
        try {
            sta = conn1.createStatement();
            sta.executeUpdate("INSERT INTO cancion ( nombre,duracion,grupo,album ) VALUE ('" + nombre + "', '" + duracion + "','" + grupo + "', '" + album + "');");
            sta.close();
            if (conn1 != null) {
                System.out.println("Añadido a Cancion!!");
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    public ArrayList<Cancion> datosCancion() {

        Statement sta;

        try {
            sta = conn1.createStatement();
            String query = "SELECT * FROM cancion;";
            ResultSet rs = sta.executeQuery(query);

            ArrayList<Cancion> canciones = new ArrayList<Cancion>();

            while (rs.next()) {
                
                canciones.add(new Cancion(rs.getString("nombre"), rs.getInt("duracion"), rs.getString("grupo"),rs.getInt("album")));

            }

            return canciones;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

    }
    
    public ArrayList<String> sacarCancion (){
        ArrayList<String> cancion = new ArrayList<String>();
        Statement sta;
        try {
            sta = conn1.createStatement();
            String query = "SELECT nombre FROM cancion;";
            ResultSet rs = sta.executeQuery(query);


            while (rs.next()) {
                
                cancion.add(rs.getString("nombre"));

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return cancion;
    }
    
    public ArrayList<String> sacarAlbum (){
        ArrayList<String> album = new ArrayList<String>();
        Statement sta;
        try {
            sta = conn1.createStatement();
            String query = "SELECT titulo FROM album;";
            ResultSet rs = sta.executeQuery(query);


            while (rs.next()) {
                
                album.add(rs.getString("titulo"));

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return album;
    }
    
    public void createDataBase() {
         Statement sta;
        
        try {
            sta = conn1.createStatement();
            String[] query = {
                "DROP DATABASE IF EXISTS discografica;",
                "CREATE DATABASE discografica;",
                "use discografica;",
                "CREATE TABLE IF NOT EXISTS cancion (id INT(11) NOT NULL AUTO_INCREMENT, nombre VARCHAR(255) NOT NULL, duracion INT(11) NOT NULL, grupo VARCHAR(255) NOT NULL, album INT(11) NOT NULL, PRIMARY KEY (id));",
                "CREATE TABLE IF NOT EXISTS album (id INT(11) NOT NULL AUTO_INCREMENT, titulo VARCHAR(255) NOT NULL, grupo VARCHAR(255) NOT NULL, publicacion YEAR NOT NULL, PRIMARY KEY (id));",
                "INSERT INTO cancion ( nombre,duracion,grupo,album ) VALUES ('La raja de tu falda',320,'Estopa',1),('So payaso',196,'Extremoduro',2),('Destragis',212,'Estopa',1);",
                "INSERT INTO album (titulo,grupo,publicacion) VALUES ('La raja de tu falda','Estopa','2001'),('Material Defectuoso','Extremoduro','2011'),('Zapatillas','El canto del Loco','2005'),('Por la Boca Vive el Pez','Fito y Fitipaldis','2006'),('Animales','Pereza','2005'),('Voces de Ultratumba','Estopa','2005');",
                "ALTER TABLE cancion ADD FOREIGN KEY (album) REFERENCES album (id)  ON DELETE CASCADE;"};
            for (int i = 0; i < query.length; i++) {
                sta.executeUpdate(query[i]);
            }
            sta.close();
        } catch (Exception e) {

        }
    }

}
