package co.edu.uniandes.csw.foros.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ne.ortega
 */
public class DiaDTO implements Serializable {
    
    /**
     * Id de la clase.
     */
    private Long id;
    
    /**
     * Nombre del día.
     */
    private String nombre;
    
    /**
     * Hora de la emisión en ese día.
     */
    private Date horaEmision;
    
    /**
     * Constructor de la clase.
     */
    public DiaDTO(){
        
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
}
