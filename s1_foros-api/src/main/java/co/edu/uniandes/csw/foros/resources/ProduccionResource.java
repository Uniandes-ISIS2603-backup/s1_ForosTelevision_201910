/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.*;
import co.edu.uniandes.csw.foros.ejb.ProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.StaffLogic;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * Recurso de una producción.
 *
 * @author jf.castaneda
 */
@Path("producciones")
@Produces("application/json")
@Consumes("application/json")
public class ProduccionResource {

    private static final Logger LOGGER = Logger.getLogger(ProduccionResource.class.getName());

    @Inject
    private ProduccionLogic produccionLogic;

    /**
     * Método que retorna una producción.
     *
     * @param id id de la producción a retornar.
     * @return el DTO con la información de la producción.
     */
    @GET
    @Path("{id: \\d+}")
    public ProduccionDTO darProduccion(@PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "ProduccionResource darProduccion: input: {0}", id);
        ProduccionEntity produccionEntity = null;
        try {
            produccionEntity = produccionLogic.darProduccion(id);
            if (produccionEntity == null) {
                throw new WebApplicationException("El recurso /producciones/" + id + " no existe.", 404);
            }
        } catch(BusinessLogicException ble) {
            throw new WebApplicationException(ble.getMessage(), 412);
        }
        ProduccionDetailDTO staffDetailDTO = new ProduccionDetailDTO(produccionEntity);
        LOGGER.log(Level.INFO, "ProduccionResource darProduccion: output: {0}", staffDetailDTO.toString());
        return staffDetailDTO;
    }

    /**
     * Método que retorna las producciones.
     *
     * @return lista con los DTO de las producciones.
     */
    @GET
    public List<ProduccionDetailDTO> darTodasProducciones() {
        LOGGER.info("ProduccionResource darTodosProducciones: input: void");
        List<ProduccionDetailDTO> listaProduccionDetailDTO = listEntity2DetailDTO(produccionLogic.darTodasProducciones());
        LOGGER.log(Level.INFO, "BookResource getBooks: output: {0}", listaProduccionDetailDTO.toString());
        return listaProduccionDetailDTO;
    }

    /**
     * Método que crea una producción.
     *
     * @param produccionDTO DTO de la producción a crear.
     * @return DTO de la producción creada.
     */
    @POST
    public ProduccionDTO crearProduccion(ProduccionDTO produccionDTO) {
        LOGGER.log(Level.INFO, "ProduccionResource crearProduccion: input: {0}", produccionDTO.toString());
        ProduccionDTO nuevaProduccionDTO;
        try {
            nuevaProduccionDTO = new ProduccionDTO(produccionLogic.crearProduccion(produccionDTO.toEntity()));
        } catch (BusinessLogicException ble) {
            throw new WebApplicationException(ble.getMessage(), 412);
        }
        LOGGER.log(Level.INFO, "ProduccionResource crearProduccion: output: {0}", nuevaProduccionDTO.toString());
        return nuevaProduccionDTO;
    }

    /**
     * Método que actualiza la información de una producción.
     *
     * @param id id de la producción a editar.
     * @return entidad de la producción editada.
     */
    @PUT
    @Path("{id: \\d+}")
    public ProduccionDTO editarProduccion(@PathParam("id") Long id, ProduccionDTO produccionDTO) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProduccionResource editarProduccion: input: id: {0} , produccion: {1}", new Object[]{id, produccionDTO.toString()});
        produccionDTO.setId(id);
        if(produccionLogic.darProduccion(id) == null) {
            throw new WebApplicationException("El recurso /producciones/" + id + " no existe.", 404);
        }
        ProduccionDetailDTO produccionDetailDTO;
        try {
            produccionDetailDTO = new ProduccionDetailDTO(produccionLogic.editarProduccion(id, produccionDTO.toEntity()));
        } catch (BusinessLogicException ble) {
            throw new WebApplicationException(ble.getMessage(), 412);
        }
        LOGGER.log(Level.INFO, "ProduccionResource editarProduccion: output: {0}", produccionDetailDTO.toString());
        return produccionDetailDTO;
    }

    /**
     * Método que elimina una producción.
     *
     * @param id id de la producción a eliminar.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void eliminarProduccion(@PathParam("id") Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProduccionResource eliminarProduccion: input: {0}", id);
        ProduccionEntity entity = produccionLogic.darProduccion(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /producciones/" + id + " no existe.", 404);
        }
        produccionLogic.eliminarProduccion(id);
        LOGGER.info("ProduccionResource eliminarProduccion: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos BookEntity a una lista de
     * objetos BookDetailDTO (json)
     *
     * @param entityList corresponde a la lista de libros de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de libros en forma DTO (json)
     */
    private List<ProduccionDetailDTO> listEntity2DetailDTO(List<ProduccionEntity> entityList) {
        List<ProduccionDetailDTO> list = new ArrayList<>();
        for (ProduccionEntity entity : entityList) {
            list.add(new ProduccionDetailDTO(entity));
        }
        return list;
    }
}
