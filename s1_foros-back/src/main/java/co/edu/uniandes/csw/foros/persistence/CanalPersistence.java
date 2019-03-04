/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author mi.carrascal
 */

@Stateless
public class CanalPersistence { 
    
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());
    
    
    
    
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
    
    
       /**
     * Actualiza un usuario.
     * @return un usuario con los cambios aplicados.
     */
    public CanalEntity update(CanalEntity canalEntity) {
        LOGGER.log(Level.INFO, "Actualizando el canal con id={0}", canalEntity.getId());
        return em.merge(canalEntity);
    }
    
    
    public List<CanalEntity> findAll()
    {
         TypedQuery<CanalEntity> query = em.createQuery("select u from CanalEntity u", CanalEntity.class);
        return query.getResultList();
    }
    
     /**
     * Borra un canal de la base de datos recibiendo como argumento el id del
     * canal
     * @param canalId: id del canal a borrar
     */
    public void delete(Long resenaId) {
        LOGGER.log(Level.INFO, "Borrando el usuario con id={0}", resenaId);
        CanalEntity canalEntity = find(resenaId);
        em.remove(canalEntity);
    }
}
