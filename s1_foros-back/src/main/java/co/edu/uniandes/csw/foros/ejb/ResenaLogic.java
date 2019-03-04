/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import co.edu.uniandes.csw.foros.persistence.ResenaPersistence;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author mi.carrascal
 */
@Stateless
public class ResenaLogic {
    
    @Inject
    private ResenaPersistence resenaPersistence;
    
    public ResenaEntity createResenaDescripcion(ResenaEntity resena) throws BusinessLogicException
    {
        
        if((resena.getDescripcion().length()>800))
        {
            throw new BusinessLogicException("La descripcion no puede ser mayor a 800 caracteres ");
        }
       
   
        resena=resenaPersistence.create(resena);
        return resena;
    }

    
     public ResenaEntity createResenacalificacionProduccion(ResenaEntity resena,Integer calificacion) throws BusinessLogicException
    {
        if(calificacion<0)
              {
            throw new BusinessLogicException("La calificacion de la Producción no puede ser negativa");
        }  
        resena=resenaPersistence.create(resena);
        return resena;
    }
    
    
     public ResenaEntity createResenacalificacionResena(ResenaEntity resena,Integer calificacion) throws BusinessLogicException
    {
        if(calificacion<0)
              {
            throw new BusinessLogicException("La calificacion de la Resena no puede ser negativa");
        }  
        resena=resenaPersistence.create(resena);
        return resena;
    }
     
     public ResenaEntity createResenaFecha(ResenaEntity resena,Date fecha) throws BusinessLogicException
     {
        Calendar fechaActual= new GregorianCalendar();
         if(fecha.after(fechaActual.getTime()))
         {
             throw new BusinessLogicException("La fecha de la reseña no puede ser mayor que la actual");
         }
         resena=resenaPersistence.create(resena);
        return resena;
     }
    
    
}
