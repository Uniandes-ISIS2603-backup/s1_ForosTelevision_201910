/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.EstadoEntity;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.persistence.EstadoPersistence;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ne.ortega
 */
@Stateless
public class EstadoProduccionLogic {
    
    @Inject
    private EstadoPersistence estPersistence;
    
    @Inject
    private ProduccionPersistence prodPersistence;
    
    
    public ProduccionEntity agregarProduccion(Long emisionId, Long produccionId){
        EstadoEntity estado = estPersistence.find(emisionId);
        ProduccionEntity produccion = prodPersistence.find(produccionId);
        estado.setProduccion(produccion);
        return produccion;
    }
      
    public ProduccionEntity obtenerProduccion(Long emisionId){
        EstadoEntity estado = estPersistence.find(emisionId);
        ProduccionEntity produccion = estado.getProduccion();
        return produccion;
    }
    
    public ProduccionEntity actualizarProduccion(Long emisionId, ProduccionEntity produccion){
        EstadoEntity estado = estPersistence.find(emisionId);
        estado.setProduccion(produccion);
        return estado.getProduccion();
    }
    
    public void eliminarProduccion(Long emisionId){
        EstadoEntity estado = estPersistence.find(emisionId);
        estado.setProduccion(null);
    }   
}
