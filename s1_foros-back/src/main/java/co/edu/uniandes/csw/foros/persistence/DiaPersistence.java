/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.DiaEntity;
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
public class DiaPersistence {
    
    /**
     * Entity Manager de la clase.
     */
    @PersistenceContext(unitName = "forosPU")
    protected EntityManager em;
    
    public DiaEntity create(DiaEntity entity){
        em.persist(entity);
        return entity;
    }
    
    public List<DiaEntity> findAll(){
        TypedQuery query = em.createQuery("select u from DiaEntity u", DiaEntity.class);
        return query.getResultList();
    }
    
    public DiaEntity find(Long id){
        return em.find(DiaEntity.class, id);
    }
    
    public DiaEntity update(DiaEntity entity){
        return em.merge(entity);
    }
    
    public void delete(Long id){
        DiaEntity rem = em.find(DiaEntity.class, id);
        em.remove(rem);
    }
}
