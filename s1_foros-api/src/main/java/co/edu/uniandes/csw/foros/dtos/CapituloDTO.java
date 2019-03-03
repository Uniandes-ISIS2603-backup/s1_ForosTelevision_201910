package co.edu.uniandes.csw.foros.dtos;

import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class CapituloDTO implements Serializable {
    
    /**
     * Id de la clase.
     */
    private Long id;
    
    /**
     * Duración del capítulo.
     */
    private int duracion;
    
    /**
     * Nombre del capítulo.
     */
    private String nombre;
            
    /**
     * Descripcion del capítulo.
     */
    private String descripcion;

    /**
     * Constructor de la clase.
     */
    public CapituloDTO(){
        
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
     * @return the duracion
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
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
    
}
