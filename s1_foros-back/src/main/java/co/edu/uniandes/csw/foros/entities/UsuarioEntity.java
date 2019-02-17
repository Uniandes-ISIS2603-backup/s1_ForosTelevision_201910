/*
 * Clase que representa un usuario
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author bsrincon
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable{
    public enum Acceso{
            ADMINISTRADOR, USUARIO
    }
    
    private String nombre;
    private String clave;
    private Acceso privilegio;
    
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
