package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.DiaEntity;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class EmisionDTO implements Serializable{
    
    private Long id;
    
    private Date fechaInicio;
    
    private Date fechaFin;
    
    private int rating;
    
    public EmisionDTO(){
        //Constructor vacío.
    }

    public EmisionDTO(EmisionEntity ent) {
        this.id=ent.getId();
        this.fechaInicio=ent.getFechaInicio();
        this.fechaFin=ent.getFechaFin();
        this.rating=ent.getRating();
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
    
    EmisionEntity toEntity() {
        EmisionEntity em=new EmisionEntity();
        em.setFechaFin(fechaFin);
        em.setFechaInicio(fechaInicio);
        em.setId(id);
        return em;
    }
}
