package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.List;

/**
 * Informacion adicional de un usuario
 */
public class UsuarioDetailDTO extends UsuarioDTO implements Serializable {

    /**
     * Lista de usuarios a los cuales sigue el usuario
     */
    private List<UsuarioDTO> seguidos;
    /**
     * Lista de producciones vistas por un usuario
     */
    private List<ProduccionDTO> vistas;
    /**
     * Lista de producciones favoritas por un usuario
     */
    private List<ProduccionDTO> favoritas;
    /**
     * Lista de producciones aun no vistas por un usuario
     */
    private List<ProduccionDTO> pendientes;

    public UsuarioDetailDTO(UsuarioEntity user) {
        super(user);
    }

    public List<UsuarioDTO> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(List<UsuarioDTO> seguidos) {
        this.seguidos = seguidos;
    }

    public List<ProduccionDTO> getVistas() {
        return vistas;
    }

    public void setVistas(List<ProduccionDTO> vistas) {
        this.vistas = vistas;
    }

    public List<ProduccionDTO> getFavoritas() {
        return favoritas;
    }

    public void setFavoritas(List<ProduccionDTO> favoritas) {
        this.favoritas = favoritas;
    }

    public List<ProduccionDTO> getPendientes() {
        return pendientes;
    }

    public void setPendientes(List<ProduccionDTO> pendientes) {
        this.pendientes = pendientes;
    }
}
