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
 * @author Jhonattan Fonseca
 */
@Entity
public class CategoriaEntity extends BaseEntity implements Serializable {

    private String nombre;

    /**
     * Retorna el nombre de la categoria.
     *
     * @return nombre de la categoria.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la categoria.
     *
     * @param nombre nombre de la categoria.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
