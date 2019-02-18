/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author 
 */
@Entity
public class ProduccionEntity extends BaseEntity implements Serializable {
    
    /**
     * Relación entre producción y emisión.
     */
    @PodamExclude
    @ManyToMany(mappedBy="producciones", fetch=LAZY)
    private List<EmisionEntity> emisiones;
    
    /**
     * Relación entre producción y staff.
     */
    @PodamExclude
    @ManyToMany(mappedBy="producciones", fetch=LAZY)
    private List<StaffEntity> staff;
    
    /**
     * Relación entre producción y capítulo.
     */
    @PodamExclude
    @OneToMany(fetch=LAZY)
    private List<CapituloEntity> capitulos;
    
    /**
     * Relación entre producción y categoría.
     */
    @PodamExclude
    @OneToMany(fetch=LAZY)
    private List<CategoriaEntity> categorias;
    
    /**
     * Relación entre producción y multimedia.
     */
    @PodamExclude
    @OneToOne
    private MultimediaEntity multimedia;
    
    /**
     * Relación entre producción y productora.
     */
    @PodamExclude
    @ManyToOne(mappedBy="producciones", fetch=LAZY)
    private ProductoraEntity productora;
    
    /**
     * Relación entre producción y estado.
     */
    @PodamExclude
    @OneToOne
    private EstadoEntity estado;
    
    /**
     * Relación entre producción y reseña.
     */
    @PodamExclude
    @OneToMany(fetch=LAZY)
    private List<ResenaEntity> resenas;
    
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
     * Constructor de la entidad de una producción.
     */ 
    public ProduccionEntity(){
        
    }

    /**
     * Getter de las emisiones de la producción.
     * @return lista con las entidades de las emisiones de la producción.
     */
    public List<EmisionEntity> darEmisiones() {
        return emisiones;
    }

    /**
     * Setter de las emisiones de la producción.
     * @param emisiones las nuevas emisiones.
     */
    public void editartEmisiones(List<EmisionEntity> emisiones) {
        this.emisiones = emisiones;
    }
    
    /**
     * Getter del staff de la producción.
     * @return lista con las entidades de los miembros del staff de la producción.
     */
    public List<StaffEntity> darStaff() {
        return staff;
    }
    
    /**
     * Getter del id de la producción.
     * @return id de la producción.
     */
    public long darIdProduccion() {
        return idProduccion;
    }

    /**
     * Setter del id de la producción.
     * @param idProduccion nuevo id de la producción.
     */
    public void editarIdProduccion(long idProduccion) {
        this.idProduccion = idProduccion;
    }

    /**
     * Getter del nombre de la producción.
     * @return nombre de la producción.
     */
    public String darNombre() {
        return nombre;
    }
    
    /**
     * Setter del nombre de la producción.
     * @param nombre nuevo nombre de la producción.
     */
    public void editarNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de la descripción de la producción.
     * @return nombre de la producción.
     */
    public String darDescripcion() {
        return descripcion;
    }

    /**
     * Setter de la descripción de la producción.
     * @param descripcion nueva descripción de la producción.
     */
    public void editarDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Getter de la clasificación de audiencia de la producción.
     * @return clasificacionAudiencia clasificación de audiencia de la producción.
     */
    public ClasificacionAudiencia darClasificacionAudiencia() {
        return clasificacionAudiencia;
    }

    /**
     * Setter de la clasificación de audiencia de la producción.
     * @param clasificacionAudiencia nueva clasificación de audiencia de la producción.
     */
    public void setClasificacionAudiencia(ClasificacionAudiencia clasificacionAudiencia) {
        this.clasificacionAudiencia = clasificacionAudiencia;
    }

    /**
     * Getter de la calificación promedio de la producción.
     * @return calificacionPromedio calificación promedio de la producción.
     */
    public int getCalificacionPromedio() {
        return calificacionPromedio;
    }

    /**
     * Setter de la calificación promedio de la producción.
     * @param calificacionPromedio nueva calificación promedio de la producción.
     */
    public void setCalificacionPromedio(int calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }
    
}
