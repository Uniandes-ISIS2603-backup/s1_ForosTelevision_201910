/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author ne.ortega
 */
@Entity
public class CapituloEntity extends BaseEntity implements Serializable {
    
    /**
     * Duracion del capitulo.
     */
    private int duracion;
    
    /**
     * Nombre del capítulo.
     */
    private String nombre;
    
    /**
     * Descripcion del capitulo.
     */
    private String descripcion;
    
  
    /**
     * Obtiene la duracion del capitulo
     * @return duracion - duracion del capitulo
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Modifica la duracion del capitulo
     * @param duracion - nueva duracion
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * Obtiene el nombre del capitulo
     * @return nombre - nombre del capitulo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del capitulo
     * @param nombre - nombre a modificar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripcion del capitulo
     * @return descripcion - descripcion del capitulo
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripcion del capitulo
     * @param descripcion - descripcion a modificar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    } 
}
