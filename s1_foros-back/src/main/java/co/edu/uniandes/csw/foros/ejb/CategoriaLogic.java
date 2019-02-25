/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.CategoriaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CategoriaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Jhonattan Fonseca.
 */
@Stateless
public class CategoriaLogic {

    @Inject
    CategoriaPersistence persitence;

    /**
     * Crea una categoría y verifica los requisitos necesarios para su
     * creaci{on.
     *
     * @param entity Categoria a crear.
     * @return entidad creada.
     * @throws BusinessLogicException si se intenta crear una categoria ya
     * registrada.
     */
    public CategoriaEntity crearProductora(CategoriaEntity entity) throws BusinessLogicException {
        if (persitence.findByName(entity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe una productora con el nombre = " + entity.getNombre());
        }
        persitence.create(entity);
        return entity;
    }

    /**
     * Borrar una Categoria.
     *
     * @param categoriaId: id de la catgoria a borrar.
     */
    public void borrarProductora(Long categoriaId) throws BusinessLogicException {

        if (persitence.find(categoriaId) == null) {
            throw new BusinessLogicException("La productora con id: " + categoriaId + " no fue encontrada");
        }
        persitence.delete(categoriaId);

    }
}
