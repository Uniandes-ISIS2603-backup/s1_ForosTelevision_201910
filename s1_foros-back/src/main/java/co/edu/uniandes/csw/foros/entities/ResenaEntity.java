/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author mi.carrascal
 */
@Entity
public class ResenaEntity extends BaseEntity implements Serializable  {
    
    private String descripcion;
    private Integer calificacionProduccion;
    private boolean recomendada;
    private Date fecha;
    private Integer calificacionResena;
  
    
    
    
    
     /**
     * Relación entre reseña y usuario.
     */
    @PodamExclude
    @ManyToOne(fetch=LAZY)
    private UsuarioEntity usuarioResena;

    
    /**
     * Relación entre reseña y produccion.
     */
    @PodamExclude
    @ManyToOne(fetch=LAZY)
    private ProduccionEntity produccionResena;

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the calificacionProduccion
     */
    public Integer getCalificacionProduccion() {
        return calificacionProduccion;
    }

    /**
     * @param calificacionProduccion the calificacionProduccion to set
     */
    public void setCalificacionProduccion(Integer calificacionProduccion) {
        this.calificacionProduccion = calificacionProduccion;
    }

    /**
     * @return the recomendada
     */
    public boolean isRecomendada() {
        return recomendada;
    }

    /**
     * @param recomendada the recomendada to set
     */
    public void setRecomendada(boolean recomendada) {
        this.recomendada = recomendada;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the calificacionResena
     */
    public Integer getCalificacionResena() {
        return calificacionResena;
    }

    /**
     * @param calificacionResena the calificacionResena to set
     */
    public void setCalificacionResena(Integer calificacionResena) {
        this.calificacionResena = calificacionResena;
    }



   
    
    
}
