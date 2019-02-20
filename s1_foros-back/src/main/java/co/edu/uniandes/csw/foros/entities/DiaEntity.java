/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ne.ortega
 */
@Entity
public class DiaEntity extends BaseEntity implements Serializable{
    
    @PodamExclude
    @ManyToOne
    private EmisionEntity emision;
    /**
     * Nombre de la entidad.
     */
    private String nombre;
    
    /**
     * Hora de la emision.
     */
    @Temporal(TemporalType.DATE)
    private Date horaEmision;
    
    /**
     * Constructor de la clase.
     */
    public DiaEntity(){
        
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
     * @return the horaEmision
     */
    public Date getHoraEmision() {
        return horaEmision;
    }

    /**
     * @param horaEmision the horaEmision to set
     */
    public void setHoraEmision(Date horaEmision) {
        this.horaEmision = horaEmision;
    }

    /**
     * @return the emision
     */
    public EmisionEntity getEmision() {
        return emision;
    }

    /**
     * @param emision the emision to set
     */
    public void setEmision(EmisionEntity emision) {
        this.emision = emision;
    }
    
}
