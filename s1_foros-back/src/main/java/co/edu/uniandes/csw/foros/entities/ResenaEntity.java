package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.ManyToOne;
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
     * @param descripcion descripcion de Produccion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return calificacionProduccion 
     */
    public Integer getCalificacionProduccion() {
        return calificacionProduccion;
    }

    /**
     * @param calificacionProduccion selecciona una calificacion
     */
    public void setCalificacionProduccion(Integer calificacionProduccion) {
        this.calificacionProduccion = calificacionProduccion;
    }

    /**
     * @return verdadero si la serie es recomendada
     */
    public boolean isRecomendada() {
        return this.recomendada;
    }

    /**
     * @param recomendada modifica si la serie es recomendada
     */
    public void setRecomendada(boolean recomendada) {
        this.recomendada = recomendada;
    }

    /**
     * @return fecha de recomendacion
     */
    public Date getFecha() {
        return this.fecha;
    }

    /**
     * @param fecha  asigna una fecha de recomendacion 
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the calificacion para la reseña de usuario
     */
    public Integer getCalificacionResena() {
        return this.calificacionResena;
    }

    /**
     * @param calificacionResena modifica la calificacion de reseña
     */
    public void setCalificacionResena(Integer calificacionResena) {
        this.calificacionResena = calificacionResena;
    }



   
    
    
}
