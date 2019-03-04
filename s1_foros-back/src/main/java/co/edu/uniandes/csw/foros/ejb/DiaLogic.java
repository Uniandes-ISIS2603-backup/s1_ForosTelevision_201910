/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.DiaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.DiaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ne.ortega
 */
@Stateless
public class DiaLogic {
 
    /**
     * Persistencia del recurso Dia.
     */
    @Inject
    private DiaPersistence persistence;
    
    /**
     * Crea una entidad del recurso dia.
     * @param dia - dia que se va a añadir.
     * @return el dia creado
     * @throws BusinessLogicException lanza una excepción en caso de incumplir alguna regla de negocio. 
     */
    public DiaEntity createDia(DiaEntity dia) throws BusinessLogicException{
        if(dia!=null){
            if(!((dia.getNombre().equals("Lunes")) || (dia.getNombre().equals("Martes"))
                    || (dia.getNombre().equals("Miercoles")) || (dia.getNombre().equals("Jueves"))
                    || (dia.getNombre().equals("Viernes")) || (dia.getNombre().equals("Sabado"))
                    || (dia.getNombre().equals("Domingo")))){
                throw new BusinessLogicException("El nombre del día no corresponde a un día de la semana");
            }
        }
        DiaEntity respuesta = persistence.create(dia);
        
        return respuesta;
    }
    
    /**
     * Método que obtiene todas las entidades del recurso.
     * @return una lista con todos los dias.
     */
    public List<DiaEntity> getDias(){
        List<DiaEntity> dias = persistence.findAll();
        return dias;
    }
    
    /**
     * Método que obtiene una entidad del recurso.
     * @param id - id del dia a buscar
     * @return dia - dia que tiene ese id.
     */
    public DiaEntity getDiaPorId(Long id){
        DiaEntity dia = persistence.find(id);
        return dia;
    }
      
    /**
     * Método para actualizar un dia.
     * @param id - Identificador del dia a actualizar
     * @param dia - Modificaciones que se realizarán.
     * @return dia actualizado.
     */
    public DiaEntity actualizarDia(Long id, DiaEntity dia){
        DiaEntity diaViejo = persistence.find(id);
        if(diaViejo==null){
            return null;
        }
        else{
            DiaEntity diaNuevo = persistence.update(dia);
            return diaNuevo;
        }
    }
    
    /**
     * Metodo para borrar un dia
     * @param id - Identificador del dia a borrar.
     */
    public void borrarDia(Long id){
        DiaEntity dia = persistence.find(id);
        if(dia!=null){
            persistence.delete(id);
        }
    }
}
