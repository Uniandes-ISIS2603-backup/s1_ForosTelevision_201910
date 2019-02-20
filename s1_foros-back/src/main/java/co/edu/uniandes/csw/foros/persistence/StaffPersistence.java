/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.StaffEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase de persistencia de los miembros del staff.
 * 
 * @author jf.castaneda
 */
@Stateless
public class StaffPersistence {
    
    @PersistenceContext(unitName="forosPU")
    protected EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(StaffPersistence.class.getName());
    
    /**
     * Método que crea un registro persistente de un miembro del staff.
     * @param staff miembro dels taff a registrar.
     * @return la misma entidad que entró por parámetro.
     */
    public StaffEntity create(StaffEntity staff){
        em.persist(staff);
        return staff;
    }
    /**
     * Método que busca un miembro del staff.
     * @param id id del miembro del staff.
     * @return entidad con la información del miembro del staff que se encontró.
     */
    public StaffEntity find(Long id){
       StaffEntity find= em.find(StaffEntity.class, id);
       LOGGER.log(Level.INFO, "Encontrar el miembro del staff con id={0}",id);
       return find;
    }
    /***
     * Método que retorna todos los miembros del staff existentes.
     * @return lista ordenada descendetemente por nombre con todos los miembros del staff.
     */
    public List<StaffEntity> getAll(){
        LOGGER.log(Level.INFO, "Generando lista de miembros del Staff");
        TypedQuery<StaffEntity> tp = em.createQuery("SELECT u FROM StaffEntity u ORDER BY u.nombre ASC", StaffEntity.class);
        return tp.getResultList();
    }
    
     /**
     * Método que actualiza la información de un miembro del staff.
     * @param staffEntity entidad con la información nueva del miembro del staff.
     * @return la entidad del usuario con todos los cambios aplicados.
     */
    public StaffEntity update(StaffEntity staffEntity) {
        LOGGER.log(Level.INFO, "Actualizando el miembro del staff con id={0}", staffEntity.getId());
        return em.merge(staffEntity);
    }

    /**
     * Método que borra un miembro del staff.
     * @param id id del miembro del staff.
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando el miembro del staff con id={0}", id);
        StaffEntity staffEntity = find(id);
        em.remove(staffEntity);
    }
}
