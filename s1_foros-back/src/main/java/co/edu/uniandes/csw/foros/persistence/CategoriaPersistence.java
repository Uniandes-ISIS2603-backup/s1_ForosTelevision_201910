/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.persistence;

import co.edu.uniandes.csw.foros.entities.CategoriaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jhonattan Fonseca.
 */
@Stateless
public class CategoriaPersistence {

    @PersistenceContext(unitName = "forosPU")
    protected EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(CategoriaPersistence.class.getName());

    /**
     * Crea un registro persistente de una categoria.
     *
     * @param categoria entidad a persistir.
     * @return entidad persistida.
     */
    public CategoriaEntity create(CategoriaEntity categoria) {
        em.persist(categoria);
        return categoria;
    }

    /**
     * Busca una categoria por su Id.
     *
     * @param categoriaId identificador de la productora.
     * @return instancia de la categoria encontrada.
     */
    public CategoriaEntity find(Long categoriaId) {
        CategoriaEntity find = em.find(CategoriaEntity.class, categoriaId);
        LOGGER.log(Level.INFO, "Encontrar la categoria con id ={0}", categoriaId);
        return find;
    }

    /**
     * Lista las categorias.
     *
     * @return
     */
    public List<CategoriaEntity> getAll() {
        LOGGER.log(Level.INFO, "Generando lista de categorias");
        TypedQuery<CategoriaEntity> tp = em.createQuery("SELECT u FROM CategoriaEntity u ORDER BY ASC,", CategoriaEntity.class);
        return tp.getResultList();
    }

    /**
     * Actualiza una categoria.
     *
     * @param categoria categoria a actualizar.
     * @return Productora actualizada.
     */
    public CategoriaEntity update(CategoriaEntity categoria) {
        LOGGER.log(Level.INFO, "Actualizando la categoria con id={0}", categoria.getId());
        return em.merge(categoria);
    }

    /**
     * Busca la categoria por nombre
     *
     * @param nombre registrado
     * @return productoras con el mismo nombre
     */
    public List<CategoriaEntity> findByName(String nombre) {
        TypedQuery<CategoriaEntity> tp = em.createQuery("SELECT u FROM CategoriaEntity u WHERE u.nombre = :name", CategoriaEntity.class);
        tp.setParameter("name", nombre);
        return tp.getResultList();
    }

    /**
     * Borra una categoria en la base de datos por medio de su id.
     *
     * @param id id de la categoria a eliminar.
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando la categoria con id=[0]", id);
        CategoriaEntity entity = find(id);
        em.remove(entity);
    }

    public List<CategoriaEntity> findAll() {
        TypedQuery query = em.createQuery("select u from CategoriaEntity u", CategoriaEntity.class);
        return query.getResultList();
    }

}
