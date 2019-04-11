/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author Jhonattan Fonseca
 */
@Entity
public class CategoriaEntity extends BaseEntity implements Serializable {

    private String nombre;
    
    @PodamExclude
    //@OneToMany(mappedBy = "categoria")
    private List<ProduccionEntity> producciones;

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

    
    /**
     *
     * @return Las producciones de un productora.
     */
    public List<ProduccionEntity> getProduccciones() {
        return producciones;
    }

    /**
     * Remplaza la lista de producciones de la categoria.
     * @param producciones Lista d eproducciones de la categoria.
     */
    public void setProducciones(List<ProduccionEntity> producciones) {
        this.producciones = producciones;
    }




}
