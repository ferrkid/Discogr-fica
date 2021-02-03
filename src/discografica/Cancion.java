/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discografica;

/**
 *
 * @author fptar
 */
public class Cancion {
    
    public String nombre,grupo;
    public int duracion,album;
    

    public Cancion(String nombre, int duracion, String grupo, int album) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.grupo = grupo;
        this.album = album;
    }
    
    
}
