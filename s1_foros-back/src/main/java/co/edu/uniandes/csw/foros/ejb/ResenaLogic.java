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
    
    public ResenaEntity createResena(ResenaEntity resena) throws BusinessLogicException
    {
        
        if(resenaPersistence.find(resena.getId()).getDescripcion().length()>800)
        {
            throw new BusinessLogicException("La descripcion no puede ser mayor a 800 caracteres ");
        }
        resena=resenaPersistence.create(resena);
        return resena;
    }
//        if()
//        canal= resenaPersistence.create(canal);
//        return canal;
 
    
}
