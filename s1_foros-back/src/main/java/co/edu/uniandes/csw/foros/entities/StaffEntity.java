/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import co.edu.uniandes.csw.foros.enums.RolStaff;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad de la clase staff.
 * 
 * @author jf.castaneda
 */
@Entity
public class StaffEntity extends BaseEntity implements Serializable{
    
    /**
     * Relación de un miembro del staff con sus producciones.
     */
    @PodamExclude
    @ManyToMany(fetch = LAZY)
    private List<ProduccionEntity> producciones;
    
    /**
     * Nombre del miembro del staff.
     */
    private String nombre;
    
    /**
     * Rol que cumple el staff dentro de la producción.
     */
    @Enumerated(EnumType.STRING)
    private RolStaff rol;
    
    /**
     * Ruta de la foto del miembro del staff.
     */
    private String foto;
    
    /**
     * Descripción del miembro del staff.
     */
    private String descripcion;
    
    /**
     * Constructor de la entidad que representa un miembro del staff.
     */
    public StaffEntity(){
        
    }
    
    /**
     * Getter de las producciones con las que está relacionada un miembro del staff.
     * @return lista con las producción en las que ha participado el miembro del staff.
     */
    public List<ProduccionEntity> getProducciones() {
        return producciones;
    }
    
    /**
     * Setter de las producciones en las que ha participado un miembro del staff.
     * @param producciones producción en las que ha participado un miembro del staff.
     */
    public void setProducciones(List<ProduccionEntity> producciones) {
        this.producciones = producciones;
    }

    /**
     * Getter del rol del miembro del staff.
     * @return rol del miembro del staff.
     */
    public RolStaff getRol() {
        return rol;
    }

    /**
     * Setter del rol del miembro del staff.
     * @param rol nuevo rol del miembro del staff.
     */
    public void setRol(RolStaff rol) {
        this.rol = rol;
    }

    /**
     * Getter del nombre del miembro del staff.
     * @return nombre del miembro del staff.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter del nombre del miembro del staff.
     * @param nombre nuevo nombre del miembro del staff.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de la foto del miembro del staff.
     * @return ruta de la foto del miembro del staff.
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Setter de la nueva foto del miembro del staff.
     * @param foto nueva ruta de la foto del mimebro del staff.
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Getter de la descripción del miembro del staff.
     * @return descripción del miembro del staff.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Setter de la descripción del miembro del staff.
     * @param descripcion nueva descripción del miembro del staff.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
