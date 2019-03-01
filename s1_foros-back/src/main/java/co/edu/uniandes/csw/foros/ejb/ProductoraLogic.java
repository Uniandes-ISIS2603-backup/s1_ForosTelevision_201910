/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ProductoraPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Jhonattan Fonseca.
 */
@Stateless
public class ProductoraLogic {

    @Inject
    ProductoraPersistence productoraPersitence;

    /**
     * Crea una productora y verifica los requisitos necesarios para su
     * creaci{on.
     *
     * @param entity Productora a crear.
     * @return entidad creada.
     * @throws BusinessLogicException si se intenta crear una productora ya
     * registrada.
     */
    public ProductoraEntity crearProductora(ProductoraEntity entity) throws BusinessLogicException {
        if (productoraPersitence.find(entity.getId()) != null) {
            throw new BusinessLogicException("Ya existe una productora con el nombre = " + entity.getNombre());
        }
        productoraPersitence.create(entity);
        return entity;
    }

    /**
     * Borrar una Productora.
     *
     * @param productoraId: id de la productora a borrar.
     */
    public void borrarProductora(Long productoraId) throws BusinessLogicException {

        if (productoraPersitence.find(productoraId) == null) {
            throw new BusinessLogicException("La productora con id: " + productoraId + " no fue encontrada");
        }
        productoraPersitence.delete(productoraId);

    }

}
