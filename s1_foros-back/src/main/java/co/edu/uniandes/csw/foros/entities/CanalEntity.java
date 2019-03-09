/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author mi.carrascal
 */
@Entity
public class CanalEntity extends BaseEntity implements Serializable {
    
    private String nombre;
    private Double rating;
    
    @PodamExclude
    @OneToMany(fetch=LAZY)
    private List<EmisionEntity> emisiones;
    
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

    public void addEmision(EmisionEntity emision) {
        emisiones.add(emision);
    }

    public EmisionEntity getEmision(Long emisionId) {
        EmisionEntity emision=null;
        for(int i=0;i<emisiones.size();i++)
        {
            if(emisionId==emisiones.get(i).getId())
            {
                emision=emisiones.get(i);
            }
        }
        
        return emision;
    }

    public List<EmisionEntity> getEmisiones()
    {
        return emisiones;
    }

    public void eliminarEmision(Long emisionId) {
        EmisionEntity emision=null;
        for(int i=0;i<emisiones.size();i++)
        {
            if(emisionId==emisiones.get(i).getId())
            {
                emisiones.remove(i);
            }
        }
        
        
    }
   
   
}
