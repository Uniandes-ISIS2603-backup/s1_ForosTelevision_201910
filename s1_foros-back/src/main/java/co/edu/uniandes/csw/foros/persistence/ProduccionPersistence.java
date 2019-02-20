/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.StaffEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author jf.castaneda
 */
@Stateless
public class ProduccionPersistence {
    
    @PersistenceContext(unitName="forosPU")
    protected EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(ProduccionPersistence.class.getName());
    
    /**
     * Método que crea un registro persistente de una producción.
     * @param produccion produccion a registrar.
     * @return la misma entidad que entró por parámetro.
     */
    public ProduccionEntity create(ProduccionEntity produccion){
        em.persist(produccion);
        return produccion;
    }
    /**
     * Método que busca una producción.
     * @param id id de la producción.
     * @return entidad con la información de la producción que se encontró.
     */
    public ProduccionEntity find(Long id){
       ProduccionEntity find= em.find(ProduccionEntity.class, id);
       LOGGER.log(Level.INFO, "Encontrar la producción con id={0}",id);
       return find;
    }
    /***
     * Método que retorna todas las producciones existentes.
     * @return lista ordenada descendetemente por nombre con todas las producciones.
     */
    public List<ProduccionEntity> getAll(){
        LOGGER.log(Level.INFO, "Generando lista de producciones");
        TypedQuery<ProduccionEntity> tp = em.createQuery("SELECT u FROM ProduccionEntity u ORDER BY u.nombre ASC", ProduccionEntity.class);
        return tp.getResultList();
    }
    
     /**
     * Método que actualiza la información de una producción.
     * @param produccionEntity entidad con la información nueva de la producción.
     * @return la entidad de la producción con todos los cambios aplicados.
     */
    public ProduccionEntity update(ProduccionEntity produccionEntity) {
        LOGGER.log(Level.INFO, "Actualizando la producción con id={0}", produccionEntity.getId());
        return em.merge(produccionEntity);
    }

    /**
     * Método que borra una producción.
     * @param id id de la producción.
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando la producción con id={0}", id);
        ProduccionEntity produccionEntity = find(id);
        em.remove(produccionEntity);
    }
    
}
