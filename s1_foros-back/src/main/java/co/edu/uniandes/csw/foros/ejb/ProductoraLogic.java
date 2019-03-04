/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ProductoraPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Jhonattan Fonseca.
 */
@Stateless
public class ProductoraLogic {

    private static final Logger LOGGER = Logger.getLogger(ProductoraLogic.class.getName());

    @Inject
    private ProductoraPersistence productoraPersistence;


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
        if (productoraPersistence.findByName(entity.getNombre()).size()!=0) {
            throw new BusinessLogicException("Ya existe una productora con el nombre = " + entity.getNombre());
        }
       productoraPersistence.create(entity);
        return entity;
    }

   public List<ProductoraEntity> getProductoras() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        List<ProductoraEntity> lista = productoraPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Productora a partir de su ID.
     *
     * @param productorasId Identificador de la instancia a consultar
     * @return Instancia de ProductoraEntity con los datos del Productora consultado.
     */
    public ProductoraEntity getProductora(Long productorasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el autor con id = {0}", productorasId);
        ProductoraEntity productoraEntity = productoraPersistence.find(productorasId);
        if (productoraEntity == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", productorasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el autor con id = {0}", productorasId);
        return productoraEntity;
    }
    /**
     * Borrar una Productora.
     *
     * @param productoraId: id de la productora a borrar.
     */
    public void borrarProductora(Long productoraId) throws BusinessLogicException {

        if (productoraPersistence.find(productoraId) == null) {
            throw new BusinessLogicException("La productora con id: " + productoraId + " no fue encontrada");
        }
        productoraPersistence.delete(productoraId);

    }

}
