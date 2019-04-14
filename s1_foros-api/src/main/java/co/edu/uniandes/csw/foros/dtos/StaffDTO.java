package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.StaffEntity;
import co.edu.uniandes.csw.foros.enums.RolStaff;
import java.io.Serializable;
import java.util.List;

/**
 * DTO que representa un miembro del staff.
 *
 * @author jf.castaneda
 */
public class StaffDTO implements Serializable {

    /**
     * Id del miembro del staff de al menos una película.
     */
    private Long id;

    /**
     * Rol que cumple el staff dentro de la producción.
     */
    private RolStaff rol;

    /**
     * Nombre del miembro del staff de al menos una película.
     */
    private String nombre;

    /**
     * Foto característica del miembro del Staff de al menos una película.
     */
    private String foto;

    /**
     * Descripción dle miembro del staff de al menos una película.
     */
    private String descripcion;

    /**
     * Constructor vacío del DTO de un miembro del staff de al menos una
     * película.
     */
    public StaffDTO() {
        // Hecho para inicializar un DTO vacío
    }

    /**
     * Constructor del DTO que recibe una entidad por parámetro.
     *
     * @param staffEntity entidad con la información del miembro del staff.
     */
    public StaffDTO(StaffEntity staffEntity) {
        if (staffEntity != null) {
            this.id = staffEntity.getId();
            this.rol = staffEntity.getRol();
            this.descripcion = staffEntity.getDescripcion();
            this.nombre = staffEntity.getNombre();
            this.foto = staffEntity.getFoto();
        }
    }

    /**
     * Método para transformar el DTO en una entidad.
     *
     * @return entidad del miembro del staff.
     */
    public StaffEntity toEntity() {
        StaffEntity staffEntity = new StaffEntity();
        staffEntity.setId(this.id);
        staffEntity.setRol(this.rol);
        staffEntity.setDescripcion(this.descripcion);
        staffEntity.setNombre(this.nombre);
        staffEntity.setFoto(this.foto);
        return staffEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RolStaff getRol() {
        return rol;
    }

    public void setRol(RolStaff rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
