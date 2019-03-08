/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.persistence.EmisionPersistence;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ne.ortega
 */
@Stateless
public class EmisionProduccionLogic {
    
    @Inject
    private EmisionPersistence emPersistence;
    
    @Inject
    private ProduccionPersistence prodPersistence;
    
    
    public ProduccionEntity agregarProduccion(Long emisionId, Long produccionId){
        EmisionEntity emision = emPersistence.find(emisionId);
        ProduccionEntity produccion = prodPersistence.find(produccionId);
        emision.setProduccion(produccion);
        return produccion;
    }
    
    
    public ProduccionEntity obtenerProduccion(Long emisionId){
        return emPersistence.find(emisionId).getProduccion();
    }
  
    public ProduccionEntity actualizarProduccion(Long emisionId, ProduccionEntity produccion){
        EmisionEntity emision = emPersistence.find(emisionId);
        emision.setProduccion(produccion);
        return emision.getProduccion();
    }
    
    public void eliminarProduccion(Long emisionId){
        EmisionEntity emision = emPersistence.find(emisionId);
        emision.setProduccion(null);
    }   
}
