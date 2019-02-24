/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author mi.carrascal
 */
@Stateless
public class CanalLogic {
    
    @Inject
    private CanalPersistence canalPersistence;
    
    public CanalEntity createCanal(CanalEntity canal) throws BusinessLogicException
    {
        if(canalPersistence.findByName(canal.getNombre())!=null)
        {
            throw new BusinessLogicException("Ya existe un canal con ese nombre \"" + canal.getNombre());
        }
        canal= canalPersistence.create(canal);
        return canal;
 
    }
    
}
