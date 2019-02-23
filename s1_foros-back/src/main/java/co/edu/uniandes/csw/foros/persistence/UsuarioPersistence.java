package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
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
public class UsuarioPersistence {
    
    @PersistenceContext(unitName="forosPU")
    protected EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());
    
    /**
     * Crea un registro persistente de usuario
     * @param usuario entidad a registrar
     * @return instancia de usuario registrado
     */
    public UsuarioEntity create(UsuarioEntity usuario){
        em.persist(usuario);
        return usuario;
    }
    /**
     * Busca un usuario
     * @param userId identificador de usuario
     * @return instancia de UsuarioEntity
     */
    public UsuarioEntity find(Long userId){
       UsuarioEntity find= em.find(UsuarioEntity.class, userId);
       LOGGER.log(Level.INFO, "Encontrar  el usuario con id={0}",userId);
       return find;
    }
    /***
     * Lista de todos los usuarios
     * @return Lista ordenada descendetemente por nombre
     */
    public List<UsuarioEntity> getAll(){
        LOGGER.log(Level.INFO, "Generando Lista de Usuarios");
        TypedQuery<UsuarioEntity> tp=em.createQuery("SELECT u FROM UsuarioEntity u ORDER BY u.nombre ASC",UsuarioEntity.class);
        return tp.getResultList();
    }
    /**
     * Busca los usuarios por nombre
     * @param nombre registrado
     * @return  usuarios con el mismo nombre
     */
    public List<UsuarioEntity>findName(String nombre){
        TypedQuery<UsuarioEntity> tp=em.createQuery("SELECT u FROM UsuarioEntity u WHERE u.nombre = :name",UsuarioEntity.class);
        tp.setParameter("name",nombre);
        return tp.getResultList();
    }
    /**
     * Busca un usuario por email
     * @param email registrado por usuario
     * @return usuarios con el mismo email
     */
    
     public List<UsuarioEntity>findEmail(String email){
        TypedQuery<UsuarioEntity> tp=em.createQuery("SELECT u FROM UsuarioEntity u WHERE u.email = :email",UsuarioEntity.class);
        tp.setParameter("email",email);
        return tp.getResultList();
    }
    
     /**
     * Actualiza un usuario.
     * @return un usuario con los cambios aplicados.
     */
    public UsuarioEntity update(UsuarioEntity usuarioEntity) {
        LOGGER.log(Level.INFO, "Actualizando el usuario con id={0}", usuarioEntity.getId());
        return em.merge(usuarioEntity);
    }

    /**
     * Borra un usuario de la base de datos recibiendo como argumento el id de
     * la usuario
     *
     * @param userId: id correspondiente del usuario a borrar.
     */
    public void delete(Long userId) {
        LOGGER.log(Level.INFO, "Borrando el usuario con id={0}", userId);
        UsuarioEntity authorEntity = find(userId);
        em.remove(authorEntity);
    }
    
    
}
