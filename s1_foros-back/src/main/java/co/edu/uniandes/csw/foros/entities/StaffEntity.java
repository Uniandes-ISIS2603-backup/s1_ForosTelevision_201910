/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author jf.castaneda
 */
public class StaffEntity extends BaseEntity implements Serializable{
    
    /**
     * Relación del miembro del staff con una producción.
     */
    @ManyToMany(mappedBy="staff", fetch=LAZY)
    @PodamExclude
    private List<ProduccionEntity> producciones;
    
    /**
     * Rol que el miembro del staff puede cumplir.
     */
    public enum RolStaff {
        ACTOR, DIRECTOR, ACTORYDIRECTOR
    }
    
    /**
     * Nombre del miembro del staff.
     */
    private String nombre;
    
    /**
     * Rol que cumple el staff dentro de la producción.
     */
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
     * @return lista con las producciones en las que ha participado el miembro del staff.
     */
    public List<ProduccionEntity> darProducciones() {
        return producciones;
    }
    
    /**
     * Setter de las producciones en las que ha participado un miembro del staff.
     * @param producciones producciones en las que ha participado un miembro del staff.
     */
    public void editarProducciones(List<ProduccionEntity> producciones) {
        this.producciones = producciones;
    }

    /**
     * Getter del rol del miembro del staff.
     * @return rol del miembro del staff.
     */
    public RolStaff darRol() {
        return rol;
    }

    /**
     * Setter del rol del miembro del staff.
     * @param rol nuevo rol del miembro del staff.
     */
    public void editarRol(RolStaff rol) {
        this.rol = rol;
    }

    /**
     * Getter del nombre del miembro del staff.
     * @return nombre del miembro del staff.
     */
    public String darNombre() {
        return nombre;
    }

    /**
     * Setter del nombre del miembro del staff.
     * @param nombre nuevo nombre del miembro del staff.
     */
    public void editarNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de la foto del miembro del staff.
     * @return ruta de la foto del miembro del staff.
     */
    public String darFoto() {
        return foto;
    }

    /**
     * Setter de la nueva foto del miembro del staff.
     * @param foto nueva ruta de la foto del mimebro del staff.
     */
    public void editarFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Getter de la descripción del miembro del staff.
     * @return descripción del miembro del staff.
     */
    public String darDescripcion() {
        return descripcion;
    }

    /**
     * Setter de la descripción del miembro del staff.
     * @param descripcion nueva descripción del miembro del staff.
     */
    public void editarDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
