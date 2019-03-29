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
    private Long idProduccion;

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
            this.idProduccion = produccionEntity.getId();
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
        produccionEntity.setId(this.idProduccion);
        produccionEntity.setNombre(this.nombre);
        produccionEntity.setMultimedia(this.multimedia.toEntity());
        produccionEntity.setClasificacionAudiencia(this.clasificacionAudiencia);
        produccionEntity.setProductora(this.productora.toEntity());
        return produccionEntity;
    }

    /**
     * Getter del id de la producción.
     *
     * @return id de la producción.
     */
    public Long darIdProduccion() {
        return idProduccion;
    }

    /**
     * Setter del id de la producción.
     *
     * @param idProduccion nuevo id de la producción.
     */
    public void editarIdProduccion(long idProduccion) {
        this.idProduccion = idProduccion;
    }

    /**
     * Getter del nombre de la producción.
     *
     * @return nombre de la producción.
     */
    public String darNombre() {
        return nombre;
    }

    /**
     * Setter del nombre de la producción.
     *
     * @param nombre nuevo nombre de la producción.
     */
    public void editarNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de la descripción de la producción.
     *
     * @return nombre de la producción.
     */
    public String darDescripcion() {
        return descripcion;
    }

    /**
     * Setter de la descripción de la producción.
     *
     * @param descripcion nueva descripción de la producción.
     */
    public void editarDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Getter de la clasificación de audiencia de la producción.
     *
     * @return clasificacionAudiencia clasificación de audiencia de la
     * producción.
     */
    public ClasificacionAudiencia darClasificacionAudiencia() {
        return clasificacionAudiencia;
    }

    /**
     * Setter de la clasificación de audiencia de la producción.
     *
     * @param clasificacionAudiencia nueva clasificación de audiencia de la
     * producción.
     */
    public void setClasificacionAudiencia(ClasificacionAudiencia clasificacionAudiencia) {
        this.clasificacionAudiencia = clasificacionAudiencia;
    }

    /**
     * Getter de la calificación promedio de la producción.
     *
     * @return calificacionPromedio calificación promedio de la producción.
     */
    public Integer getCalificacionPromedio() {
        return calificacionPromedio;
    }

    /**
     * Setter de la calificación promedio de la producción.
     *
     * @param calificacionPromedio nueva calificación promedio de la producción.
     */
    public void setCalificacionPromedio(int calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    /**
     * Getter de la multimedia de la producción.
     *
     * @return DTO de multimedia de la producción
     */
    public MultimediaDTO getMultimedia() {
        return multimedia;
    }

    /**
     * Setter de la multimedia de una producción.
     *
     * @param multimedia nueva multimedia de la producción.
     */
    public void setMultimedia(MultimediaDTO multimedia) {
        this.multimedia = multimedia;
    }

    /**
     * Getter de la productora de una producción.
     *
     * @return DTO de productora de la producción.
     */
    public ProductoraDTO getProductora() {
        return productora;
    }

    /**
     * Setter de la productora de una producción.
     *
     * @param productora nueva productora de la producción.
     */
    public void setProductora(ProductoraDTO productora) {
        this.productora = productora;
    }
}
