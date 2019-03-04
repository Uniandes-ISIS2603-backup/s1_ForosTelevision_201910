/*
 * Clase que representa un usuario
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author bsrincon
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable{
    /**
     * Niveles de acceso en la plataforma
     */
     
    public enum Acceso{
            ADMINISTRADOR, USUARIO
    }
    
    /**
     * Relacion entre usuarios seguidos
     */
    @PodamExclude
    @OneToMany(fetch=LAZY)
    private List<UsuarioEntity> seguidos;
    /**
     * Relacion entre usuarios y programas en emision
     */
    @PodamExclude
    @OneToMany(fetch=LAZY)
    private List<ProductoraEntity> productorasFav;
    /**
     * Parrila de programacion
     */
    @PodamExclude
    @OneToMany(fetch=LAZY)
    private List<EmisionEntity> parrilla;
    /**
     * Estado de la serie visualizada
     */
    @PodamExclude
    @OneToMany(fetch=LAZY)
    private List<EstadoEntity> seriesEstado;
    /**
     * Nombre de usuario
     */
    private String nombre;
    /**
     * Clave de usuario
     */
    private String clave;
    /**
     * Correo electronico de usuario
     */
    private String email;
    
    @Enumerated(EnumType.STRING)
    private Acceso privilegio;
    
    public List<UsuarioEntity> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(List<UsuarioEntity> seguidos) {
        this.seguidos = seguidos;
    }

    public List<ProductoraEntity> getProductorasFav() {
        return productorasFav;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProductorasFav(List<ProductoraEntity> productorasFav) {
        this.productorasFav = productorasFav;
    }

    public List<EmisionEntity> getParrilla() {
        return parrilla;
    }

    public void setParrilla(List<EmisionEntity> parrilla) {
        this.parrilla = parrilla;
    }

    public List<EstadoEntity> getSeriesEstado() {
        return seriesEstado;
    }

    public void setSeriesEstado(List<EstadoEntity> seriesEstado) {
        this.seriesEstado = seriesEstado;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Acceso getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Acceso privilegio) {
        this.privilegio = privilegio;
    }
}
