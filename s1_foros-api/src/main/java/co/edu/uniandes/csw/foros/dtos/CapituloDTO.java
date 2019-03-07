package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.CapituloEntity;
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
    
    public CapituloDTO(CapituloEntity ent){
            this.id=ent.getId();
            this.descripcion=ent.getDescripcion();
            this.nombre=ent.getNombre();
            this.duracion=ent.getDuracion();
    }
    
    /**
     * @return el identificador de Capitulo
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id modifica el identificador de recurso
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return duracion del capitulo
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * @param duracion en minutos de capitulo
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * @return nombre del capitulo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre representativo de capitulo
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

    CapituloEntity toEntity() {
        CapituloEntity cap=new CapituloEntity();
        cap.setDescripcion(descripcion);
        cap.setDuracion(duracion);
        cap.setId(id);
        cap.setNombre(nombre);
        return cap;
    }
    
}
