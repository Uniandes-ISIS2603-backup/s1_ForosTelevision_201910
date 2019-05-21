package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.enums.ClasificacionAudiencia;

import java.io.Serializable;

/**
 * DTO que representa una producción.
 *
 * @author jf.castaneda
 */
public class ProduccionDTO implements Serializable {

    /**
     * Id de la producción
     */
    private Long id;

    /**
     * Nombre de la producción.
     */
    private String nombre;

    /**
     * Descripción de la producción.
     */
    private String descripcion;

    /**
     * Clasificación de audiencia de la producción.
     */
    private ClasificacionAudiencia clasificacionAudiencia;

    /**
     * Calificación promedio de la producción.
     */
    private Integer calificacionPromedio;

    /**
     * Relación de una producción con su multimedia.
     */
    private Long multimedia;

    /**
     * Relación de una producción con su productora.
     */
    private Long productora;

    /**
     * Constructor vacío del DTO de una producción.
     */
    public ProduccionDTO() {
        // Constructor vacío.
    }

    /**
     * Constructor del DTO que recibe una entidad por parámetro.
     *
     * @param produccionEntity entidad de la producción.
     */
    public ProduccionDTO(ProduccionEntity produccionEntity) {
        if(produccionEntity != null) {
            this.nombre = produccionEntity.getNombre();
            this.descripcion = produccionEntity.getDescripcion();
            this.clasificacionAudiencia = produccionEntity.getClasificacionAudiencia();
            this.id = produccionEntity.getId();
            this.calificacionPromedio = produccionEntity.getCalificacionPromedio();
            this.multimedia = produccionEntity.getMultimedia();
            this.productora = produccionEntity.getProductora();
        }
    }

    /**
     * Método que convierte el DTO en una entidad.
     *
     * @return entidad de la producción.
     */
    public ProduccionEntity toEntity() {
        ProduccionEntity produccionEntity = new ProduccionEntity();
        produccionEntity.setCalificacionPromedio(this.calificacionPromedio);
        produccionEntity.setDescripcion(this.descripcion);
        produccionEntity.setId(this.id);
        produccionEntity.setNombre(this.nombre);
        produccionEntity.setMultimedia(this.multimedia);
        produccionEntity.setClasificacionAudiencia(this.clasificacionAudiencia);
        produccionEntity.setProductora(this.productora);
        return produccionEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ClasificacionAudiencia getClasificacionAudiencia() {
        return clasificacionAudiencia;
    }

    public void setClasificacionAudiencia(ClasificacionAudiencia clasificacionAudiencia) {
        this.clasificacionAudiencia = clasificacionAudiencia;
    }

    public Integer getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(Integer calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public Long getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Long multimedia) {
        this.multimedia = multimedia;
    }

    public Long getProductora() {
        return productora;
    }

    public void setProductora(Long productora) {
        this.productora = productora;
    }
    
    
}
