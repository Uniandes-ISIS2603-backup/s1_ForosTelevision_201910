/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author ne.ortega
 */
@Entity
public class EmisionEntity extends BaseEntity implements Serializable {
    
    @PodamExclude
    @OneToOne(fetch=EAGER)
    private CanalEntity canal;
    
    @PodamExclude
    @OneToMany(mappedBy="emision", fetch=LAZY)
    private List<DiaEntity> dias;
    
    @PodamExclude
    @ManyToOne 
    private ProduccionEntity produccion;
    /**
     * Rating de la emision.
     */
    private int rating;
    
    /**
     * Fecha en la que inicia la emisión.
     */
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    
    /**
    * Fecha en la que termina la emision.
    */
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    /**
     * Constructor de la clase.
     */
    public EmisionEntity(){
        //Constructor vacío de la clase.
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the canal
     */
    public CanalEntity getCanal() {
        return canal;
    }

    /**
     * @param canal the canal to set
     */
    public void setCanal(CanalEntity canal) {
        this.canal = canal;
    }

    /**
     * @return the dias
     */
    public List<DiaEntity> getDias() {
        return dias;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(List<DiaEntity> dias) {
        this.dias = dias;
    }

    /**
     * @return the producciones
     */
    public ProduccionEntity getProduccion() {
        return produccion;
    }

    /**
     * @param produccion the producciones to set
     */
    public void setProduccion(ProduccionEntity produccion) {
        this.produccion = produccion;
    }
}
