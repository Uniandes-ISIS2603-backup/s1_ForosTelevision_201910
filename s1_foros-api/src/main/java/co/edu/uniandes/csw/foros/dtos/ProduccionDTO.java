package co.edu.uniandes.csw.foros.dtos;

import java.io.Serializable;

public class ProduccionDTO implements Serializable {
    
    /**
     * Clasificacion de audiencias para las producciones.
     */
    public enum ClasificacionAudiencia {
        FAMILIAR, INFANTIL, ADOLESCENTES, ADULTOS
    }
    
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
    private ClasificacionAudiencia clasificacionAudiencia;
    
    /**
     * Calificación promedio de la producción.
     */
    private int calificacionPromedio; 
    
    /**
     * Constructor del DTO de una producción.
     */
    public ProduccionDTO() {
        
    }

    /**
     * Getter del id de una producción.
     * @return 
     */
    public long darIdProduccion() {
        return idProduccion;
    }

    public void setIdProduccion(long idProduccion) {
        this.idProduccion = idProduccion;
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

    public int getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(int calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }
    
    
}
