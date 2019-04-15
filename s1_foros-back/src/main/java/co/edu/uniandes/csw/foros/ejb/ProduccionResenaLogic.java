/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.persistence.ResenaPersistence;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ProduccionResenaLogic {
 

    @Inject
    private ResenaPersistence resPersistence;
    
    @Inject
    private ProduccionPersistence prodPersistence;
    
     public ResenaEntity agregarResena(Long resenaId, Long produccionId){
        ProduccionEntity produccionEntity =prodPersistence.find(produccionId);
        ResenaEntity resena = resPersistence.find(resenaId);
   
        List<ResenaEntity> resenas=produccionEntity.getResenas();
        resenas.add(resena);
        produccionEntity.setResenas(resenas);
        
        return resena;
    }

    public List<ResenaEntity> darResenas(Long produccionId) {
        ProduccionEntity produccionEntity =prodPersistence.find(produccionId);
        List<ResenaEntity> resenasEntity=produccionEntity.getResenas();
        return resenasEntity;
    }
}
