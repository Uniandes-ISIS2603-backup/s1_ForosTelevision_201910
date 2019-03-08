package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.EstadoEntity;
import java.io.Serializable;

/**
 *
 * @author ne.ortega
 */
public class EstadoDTO implements Serializable {
    
    /**
     * Id de la clase.
     */
    private Long id;
    
    /**
     * Estado que maneja la serie.
     */
    private int estado;

    /**
     * Constructor de la clase.
     */
    public EstadoDTO(){
        //Constructor vacío de la clase.
    }
    
    public EstadoDTO(EstadoEntity entity){
        if(entity!=null){
            this.id = entity.getId();
            this.estado = entity.getEstado();
        }
    }
    
    public EstadoEntity toEntity(){
        EstadoEntity entity = new EstadoEntity();
        entity.setId(this.getId());
        entity.setEstado(this.getEstado());
        return entity;
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
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
}
