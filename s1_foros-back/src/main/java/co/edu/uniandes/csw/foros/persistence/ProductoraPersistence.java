/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.persistence;


import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jhonattan Fonseca
 */
@Stateless
public class ProductoraPersistence {

    @PersistenceContext(unitName = "forosPU")
    protected EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(ProductoraPersistence.class.getName());

    /**
     * Crea un registro persistente de una productora.
     *
     * @param productora entidad a persistir.
     * @return entidad persistida.
     */
    public ProductoraEntity create(ProductoraEntity productora) {
        em.persist(productora);
        return productora;
    }

    /**
     * Busca una productora por su Id.
     *
     * @param productoraId identificador de la productora.
     * @return instancia de la productora encontrada.
     */
    public ProductoraEntity find(Long productoraId) {
        ProductoraEntity find = em.find(ProductoraEntity.class, productoraId);
        LOGGER.log(Level.INFO, "Encontrar la productora con id ={0}", productoraId);
        return find;
    }

    /**
     * Busca las productoras por nombre
     *
     * @param nombre registrado
     * @return productoras con el mismo nombre
     */
    public List<ProductoraEntity> findByName(String nombre) {
        TypedQuery<ProductoraEntity> tp = em.createQuery("SELECT u FROM ProductoraEntity u WHERE u.nombre = :name", ProductoraEntity.class);
        tp.setParameter("name", nombre);
        return tp.getResultList();
    }

    /**
     * Lista las productoras.
     *
     * @return
     */
    public List<ProductoraEntity> getAll() {
        LOGGER.log(Level.INFO, "Generando lista de productoras");
        TypedQuery<ProductoraEntity> tp = em.createQuery("SELECT u FROM ProductoraEntity u ORDER BY ASC,", ProductoraEntity.class);
        return tp.getResultList();
    }

    /**
     * Actualiza una productora.
     *
     * @param productora productora a actualizar.
     * @return Productora actualizada.
     */
    public ProductoraEntity update(ProductoraEntity productora) {
        LOGGER.log(Level.INFO, "Actualizando la productora con id={0}", productora.getId());
        return em.merge(productora);
    }

    /**
     * Borra una prodcutora en la base de datos por medio de su id.
     *
     * @param id id de la productora a eliminar.
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando la productora con id=[0]", id);
        ProductoraEntity entity = find(id);
        em.remove(entity);
    }

    public List<ProductoraEntity> findAll() {
        TypedQuery query = em.createQuery("select u from ProductoraEntity u", ProductoraEntity.class);
        return query.getResultList();
    }

}
