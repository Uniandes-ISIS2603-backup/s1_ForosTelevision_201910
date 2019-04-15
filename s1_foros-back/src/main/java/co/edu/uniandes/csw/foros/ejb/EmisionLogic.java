/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.EmisionPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ne.ortega
 */
@Stateless
public class EmisionLogic {
    
    /**
     * Persistencia del recurso.
     */
    @Inject
    private EmisionPersistence persistence;
    
    /**
     * Método que crea una entidad del recurso.
     * @param entidad - entidad a crear.
     * @throws BusinessLogicException - Lanza cualquier excepción que no cumpla las reglas de negocio.
     * @return entidad creada.
     */
    public EmisionEntity createEmision(EmisionEntity entidad) throws BusinessLogicException{
        if(persistence.find(entidad.getId())!=null){
              throw new BusinessLogicException("No pueden haber dos emisiones iguales");
        }
        EmisionEntity emision = persistence.create(entidad);
        return emision;
    }
    
    /**
     * Método que obtiene todas las entidades del recurso.
     * @return una lista con todas las emisiones
     */
    public List<EmisionEntity> getEmisiones(){
        List<EmisionEntity> emisiones = persistence.findAll();
        return emisiones;
    }
    
    /**
     * Método que obtiene una entidad del recurso.
     * @param id - id de la emision a buscar
     * @return emision - emision que tiene ese id.
     */
    public EmisionEntity getEmisionPorId(Long id) throws BusinessLogicException{
        EmisionEntity emision = persistence.find(id);
        if(emision!=null){
            return emision;
        }
        else{
           throw new BusinessLogicException("El recurso no existe");
        }   
    }
      
    /**
     * Método para actualizar una emisión.
     * @param id - Identificador de la emision a actualizar
     * @param emision - Modificaciones que se realizarán.
     * @return emision actualizada.
     */
    public EmisionEntity actualizarEmision(Long id, EmisionEntity emision){
        EmisionEntity emisionVieja = persistence.find(id);
        if(emisionVieja==null){
            return null;
        }
        else{
            EmisionEntity emisionNueva = persistence.update(emision);
            return emisionNueva;
        }
    }
    
    /**
     * Metodo para borrar una emision
     * @param id - Identificador de la emision a borrar.
     */
    public void borrarEmision(Long id){
        EmisionEntity emision = persistence.find(id);
        if(emision!=null){
            persistence.delete(id);
        }
    }
    
}