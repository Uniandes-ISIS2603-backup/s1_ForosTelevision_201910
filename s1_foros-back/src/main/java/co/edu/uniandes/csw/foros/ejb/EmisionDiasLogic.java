/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.DiaEntity;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.persistence.EmisionPersistence;
import co.edu.uniandes.csw.foros.persistence.DiaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ne.ortega
 */
@Stateless
public class EmisionDiasLogic {
    
    @Inject
    private EmisionPersistence emPersistence;
    
    @Inject
    private DiaPersistence diaPersistence;
    
    
    public DiaEntity agregarDia(Long emisionId, Long diaId){
        EmisionEntity emision = emPersistence.find(emisionId);
        DiaEntity dia = diaPersistence.find(diaId);
        emision.getDias().add(dia);
        return dia;
    }
    
    public List<DiaEntity> obtenerDias(Long emisionId){
        EmisionEntity emision = emPersistence.find(emisionId);
        return emision.getDias();
    }
    
    public DiaEntity obtenerDia(Long emisionId, Long diaId){
        EmisionEntity emision = emPersistence.find(emisionId);
        DiaEntity dia = diaPersistence.find(diaId);
        List<DiaEntity> dias = emision.getDias();
        if(!dias.isEmpty()){
            for(DiaEntity ent : dias){
                if(ent.getId().equals(dia.getId())){
                    return ent;
                }
            }
        }
        return dia;
    }
    
    public List<DiaEntity> actualizarDias(Long emisionId, List<DiaEntity> dias){
        EmisionEntity emision = emPersistence.find(emisionId);
        if(!dias.isEmpty()){
            emision.setDias(dias);
        }
        return emision.getDias();
    }
    
    public void eliminarDia(Long emisionId, Long diaId){
        EmisionEntity emision = emPersistence.find(emisionId);
        DiaEntity dia = diaPersistence.find(diaId);
        List<DiaEntity> dias = emision.getDias();
        if(!dias.isEmpty()){
            if(dias.contains(dia)){
                dias.remove(dia);
            }
        }
    }
}
