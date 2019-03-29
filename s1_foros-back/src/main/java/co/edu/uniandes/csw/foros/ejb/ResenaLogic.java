/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ResenaPersistence;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author mi.carrascal
 */
@Stateless
public class ResenaLogic {
    
    @Inject
    private ResenaPersistence resenaPersistence;
    @Inject
    private UsuarioLogic usuarioLogic;
    @Inject
    private ProduccionLogic produccionLogic;
    
    public ResenaEntity createResena(Long user_id,Long produccion_id,ResenaEntity resena) throws BusinessLogicException
    {
        Calendar fechaActual= new GregorianCalendar();
        if((resena.getDescripcion().length()>800))
        {
            throw new BusinessLogicException("La descripcion no puede ser mayor a 800 caracteres ");
        }
       if(resena.getCalificacionProduccion()<0)
              {
            throw new BusinessLogicException("La calificacion de la Producción no puede ser negativa");
        }  
        if(resena.getCalificacionResena()<0)
              {
            throw new BusinessLogicException("La calificacion de la Resena no puede ser negativa");
        }   
         if(resena.getFecha().after(fechaActual.getTime()))
         {
             throw new BusinessLogicException("La fecha de la reseña no puede ser mayor que la actual");
         }
        UsuarioEntity usuarioEntity=usuarioLogic.find(user_id);
        if(usuarioEntity!=null)
            resena.setUsuarioResena(usuarioEntity);
        ProduccionEntity produccionEntity=produccionLogic.darProduccion(produccion_id);
        if(produccionEntity!=null)
            resena.setProduccionResena(produccionEntity);
        return resenaPersistence.create(resena);
    }

     /**
     * Lista de todas las reseñas
     *
     * @return reseñas registradas
     * @throws BusinessLogicException si no hay reseñas registrados
     */
    public List<ResenaEntity> getAll() throws BusinessLogicException {
        List<ResenaEntity> resenas= resenaPersistence.findAll();
        if (resenas.isEmpty()) throw new BusinessLogicException("No hay resenas registrados");
        return resenas;
    }

    /**
     * Encuentra una reseña
     *
     * @param user identificador reseña
     * @return Resena
     * @throws BusinessLogicException no existe la reseña
     */
    public ResenaEntity find(Long resenaId) throws BusinessLogicException {
        ResenaEntity u = resenaPersistence.find(resenaId);
        if (u == null) throw new BusinessLogicException("No existe la resena ");
        return u;
    }

    /**
     * actualiza resena
     *
     * @param entity actualiza la informacion de una resena
     * @return instancia actualizada
     */

    public ResenaEntity update(ResenaEntity entity) throws BusinessLogicException {
     
        return resenaPersistence.update(entity);
    }

     /**
     * Elimina una  resena
     *
     * @param id el id de la 
     * @return instancia actualizada
     */
    public void delete(Long id) {
        resenaPersistence.delete(id);
    }
    
    
    
}
