/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import co.edu.uniandes.csw.foros.persistence.EmisionPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ne.ortega
 */
@Stateless
public class EmisionCanalLogic {
    
    @Inject
    private EmisionPersistence emPersistence;
    
    @Inject
    private CanalPersistence canalPersistence;
    
    
    public CanalEntity agregarCanal(Long emisionId, Long canalId){
        EmisionEntity emision = emPersistence.find(emisionId);
        CanalEntity canal = canalPersistence.find(canalId);
        emision.setCanal(canal);
        return canal;
    }
    
    public CanalEntity obtenerCanal(Long emisionId){
        EmisionEntity emision = emPersistence.find(emisionId);
        return emision.getCanal();
    }
    
    public EmisionEntity actualizarCanal(Long emisionId, CanalEntity canal){
        EmisionEntity emision = emPersistence.find(emisionId);
        emision.setCanal(canal);
        return emision;
    }
    
    public void eliminarCanal(Long emisionId){
        EmisionEntity emision = emPersistence.find(emisionId);
        emision.setCanal(null);
    }
    
}
