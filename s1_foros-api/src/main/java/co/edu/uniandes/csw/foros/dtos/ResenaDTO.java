package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *DTO que representa una Resena de una Produccion 
 * @author mi.carrascal
 */
public class ResenaDTO implements Serializable {
    
    private Long id;
    private String descripcion;
    private Integer calificacionProduccion;
    private boolean recomendada;
    private Date fecha;
    private Integer calificacionResena;
    
    public ResenaDTO()
    {}
    
    public ResenaDTO(ResenaEntity resenaEntity)
    {
        this.id=resenaEntity.getId();
        this.descripcion=resenaEntity.getDescripcion();
        this.calificacionProduccion=resenaEntity.getCalificacionProduccion();
        this.fecha=resenaEntity.getFecha();
        this.id=resenaEntity.getId();
        this.calificacionResena=resenaEntity.getCalificacionResena();
        this.recomendada=resenaEntity.isRecomendada();      
    }
   
    /**
     * @return la descripcion de la reseña
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion modifica la descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the calificacion de reseña a produccion
     */
    public Integer getCalificacionProduccion() {
        return calificacionProduccion;
    }

    /**
     * @param calificacionProduccion modifica la calificacion
     */
    public void setCalificacionProduccion(Integer calificacionProduccion) {
        this.calificacionProduccion = calificacionProduccion;
    }

    /**
     * @return recomienda la serie
     */
    public boolean isRecomendada() {
        return recomendada;
    }

    /**
     * @param recomendada modifica la recomendacion
     */
    public void setRecomendada(boolean recomendada) {
        this.recomendada = recomendada;
    }

    /**
     * @return the fecha de registro
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha modifica la fecha de registro
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return calificacion para produccion
     */
    public Integer getCalificacionResena() {
        return calificacionResena;
    }

    /**
     * @param calificacionResena modifica calificacion de produccion
     */
    public void setCalificacionResena(Integer calificacionResena) {
        this.calificacionResena = calificacionResena;
    }

    /**
     * @return el identificador de recurso
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id modifica identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    public ResenaEntity toEntity()
    {
        ResenaEntity resenaEntity=new ResenaEntity();
        resenaEntity.setId( this.id);
        resenaEntity.setDescripcion(this.descripcion);
        resenaEntity.setCalificacionProduccion(this.calificacionProduccion);
        resenaEntity.setCalificacionResena(this.calificacionResena);
        resenaEntity.setRecomendada(this.recomendada);
        resenaEntity.setFecha(this.fecha);
        return resenaEntity;
    }

   

}

