/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.CategoriaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CategoriaPersistence;
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
public class CategoriaLogic {

    private static final Logger LOGGER = Logger.getLogger(CategoriaLogic.class.getName());

    @Inject
    private CategoriaPersistence categoriaPersistence;


    /**
     * Crea una categoria y verifica los requisitos necesarios para su
     * creaci{on.
     *
     * @param entity Categoria a crear.
     * @return entidad creada.
     * @throws BusinessLogicException si se intenta crear una categoria ya
     * registrada.
     */
    public CategoriaEntity crearCategoria(CategoriaEntity entity) throws BusinessLogicException {

        if (categoriaPersistence.findByName(entity.getNombre()).size()!=0) {

            throw new BusinessLogicException("Ya existe una categoria con el nombre = " + entity.getNombre());
        }
       categoriaPersistence.create(entity);
        return entity;
    }

   public List<CategoriaEntity> getCategorias() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        List<CategoriaEntity> lista = categoriaPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Categoria a partir de su ID.
     *
     * @param categoriasId Identificador de la instancia a consultar
     * @return Instancia de CategoriaEntity con los datos del Categoria consultado.
     */
    public CategoriaEntity getCategoria(Long categoriasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el autor con id = {0}", categoriasId);
        CategoriaEntity categoriaEntity = categoriaPersistence.find(categoriasId);
        if (categoriaEntity == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", categoriasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el autor con id = {0}", categoriasId);
        return categoriaEntity;
    }
    /**
     * Borrar una Categoria.
     *
     * @param categoriaId: id de la categoria a borrar.
     */
    public void borrarCategoria(Long categoriaId) throws BusinessLogicException {

        if (categoriaPersistence.find(categoriaId) == null) {
            throw new BusinessLogicException("La categoria con id: " + categoriaId + " no fue encontrada");
        }
        categoriaPersistence.delete(categoriaId);

    }

}