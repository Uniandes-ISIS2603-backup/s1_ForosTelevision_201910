/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.EstadoEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.EstadoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
/**
 *
 * @author ne.ortega
 */
@Stateless
public class EstadoLogic {
    
    /**
     * Persistencia del recurso.
     */
    @Inject
    private EstadoPersistence persistence;
    
    /**
     * Método que crea una entidad del recurso.
     * @param entidad - entidad a crear.
     * @throws BusinessLogicException - Lanza cualquier excepción que no cumpla las reglas de negocio.
     * @return entidad creada.
     */
    public EstadoEntity createEstado(EstadoEntity entidad) throws BusinessLogicException{
        if(!(entidad.getEstado()>=0)){
            throw new BusinessLogicException("El estado no está en el rango permitido");
        }
        EstadoEntity estado = persistence.create(entidad);
        return estado;
    }
    
    /**
     * Método que obtiene todas las entidades del recurso.
     * @return una lista con todos los estados
     */
    public List<EstadoEntity> getEstados(){
        List<EstadoEntity> estados = persistence.findAll();
        return estados;
    }
    
    /**
     * Método que obtiene una entidad del recurso.
     * @param id - id del estado a buscar
     * @return estado - estado que tiene ese id.
     */
    public EstadoEntity getEstadoPorId(Long id){
        EstadoEntity estado = persistence.find(id);
        return estado;
    }
      
    /**
     * Método para actualizar una emisión.
     * @param id - Identificador del estado a actualizar
     * @param estado - Modificaciones que se realizarán.
     * @return estado actualizado.
     */
    public EstadoEntity actualizarEstado(Long id, EstadoEntity estado){
        EstadoEntity estadoViejo = persistence.find(id);
        if(estadoViejo==null){
            return null;
        }
        else{
            EstadoEntity estadoNuevo = persistence.update(estado);
            return estadoNuevo;
        }
    }
    
    /**
     * Metodo para borrar un estado
     * @param id - Identificador del estado a borrar.
     */
    public void borrarEstado(Long id){
        EstadoEntity estado = persistence.find(id);
        if(estado!=null){
            persistence.delete(id);
        }
    }
}
