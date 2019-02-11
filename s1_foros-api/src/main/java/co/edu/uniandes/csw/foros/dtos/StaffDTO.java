/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.dtos;

import java.io.Serializable;

/**
 *
 * @author jf.castaneda
 */
public class StaffDTO implements Serializable {
    
    /**
     * Rol que el miembro del staff puede cumplir.
     */
    public enum RolStaff {
        ACTOR, DIRECTOR, ACTORYDIRECTOR
    }
    
    /**
     * Id del miembro del staff de al menos una película.
     */
    private long idStaff;
    
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
     * Constructor del DTO de un miembro del staff de al menos una película.
     */
    public StaffDTO() {
    
    }
    
    /**
     * Getter del id del miembro del staff.
     * @return id del miembro del staff.
     */
    public long darIdStaff() {
        return idStaff;
    }

    /**
     * Setter del id del miembro del staff.
     * @param idStaff nuevo id del miembro del staff.
     */
    public void editarIdStaff(long idStaff) {
        this.idStaff = idStaff;
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
