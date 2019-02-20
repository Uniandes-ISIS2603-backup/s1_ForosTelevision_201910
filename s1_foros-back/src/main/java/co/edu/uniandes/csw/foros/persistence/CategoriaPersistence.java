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
     * Borra una categoria en la base de datos por medio de su id.
     *
     * @param id id de la categoria a eliminar.
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando la categoria con id=[0]", id);
        CategoriaEntity entity = find(id);
        em.remove(entity);
    }
}
