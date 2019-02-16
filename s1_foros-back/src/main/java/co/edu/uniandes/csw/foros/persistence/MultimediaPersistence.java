package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.MultimediaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
/**
 *
 * @author bsrincon
 */

@Stateless
public class MultimediaPersistence {
    
    @PersistenceContext(unitName="forosPU")
    protected EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());
    
    /**
     * Crea un nuevo registro de multimedia
     * @param multimedia instancia a persistir
     * @return instancia que fue almacenada
     */
    public MultimediaEntity create(MultimediaEntity multimedia){
        em.persist(multimedia);
        return multimedia;
    }
    /**
     * Busca un recurso especifico
     * @param recursoID id principal del recurso
     * @return  Instancia de MultimediaEntity
     */
    public MultimediaEntity find(Long recursoID){
       MultimediaEntity find= em.find(MultimediaEntity.class, recursoID);
       LOGGER.log(Level.INFO, "Encontrar  el recurso multimedia con id={0}",recursoID);
       return find;
    }
    /***
     * Lista de todos los recursos multimedia
     * @return Lista de recursos
     */
    public List<MultimediaEntity> getAll(){
        LOGGER.log(Level.INFO, "Generando Lista de Multimedia");
        TypedQuery<MultimediaEntity> tp=em.createQuery("SELECT u FROM MultimediaEntity u ",MultimediaEntity.class);
        return tp.getResultList();
    }
    
     /**
     * Actualiza un recurso multimedia.
     
     * @return un recurso multimedia con los cambios aplicados.
     */
    public MultimediaEntity update(MultimediaEntity mulEntity) {
        LOGGER.log(Level.INFO, "Actualizando el recurso multimedia con id={0}", mulEntity.getId());
        return em.merge(mulEntity);
    }

    /**
     * Borra un recurso multimedia de la base de datos recibiendo como argumento el id del recurso
     *
     * @param recursoId: id correspondiente del recurso a borrar.
     */
    public void delete(Long recursoId) {
        LOGGER.log(Level.INFO, "Borrando el recurso multimedia con id={0}", recursoId);
        MultimediaEntity mlEntity = find(recursoId);
        em.remove(mlEntity);
    }
    
    
    
}
