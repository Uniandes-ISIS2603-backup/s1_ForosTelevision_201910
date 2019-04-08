/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.*;
import co.edu.uniandes.csw.foros.ejb.CapituloLogic;
import co.edu.uniandes.csw.foros.ejb.ProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.StaffLogic;
import co.edu.uniandes.csw.foros.entities.CapituloEntity;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.StaffEntity;
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
    
    @Inject
    private StaffLogic staffLogic;

    @Inject
    private CapituloLogic capituloLogic;

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
        ProduccionEntity produccionEntity = darEntidadProduccion(id);
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
        List<ProduccionDetailDTO> listaProduccionDetailDTO = listEntity2DetailDTOProducciones(produccionLogic.darTodasProducciones());
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
     * @param produccionDTO nueva información de la producción.
     * @return entidad de la producción editada.
     * @throws co.edu.uniandes.csw.foros.exceptions.BusinessLogicException
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
     * Método que registra un staff a una producción
     * 
     * @param idProduccion id de la producción a la cual agregarle el staff.
     * @param idStaff id del staff a agregar.
     * @return DTO con la información de la producción actualizada.
     * @throws BusinessLogicException 
     */
    @POST
    @Path("{idProduccion: \\d+}/staff/{idStaff: \\d+}")
    public ProduccionDTO registrarStaff(@PathParam("idProduccion") Long idProduccion, @PathParam("idStaff") Long idStaff) throws BusinessLogicException {
        ProduccionEntity produccionEntity = darEntidadProduccion(idProduccion);
        List<StaffEntity> staffs = produccionEntity.getStaff();
        StaffEntity nuevaRelacionStaff = staffLogic.darStaff(idStaff);
        if(nuevaRelacionStaff == null) {
            throw new WebApplicationException("El recurso /staff/" + idStaff + " no existe.", 404);
        }
        staffs.add(nuevaRelacionStaff);
        produccionEntity.setStaff(staffs);
        produccionEntity = produccionLogic.editarProduccion(idProduccion, produccionEntity);
        return new ProduccionDetailDTO(produccionEntity);
    }
    
    /**
     * Método que retorna los staffs de una producción.
     * 
     * @param id id de la producción.
     * @return lista con los staffs de la producción.
     */
    @GET
    @Path("{idProduccion: \\d+}/staff/")
    public List<StaffDetailDTO> darStaffs(@PathParam("idProduccion") Long id) {
        ProduccionEntity produccionEntity = darEntidadProduccion(id);
        List<StaffDetailDTO> staffs = listEntity2DetailDTOStaffs(produccionEntity.getStaff());
        return staffs;
    }

    /**
     * Método que registra un capítulo a una producción
     *
     * @param idProduccion id de la producción a la cual agregarle el staff.
     * @param idCapitulo id del capítulo a agregar.
     * @return DTO con la información de la producción actualizada.
     * @throws BusinessLogicException
     */
    @POST
    @Path("{idProduccion: \\d+}/capitulos/{idCapitulo: \\d+}")
    public ProduccionDTO registrarCapitulo(@PathParam("idProduccion") Long idProduccion, @PathParam("idCapitulo") Long idCapitulo) throws BusinessLogicException {
        ProduccionEntity produccionEntity = darEntidadProduccion(idProduccion);
        List<CapituloEntity> capitulos = produccionEntity.getCapitulos();
        CapituloEntity nuevaRelacionCapitulo = capituloLogic.getCapituloPorId(idCapitulo);
        if(nuevaRelacionCapitulo == null) {
            throw new WebApplicationException("El recurso /capitulo/" + idCapitulo + " no existe.", 404);
        }
        capitulos.add(nuevaRelacionCapitulo);
        produccionEntity.setCapitulos(capitulos);
        produccionEntity = produccionLogic.editarProduccion(idProduccion, produccionEntity);
        return new ProduccionDetailDTO(produccionEntity);
    }

    /**
     * Método que retorna los staffs de una producción.
     *
     * @param id id de la producción.
     * @return lista con los staffs de la producción.
     */
    @GET
    @Path("{idProduccion: \\d+}/capitulos/")
    public List<CapituloDTO> darCapitulos(@PathParam("idProduccion") Long id) {
        ProduccionEntity produccionEntity = darEntidadProduccion(id);
        List<CapituloDTO> capitulos = listEntity2DetailDTOCapitulos(produccionEntity.getCapitulos());
        return capitulos;
    }
    
    /**
     * Método que retorna la entidad de una producción según su id.
     * 
     * @param id id de la producción a retornar.
     * @return entidad de la producción.
     */
    private ProduccionEntity darEntidadProduccion(Long id) {
        try {
            ProduccionEntity produccionEntity = produccionLogic.darProduccion(id);
            if (produccionEntity == null) {
                throw new WebApplicationException("El recurso /producciones/" + id + " no existe.", 404);
            }
            return produccionEntity;
        } catch(BusinessLogicException ble) {
            throw new WebApplicationException(ble.getMessage(), 412);
        }
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
    private List<ProduccionDetailDTO> listEntity2DetailDTOProducciones(List<ProduccionEntity> entityList) {
        List<ProduccionDetailDTO> list = new ArrayList<>();
        for (ProduccionEntity entity : entityList) {
            list.add(new ProduccionDetailDTO(entity));
        }
        return list;
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
    private List<StaffDetailDTO> listEntity2DetailDTOStaffs(List<StaffEntity> entityList) {
        List<StaffDetailDTO> list = new ArrayList<>();
        for (StaffEntity entity : entityList) {
            list.add(new StaffDetailDTO(entity));
        }
        return list;
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
    private List<CapituloDTO> listEntity2DetailDTOCapitulos(List<CapituloEntity> entityList) {
        List<CapituloDTO> list = new ArrayList<>();
        for (CapituloEntity entity : entityList) {
            list.add(new CapituloDTO(entity));
        }
        return list;
    }

}
