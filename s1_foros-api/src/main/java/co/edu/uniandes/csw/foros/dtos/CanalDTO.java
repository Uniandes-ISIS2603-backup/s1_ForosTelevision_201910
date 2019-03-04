
package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import java.io.Serializable;

/**
 *
 * @author estudiante
 */


public class CanalDTO implements Serializable {
    
    
    
    private Long id;
    private String nombre;
    private Double rating;
    
    public CanalDTO()
    {
        
    }

    public CanalDTO(CanalEntity entidad)
    {
        setId(entidad.getId());
        setNombre(entidad.getNombre());
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
    
    public CanalEntity toEntity()
    {
        CanalEntity entidad= new CanalEntity();
        entidad.setId(this.getId());
        entidad.setNombre(this.getNombre());
        return entidad;
    }
    
    
    
}
