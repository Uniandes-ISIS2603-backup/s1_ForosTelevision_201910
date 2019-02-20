/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jhonattan Fonseca.
 */
public class ProductoraDetailDTO extends ProductoraDTO implements Serializable {

    private List<ProduccionDTO> producciones;

    /**
     * Retorna la lista de produciones de la productora.
     *
     * @return
     */
    public List<ProduccionDTO> getProducciones() {
        return producciones;
    }

    /**
     * Sobreescribe la lista de producciones de la productora.
     *
     * @param producciones
     */
    public void setProducciones(List<ProduccionDTO> producciones) {
        this.producciones = producciones;
    }

}
