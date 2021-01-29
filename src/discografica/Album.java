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
public class Album {
    
    
    public String id, titulo, grupo;
    public int publicacion;

    public Album(String id, String titulo, String grupo, int publicacion) {
        this.id = id;
        this.titulo = titulo;
        this.grupo = grupo;
        this.publicacion = publicacion;
    }
    
    
    
}
