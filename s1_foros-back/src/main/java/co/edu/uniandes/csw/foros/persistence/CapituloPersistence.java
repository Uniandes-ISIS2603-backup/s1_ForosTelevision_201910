/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.CapituloEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ne.ortega
 */
@Stateless
public class CapituloPersistence {
    
    /**
     * Entity Manager de la clase.
     */
    @PersistenceContext(unitName = "forosPU")
    protected EntityManager em;
    
    /**
     * Crea una entidad de capitulo
     * @param entity entidad a crear
     * @return entity entidad creada
     */
    public CapituloEntity create(CapituloEntity entity){
        em.persist(entity);
        return entity;
    }
    
    /**
     * Obtiene todas las instancias de capitulo
     * @return lista con todos los capitulos
     */
    public List<CapituloEntity> findAll(){    
        TypedQuery query = em.createQuery("select u from CapituloEntity u", CapituloEntity.class);
        return query.getResultList();
    }
    
    /**
     * 
     * @param capituloId
     * @return 
     */
    public CapituloEntity find(Long capituloId){
        return em.find(CapituloEntity.class, capituloId);
    }
    
    /**
     * 
     * @param nombreCap
     * @return 
     */
    public CapituloEntity findByName(String nombreCap){
        TypedQuery query = em.createQuery("select a from CapituloEntity a where a.nombre = :nombreCap", CapituloEntity.class);
        query = query.setParameter("nombreCap", nombreCap);
        List<CapituloEntity> capitulos = query.getResultList();
        CapituloEntity respuesta;
        if(capitulos==null){
            respuesta = null;
        }
        else if(capitulos.isEmpty()){
            respuesta = null;
        }
        else{
            respuesta = capitulos.get(0);
        }
        return respuesta;
    }
    
    /**
     * 
     * @param capitulo
     * @return 
     */
    public CapituloEntity update(CapituloEntity capitulo){
        return em.merge(capitulo);
    }
    
    /**
     * 
     * @param entityId 
     */
    public void delete(Long entityId){
        
        CapituloEntity temp = em.find(CapituloEntity.class, entityId);
        em.remove(temp);
    }
}
