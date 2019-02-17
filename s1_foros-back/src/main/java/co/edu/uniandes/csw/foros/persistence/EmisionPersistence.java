/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.EmisionEntity;
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
public class EmisionPersistence {
    
    /**
     * Entity Manager de la clase.
     */
    @PersistenceContext(unitName = "forosPU")
    protected EntityManager em;
    
    public EmisionEntity create(EmisionEntity entity){
        em.persist(entity);
        return entity;
    }
    
    public List<EmisionEntity> findAll(){
        TypedQuery query = em.createQuery("select u from EmisionEntity u", EmisionEntity.class);
        return query.getResultList();
    }
    
    public EmisionEntity find(Long id){
        return em.find(EmisionEntity.class, id);
    }
    
    public EmisionEntity update(EmisionEntity entity){
        return em.merge(entity);
    }
    
    public void delete(Long id){
        EmisionEntity rem = em.find(EmisionEntity.class, id);
        em.remove(rem);
    }
    
}
