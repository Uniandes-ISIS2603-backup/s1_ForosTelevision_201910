package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
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
    private long idProduccion;

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
    private ProduccionEntity.ClasificacionAudiencia clasificacionAudiencia;

    /**
     * Calificación promedio de la producción.
     */
    private int calificacionPromedio;

    /**
     * Constructor del DTO de una producción.
     */
    public ProduccionDTO() {

    }
    
    public ProduccionDTO(ProduccionEntity ent){
       this.nombre=ent.getNombre();
       this.descripcion=ent.getDescripcion();
       this.clasificacionAudiencia=ent.getClasificacionAudiencia();
       this.idProduccion=ent.getId();
       this.calificacionPromedio=ent.getCalificacionPromedio();
    }

    /**
     * Getter del id de la producción.
     *
     * @return id de la producción.
     */
    public long darIdProduccion() {
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
    public ProduccionEntity.ClasificacionAudiencia darClasificacionAudiencia() {
        return clasificacionAudiencia;
    }

    /**
     * Setter de la clasificación de audiencia de la producción.
     *
     * @param clasificacionAudiencia nueva clasificación de audiencia de la
     * producción.
     */
    public void setClasificacionAudiencia(ProduccionEntity.ClasificacionAudiencia clasificacionAudiencia) {
        this.clasificacionAudiencia = clasificacionAudiencia;
    }

    /**
     * Getter de la calificación promedio de la producción.
     *
     * @return calificacionPromedio calificación promedio de la producción.
     */
    public int getCalificacionPromedio() {
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

}
