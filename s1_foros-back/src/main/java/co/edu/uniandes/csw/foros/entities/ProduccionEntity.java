/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author 
 */
@Entity
public class ProduccionEntity extends BaseEntity implements Serializable {
    
    @PodamExclude
    @ManyToMany
    private List<EmisionEntity> emisiones;
    
    public ProduccionEntity(){
        
    }

    /**
     * @return the emisiones
     */
    public List<EmisionEntity> getEmisiones() {
        return emisiones;
    }

    /**
     * @param emisiones the emisiones to set
     */
    public void setEmisiones(List<EmisionEntity> emisiones) {
        this.emisiones = emisiones;
    }
}
