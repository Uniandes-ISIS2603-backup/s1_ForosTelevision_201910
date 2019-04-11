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
    private MultimediaDTO multimedia;

    /**
     * Relación de una producción con su productora.
     */
    private ProductoraDTO productora;

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
            if(produccionEntity.getMultimedia() != null) {
                this.multimedia = new MultimediaDTO(produccionEntity.getMultimedia());
            } else {
                this.multimedia = null;
            }
            if(produccionEntity.getProductora() != null) {
                this.productora = new ProductoraDTO(produccionEntity.getProductora());
            } else {
                this.productora = null;
            }
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
        produccionEntity.setMultimedia(this.multimedia.toEntity());
        produccionEntity.setClasificacionAudiencia(this.clasificacionAudiencia);
        produccionEntity.setProductora(this.productora.toEntity());
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

    public MultimediaDTO getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(MultimediaDTO multimedia) {
        this.multimedia = multimedia;
    }

    public ProductoraDTO getProductora() {
        return productora;
    }

    public void setProductora(ProductoraDTO productora) {
        this.productora = productora;
    }
    
    
}
