package co.edu.uniandes.csw.foros.dtos;

import java.io.Serializable;

/**
 * Clase que represnta los usuarios en la plataforma
 */
public class UsuarioDTO implements Serializable {

    public enum Acceso{
            ADMINISTRADOR, USUARIO
    }

    private Long id;
    private String nombre;
    private String clave;
    private Acceso privilegio;

    public  UsuarioDTO(){

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
