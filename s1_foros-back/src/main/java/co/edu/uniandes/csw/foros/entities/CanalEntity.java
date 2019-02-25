/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author 
 */
@Entity
public class CanalEntity extends BaseEntity implements Serializable {
    
    private String nombre;
    private Double rating;
    
    @PodamExclude
    @OneToOne(fetch=LAZY)
    private EmisionEntity emision;
    
    public CanalEntity(){
       
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

   
   
}
