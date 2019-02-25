/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */

@Stateless
public class CanalPersistence {
    
    @PersistenceContext(unitName = "forosPU")
    protected EntityManager em;
    
    public CanalEntity create(CanalEntity canalEntity)
    {
        em.persist(canalEntity);
        return canalEntity;
    }
    
    public CanalEntity find(Long canalId)
    {
        return em.find(CanalEntity.class, canalId);
    
    }
    
    /**
     * Busca un canal por el nombre
     * @param nombre del canal
     * @return Canal con el nombre
     */
    public CanalEntity findByName(String nombre){
        TypedQuery<CanalEntity> query=em.createQuery("SELECT u FROM CanalEntity u WHERE u.nombre = :nombre",CanalEntity.class);
        query=query.setParameter("nombre",nombre);
        List<CanalEntity> lista =query.getResultList();
        CanalEntity resultado;
        if(lista==null)
        {
            resultado=null;
        }
        else if(lista.isEmpty())
        {
            resultado=null;
        }
        else
        {
            resultado=lista.get(0);
        }
        return resultado;
        
    }
    
    
    public List<CanalEntity> findAll()
    {
         TypedQuery<CanalEntity> query = em.createQuery("select u from CanalEntity u", CanalEntity.class);
        return query.getResultList();
    }
}
