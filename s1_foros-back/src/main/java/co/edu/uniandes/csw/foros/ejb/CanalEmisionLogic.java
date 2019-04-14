/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import co.edu.uniandes.csw.foros.persistence.EmisionPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author mi.carrascal
 */
@Stateless
public class CanalEmisionLogic {
 
    
     @Inject
    private EmisionPersistence emPersistence;
    
    @Inject
    private CanalPersistence canalPersistence;
    
    
  
    
    public List<EmisionEntity> darEmisiones(Long canalId)
    {
        CanalEntity canal=canalPersistence.find(canalId);
        return canal.getEmisiones();
    }
    
    
    public EmisionEntity agregarEmision(Long emisionId, Long canalId){
        EmisionEntity emision = emPersistence.find(emisionId);
        
        CanalEntity canal = canalPersistence.find(canalId);
        canal.addEmision(emision);
        return emision;
    }
    
    public EmisionEntity obtenerEmision(Long emisionId,Long canalId){
        CanalEntity canal=canalPersistence.find(canalId);
        EmisionEntity emision=canal.getEmision(emisionId);
        return emision;
    }
        
    public void eliminarEmision(Long emisionId,Long canalId)throws BusinessLogicException{
        CanalEntity canal = canalPersistence.find(canalId);
        if(canal==null)
        {
            throw new BusinessLogicException("No existe el canal");
        }
        EmisionEntity emision=canal.getEmision(emisionId);
        canal.eliminarEmision(emisionId);
    
    }
    
    
    public EmisionEntity updateEmision(Long emisionId,Long canalId,EmisionEntity emision)
    {
         CanalEntity canal=canalPersistence.find(canalId);
         EmisionEntity emision2=canal.getEmision(emisionId);
         emPersistence.update(emision2);
        
         return emision2;
    }
    
}
