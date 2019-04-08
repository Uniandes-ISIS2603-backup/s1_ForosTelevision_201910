/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import co.edu.uniandes.csw.foros.persistence.EmisionPersistence;
import co.edu.uniandes.csw.foros.persistence.ResenaPersistence;
import co.edu.uniandes.csw.foros.persistence.UsuarioPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author mi.carrascal
 */
@Stateless
public class ResenaUsuarioLogic {
    
     @Inject
    private UsuarioPersistence uPersistence;
    
    @Inject
    private ResenaPersistence rPersistence;
    
    
    public UsuarioEntity darUsuarioResena(Long resenaId){
        ResenaEntity resena=rPersistence.find(resenaId);
        UsuarioEntity usuario=resena.getUsuarioResena();
        return usuario;
    }
    
    
  
    
}
