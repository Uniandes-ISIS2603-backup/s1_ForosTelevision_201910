/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import co.edu.uniandes.csw.foros.enums.ClasificacionAudiencia;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad de la clase producción.
 * 
 * @author jf.castaneda 
 */
@Entity
public class ProduccionEntity extends BaseEntity implements Serializable {
    
    /**
     * Relación entre producción y emisión.
     */
    @PodamExclude
    @OneToMany(mappedBy="produccion", fetch=LAZY)
    private List<EmisionEntity> emisiones;
    
    /**
     * Relación entre producción y staff.
     */
    @PodamExclude
    @ManyToMany(fetch=LAZY)
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
    @ManyToOne(fetch=LAZY)
    private ProductoraEntity productora;
    
    /**
     * Relación entre producción y reseña.
     */
    @PodamExclude
    @OneToMany(fetch=LAZY)
    private List<ResenaEntity> resenas;
    
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
    @PodamExclude
    @Enumerated(EnumType.STRING)
    private ClasificacionAudiencia clasificacionAudiencia;
    
    /**
     * Calificación promedio de la producción.
     */
    private Integer calificacionPromedio;
   
    /**
     * Constructor vacío de la entidad de una producción.
     */ 
    public ProduccionEntity(){
        // Constructor vacío.
    }

    /**
     * Getter de las emisiones de la producción.
     * @return lista con las entidades de las emisiones de la producción.
     */
    public List<EmisionEntity> getEmisiones() {
        return emisiones;
    }

    /**
     * Setter de las emisiones de la producción.
     * @param emisiones las nuevas emisiones.
     */
    public void setEmisiones(List<EmisionEntity> emisiones) {
        this.emisiones = emisiones;
    }
    
    /**
     * Getter del staff de la producción.
     * @return lista con las entidades de los miembros del staff de la producción.
     */
    public List<StaffEntity> getStaff() {
        return staff;
    }
    
    /**
     * Setter del staff de la producción.
     * @param staff los nuevos miembros del staff.
     */
    public void setStaff(List<StaffEntity> staff) {
        this.staff = staff;
    }
    
    /**
     * Getter de los capítulos de la producción.
     * @return lista con las entidades de los capítulos de la producción.
     */
    public List<CapituloEntity> getCapitulos() {
        return capitulos;
    }

    /**
     * Setter de los capítulos de la producción.
     * @param capitulos los nuevos capitulos.
     */
    public void setCapitulos(List<CapituloEntity> capitulos) {
        this.capitulos = capitulos;
    }
    
    /**
     * Getter de las categorías de la producción.
     * @return lista con las entidades de las categorías de la producción.
     */
    public List<CategoriaEntity> getCategorias() {
        return categorias;
    }

    /**
     * Setter de las categorías de la producción.
     * @param categorias las nuevas categorías.
     */
    public void setCategorias(List<CategoriaEntity> categorias) {
        this.categorias = categorias;
    }
    
    /**
     * Getter de la multimedia de la producción.
     * @return entidad de la multimedia de la producción.
     */
    public MultimediaEntity getMultimedia() {
        return multimedia;
    }

    /**
     * Setter de la multimedia de la producción.
     * @param multimedia las nueva multimedia.
     */
    public void setMultimedia(MultimediaEntity multimedia) {
        this.multimedia = multimedia;
    }
    
    /**
     * Getter de la productora de la producción.
     * @return la entidad de la productora de la producción.
     */
    public ProductoraEntity getProductora() {
        return productora;
    }

    /**
     * Setter de la productora de la producción.
     * @param productora las nueva productora.
     */
    public void setProductora(ProductoraEntity productora) {
        this.productora = productora;
    }
    
    /**
     * Getter de las reseñas de la producción.
     * @return lista con las entidades de las reseñas de la producción.
     */
    public List<ResenaEntity> getResenas() {
        return resenas;
    }

    /**
     * Setter de las reseñas de la producción.
     * @param resenas las nuevas reseñas.
     */
    public void setResenas(List<ResenaEntity> resenas) {
        this.resenas = resenas;
    }

    /**
     * Getter del nombre de la producción.
     * @return nombre de la producción.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Setter del nombre de la producción.
     * @param nombre nuevo nombre de la producción.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de la descripción de la producción.
     * @return nombre de la producción.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Setter de la descripción de la producción.
     * @param descripcion nueva descripción de la producción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Getter de la clasificación de audiencia de la producción.
     * @return clasificacionAudiencia clasificación de audiencia de la producción.
     */
    public ClasificacionAudiencia getClasificacionAudiencia() {
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
    public Integer getCalificacionPromedio() {
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
