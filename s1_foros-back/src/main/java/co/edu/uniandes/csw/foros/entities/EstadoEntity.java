/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ne.ortega
 */
@Entity
public class EstadoEntity extends BaseEntity implements Serializable {
    
    @PodamExclude
    @OneToOne
    private ProduccionEntity produccion;
    
    @PodamExclude
    @ManyToOne(fetch=LAZY)
    private UsuarioEntity usuarios;
    
    private int estado;
    
    /**
     * Constructor de la clase.
     */
    public EstadoEntity(){
        // Constructor vacío de la clase.
    }

    /**
     * @return the produccion
     */
    public ProduccionEntity getProduccion() {
        return produccion;
    }

    /**
     * @param produccion the produccion to set
     */
    public void setProduccion(ProduccionEntity produccion) {
        this.produccion = produccion;
    }

    /**
     * @return the usuarios
     */
    public UsuarioEntity getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(UsuarioEntity usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
}
