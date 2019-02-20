/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.EstadoEntity;
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
public class EstadoPersistence {
    
    /**
     * Entity Manager de la clase.
     */
    @PersistenceContext(unitName = "forosPU")
    protected EntityManager em;
    
    /**
     * Crea una entidad de estado
     * @param entity entidad a crear
     * @return entity entidad creada
     */
    public EstadoEntity create(EstadoEntity entity){
        em.persist(entity);
        return entity;
    }
    
    /**
     * Obtiene todas las instancias de estado
     * @return lista con todos los estados
     */
    public List<EstadoEntity> findAll(){    
        TypedQuery query = em.createQuery("select u from EstadoEntity u", EstadoEntity.class);
        return query.getResultList();
    }
    
    /**
     * 
     * @param estadoId
     * @return 
     */
    public EstadoEntity find(Long estadoId){
        return em.find(EstadoEntity.class, estadoId);
    }
    
    
    /**
     * 
     * @param estado
     * @return 
     */
    public EstadoEntity update(EstadoEntity estado){
        return em.merge(estado);
    }
    
    /**
     * 
     * @param entityId 
     */
    public void delete(Long entityId){
        
        EstadoEntity temp = em.find(EstadoEntity.class, entityId);
        em.remove(temp);
    }
    
}
