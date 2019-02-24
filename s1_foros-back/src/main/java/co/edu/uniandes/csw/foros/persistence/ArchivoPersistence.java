package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.ArchivoEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author bsrincon
 */
@Stateless
public class ArchivoPersistence {
    
    @PersistenceContext(unitName="forosPU")
    protected EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());
    
    /**
     * Crea un registro persistente de usuario
     * @param arch archivo a registrar
     * @return instancia de usuario registrado
     */
    public ArchivoEntity create(ArchivoEntity arch){
        em.persist(arch);
        return arch;
    }
    /**
     * Busca un archivo
     * @param id de recurso
     * @return instancia de UsuarioEntity
     */
    public ArchivoEntity find(Long id){
       ArchivoEntity find= em.find(ArchivoEntity.class, id);
       LOGGER.log(Level.INFO, "Encontrar  el archivo con id={0}",id);
       return find;
    }
     /**
     * Actualiza un archivo.
     * @return un archivo con los cambios aplicados.
     */
    public ArchivoEntity update(ArchivoEntity archivo) {
        LOGGER.log(Level.INFO, "Actualizando el archivo con id={0}", archivo.getId());
        return em.merge(archivo);
    }
    /**
     * Borra un archivo de la base de datos recibiendo como argumento el id
     * @param id: id correspondiente del archivo a borrar.
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando el archivo con id={0}", id);
        ArchivoEntity arch = find(id);
        em.remove(arch);
    }
}
