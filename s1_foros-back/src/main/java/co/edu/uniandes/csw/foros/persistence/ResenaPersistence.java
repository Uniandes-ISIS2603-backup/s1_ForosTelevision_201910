
package co.edu.uniandes.csw.foros.persistence;


import static co.edu.uniandes.csw.foros.ejb.MultimediaLogic.validateFileExtn;
import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.MultimediaEntity;


import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author mi.carrascal
 */

@Stateless
public class ResenaPersistence {
        
        
    private static final Logger LOGGER = Logger.getLogger(ResenaPersistence.class.getName());
    
    @PersistenceContext(unitName = "forosPU")
    protected EntityManager em;
    
       /**
     * Método que crea un registro persistente
     * @param resenaEntity 
     * @return 
     */
    public ResenaEntity create(ResenaEntity resenaEntity)
    {
        em.persist(resenaEntity);
        return resenaEntity;
    }
      /**
     * Método que encuentra una resena por ID
     * @param resenaId el id de la reseña
     * @return una lista con las reseñas
     */
    public ResenaEntity find(Long resenaId)
    {
        return em.find(ResenaEntity.class, resenaId);
    
    }
    
      /**
     * Método que encuentra todas las resenas
     * @return una lista con las reseñas
     */
    public List<ResenaEntity> findAll()
    {
         TypedQuery<ResenaEntity> query = em.createQuery("select u from ResenaEntity u", ResenaEntity.class);
        return query.getResultList();
    }
    
    
       /**
     * Actualiza una reseña
     * @return una reseña con los cambios aplicados.
     */
    public ResenaEntity update(ResenaEntity resenaEntity) {
        LOGGER.log(Level.INFO, "Actualizando el canal con id={0}", resenaEntity.getId());
        return em.merge(resenaEntity);
    }
    
    /**
     * Método que borra una reseña.
     * @param id id de la resena.
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando la reseña con id={0}", id);
        ResenaEntity resenaEntity = find(id);
        em.remove(resenaEntity);
    }
   
    
   
    
    
}