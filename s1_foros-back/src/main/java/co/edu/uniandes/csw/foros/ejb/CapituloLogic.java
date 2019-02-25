/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.CapituloEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CapituloPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ne.ortega
 */
@Stateless
public class CapituloLogic {
    
    /**
     * Clase de Persistencia del recurso
     */
    @Inject
    private CapituloPersistence persistence;
    
    
    /**
     * Método que crea una entidad del recurso.
     * @param entidad - entidad a crear.
     * @throws BusinessLogicException - Lanza cualquier excepción que no cumpla las reglas de negocio.
     * @return entidad creada.
     */
    public CapituloEntity createCapitulo(CapituloEntity entidad) throws BusinessLogicException{
        if(entidad!=null){
            if(entidad.getDuracion() < 0){
                throw new BusinessLogicException("La duración de un capítulo no puede ser negativa");
            }
            if(entidad.getNombre().equals("")){
                throw new BusinessLogicException("El capítulo debe tener un nombre.");
            }
                    
            if(entidad.getDescripcion().equals("")){
                throw new BusinessLogicException("La descripción del capítulo no puede estar vacía.");
            }
            
            else{
                CapituloEntity nuevaEntidad = persistence.create(entidad);
                return nuevaEntidad;
            }
        }
        
        else{
            throw new BusinessLogicException("No se puede añadir un capítulo sin información");
        }
    }
    
    /**
     * Método que obtiene todas las entidades del recurso.
     * @return una lista con todos los capitulos
     */
    public List<CapituloEntity> getCapitulos(){
        List<CapituloEntity> capitulos = persistence.findAll();
        return capitulos;
    }
    
    /**
     * Método que obtiene una entidad del recurso.
     * @param id - id del capitulo a buscar
     * @return capitulo - capitulo que tiene ese id.
     */
    public CapituloEntity getCapituloPorId(Long id){
        CapituloEntity capitulo = persistence.find(id);
        return capitulo;
    }
    
    /**
     * Método que obtiene un capitulo por su nombre.
     * @param nombre - nombre del capitulo que se busca
     * @return capitulo al que le corresponde ese nombre.
     */
    public CapituloEntity getCapituloPorNombre(String nombre){
        if(!nombre.equals("")){
            CapituloEntity capitulo = persistence.findByName(nombre);
            return capitulo;
        }
        else{
            return null;
        }
    }
    
    /**
     * Método para actualizar un capítulo.
     * @param id - Identificador del capítulo a actualizar
     * @param capitulo - Modificaciones que se realizarán.
     * @return capitulo actualizado.
     */
    public CapituloEntity actualizarCapitulo(Long id, CapituloEntity capitulo){
        CapituloEntity capituloViejo = persistence.find(id);
        if(capituloViejo==null){
            return null;
        }
        else{
            CapituloEntity capituloNuevo = persistence.update(capitulo);
            return capituloNuevo;
        }
    }
    
    /**
     * Metodo para borrar un capitulo
     * @param id - Identificador del capitulo a borrar.
     */
    public void borrarCapitulo(Long id){
        CapituloEntity capitulo = persistence.find(id);
        if(capitulo!=null){
            persistence.delete(id);
        }
    }
}
