/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
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
        LOGGER.log(Level.INFO, "Crear");

        if (productoraPersistence.findByName(entity.getNombre()).size() != 0) {

            throw new BusinessLogicException("Ya existe una productora con el nombre = " + entity.getNombre());
        }
        int tamaño = entity.getNombre().length();
        if(tamaño < 5)
        {
            throw new BusinessLogicException("La productora debe tener un nombre válido.");
        }
        productoraPersistence.create(entity);
        return entity;
    }

    public List<ProductoraEntity> getProductoras() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las productoras");
        List<ProductoraEntity> lista = productoraPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos las productoras");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Productora a partir de su ID.
     *
     * @param productorasId Identificador de la instancia a consultar
     * @return Instancia de ProductoraEntity con los datos del Productora
     * consultado.
     */
    public ProductoraEntity getProductora(Long productorasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la productora con id = {0}", productorasId);
        ProductoraEntity productoraEntity = productoraPersistence.find(productorasId);
        if (productoraEntity == null) {
            LOGGER.log(Level.SEVERE, "La prodcutora con el id = {0} no existe", productorasId);
            throw new BusinessLogicException("Productora inexistente");
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

        LOGGER.log(Level.INFO, "Inicia proceso de eliminación de una prodcutora");
        ProductoraEntity productoraEntity = productoraPersistence.find(productoraId);
        if (productoraEntity == null) {
            throw new BusinessLogicException("No existe una productora con el id :" + productoraId);
        }
        productoraPersistence.delete(productoraId);
        LOGGER.log(Level.INFO, "Termina proceso de eliminación del miembro del staff.");
       

    }

    /**
     * Método que edita la información de una productora.
     *
     * @param idProductora el id de la productora a editar.
     * @param productoraEntity la nueva información de la producción.
     * @return la entidad de la producción editada.
     * @throws BusinessLogicException cuando alguno de los atributos no cumple
     * con las reglas de negocio.
     */
    public ProductoraEntity editarProductora(Long idProductora, ProductoraEntity productoraEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la productora con id = {0}", idProductora);
        if (idProductora == null) {
            throw new BusinessLogicException("El id de la producción debe existir");
        }
        comprobarReglasDeNegocio(productoraEntity);
        ProductoraEntity newEntity = productoraPersistence.update(productoraEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la productora con id = {0}", productoraEntity.getId());
        return newEntity;
    }

    public ProductoraEntity find(Long idProductora) throws BusinessLogicException {
       LOGGER.log(Level.INFO, "Inicia proceso de consultar la productora con id = {0}", idProductora);
       if(idProductora == null)
       {
           throw new BusinessLogicException("Identificador no valido");
       }
       ProductoraEntity prod = productoraPersistence.find(idProductora);
        if (prod == null) {
            throw new BusinessLogicException("Productora no registrada");
        }
       LOGGER.log(Level.INFO, "Termina el proceso de consultar la productora con id = {0}", idProductora);
        return prod;
    }

    private void comprobarReglasDeNegocio(ProductoraEntity productoraEntity) throws BusinessLogicException {

        if (productoraEntity.getNombre() == null) {
            throw new BusinessLogicException("El nombre de la productora es null");
        }

    }

}
