/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import java.time.Clock;
import java.util.List;
import java.util.Objects;
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
        if(canal.getNombre()=="")
        {
               throw new BusinessLogicException("El nombre no puede ser vacío");
        }
        if(canal.getRating()<0)
        {
             throw new BusinessLogicException("Rating no puede ser nagtivo");
        }
       
        canal= canalPersistence.create(canal);
        return canal;
 
    }
   
    
     /**
     * Elimina un canal 
     * @param canalId id del canal
     * @param emisionId identificador de emision
     * @throws BusinessLogicException si el canal no existe
     */
    public void eliminarCanal(Long canalId) throws BusinessLogicException{
     
        CanalEntity entity=canalPersistence.find(canalId);
        if(entity==null)
        {
            throw  new BusinessLogicException("No existe el canal");
        }
        else{
            canalPersistence.delete(canalId);
        }
           
        
    }
 /**
     * Retorna un id
     * @param canalId id del canal
     * @throws BusinessLogicException si el canal no existe
     */
    public CanalEntity darCanal(Long canalId) throws BusinessLogicException{
        
       CanalEntity entity=canalPersistence.find(canalId);
       if(entity==null)
       {
           throw  new BusinessLogicException("No existe el canal");
       }
       return entity;
    }

    
     /**
     * Retorna un id
     * @param canalId id del canal
     * @throws BusinessLogicException si el canal no existe
     */
    public CanalEntity actualizarCanal(CanalEntity canal, Long canalId) throws BusinessLogicException{
        
       CanalEntity entity=canalPersistence.find(canalId);
       if(canal.getRating()<0)
       {
           throw new BusinessLogicException("El rating no puede ser negativo");
       }
       entity = canalPersistence.update(canal);
       
       return entity;
    }

      /**
     * Lista de todos los usuarios
     *
     * @return usuarios registrados
     * @throws BusinessLogicException si no hay usuario registrados
     */
    public List<CanalEntity> getAll() throws BusinessLogicException {
        List<CanalEntity> users = canalPersistence.findAll();
        if (users.isEmpty()) throw new BusinessLogicException("No hay usuarios registrados");
        return users;
    }
     
   
}
