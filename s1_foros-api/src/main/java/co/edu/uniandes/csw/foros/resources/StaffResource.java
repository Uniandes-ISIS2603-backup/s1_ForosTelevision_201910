package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ProduccionDTO;
import co.edu.uniandes.csw.foros.dtos.ProduccionDetailDTO;
import co.edu.uniandes.csw.foros.dtos.StaffDTO;
import co.edu.uniandes.csw.foros.dtos.StaffDetailDTO;
import co.edu.uniandes.csw.foros.ejb.ProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.StaffLogic;
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
 * Recurso de un miembro del staff.
 *
 * @author jf.castaneda
 */
@Path("staff")
@Produces("application/json")
@Consumes("application/json")
public class StaffResource {

    private static final Logger LOGGER = Logger.getLogger(StaffResource.class.getName());

    /**
     * Variable para acceder a la lógica de la aplicación.
     */
    @Inject
    private StaffLogic staffLogic;

    /**
     * Método que retorna un miembro del staff.
     *
     * @param id id del miembro del staff a retornar.
     * @return el DTO con la información del miembro del staff.
     * @throws BusinessLogicException cuando no se encuentra algún atributo o la entidad es nula.
     */
    @GET
    @Path("{id: \\d+}")
    public StaffDTO darStaff(@PathParam("id") Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "StaffResource darStaff: input: {0}", id);
        StaffEntity staffEntity = null;
        try {
            staffEntity = staffLogic.darStaff(id);
            if (staffEntity == null) {
                throw new WebApplicationException("El recurso /staff/" + id + " no existe.", 404);
            }
        } catch(BusinessLogicException ble) {
            throw new WebApplicationException(ble.getMessage(), 412);
        }
        StaffDetailDTO staffDetailDTO = new StaffDetailDTO(staffEntity);
        LOGGER.log(Level.INFO, "StaffResource darStaff: output: {0}", staffDetailDTO.toString());
        return staffDetailDTO;
    }

    /**
     * Método que crea un miembro de staff.
     *
     * @param staffDTO DTO del miembro del staff a crear
     * @return DTO del staff creado.
     */
    @POST
    public StaffDTO crearStaff(StaffDTO staffDTO) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "StaffResource crearStaff: input: {0}", staffDTO.toString());
        StaffDTO nuevoStaffDTO;
        try {
            nuevoStaffDTO = new StaffDTO(staffLogic.crearStaff(staffDTO.toEntity()));
        } catch (BusinessLogicException ble) {
            throw new WebApplicationException(ble.getMessage(), 412);
        }
        LOGGER.log(Level.INFO, "StaffResource crearStaff: output: {0}", nuevoStaffDTO.toString());
        return nuevoStaffDTO;
    }

    /**
     * Método que actualiza la información de un miembro del staff.
     *
     * @param id id del staff a editar.
     * @return entidad del staff editado.
     */
    @PUT
    @Path("{id: \\d+}")
    public StaffDTO editarStaff(@PathParam("id") Long id, StaffDTO staffDTO) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "StaffResource editarStaff: input: id: {0} , staff: {1}", new Object[]{id, staffDTO.toString()});
        staffDTO.setId(id);
        if(staffLogic.darStaff(id) == null) {
            throw new WebApplicationException("El recurso /staff/" + id + " no existe.", 404);
        }
        StaffDetailDTO staffDetailDTO;
        try {
            staffDetailDTO = new StaffDetailDTO(staffLogic.editarStaff(id, staffDTO.toEntity()));
        } catch (BusinessLogicException ble) {
            throw new WebApplicationException(ble.getMessage(), 412);
        }
        LOGGER.log(Level.INFO, "StaffResource editarStaff: output: {0}", staffDetailDTO.toString());
        return staffDetailDTO;
    }

    /**
     * Método que elimina un miembro del staff.
     *
     * @param id id del staff a eliminar.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void eliminarStaff(@PathParam("id") Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "StaffResource eliminarStaff: input: {0}", id);
        StaffEntity entity = staffLogic.darStaff(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /staff/" + id + " no existe.", 404);
        }
        //staffProduccionesResource.eliminarProducciones();
        staffLogic.eliminarStaff(id);
        LOGGER.info("StaffResource eliminarStaff: output: void");
    }

    /**
     * Método que retorna las producciones en las que ha participado el miembro
     * del staff.
     *
     * @return lista con los DTO de las producciones donde ha participado.
     */
    @GET
    public List<StaffDetailDTO> darTodosStaff() {
        LOGGER.info("StaffResource darTodosStaff: input: void");
        List<StaffDetailDTO> listaStaffDetailDTO = listEntity2DetailDTO(staffLogic.darTodosStaff());
        LOGGER.log(Level.INFO, "StaffResource darTodosStaff: output: {0}", listaStaffDetailDTO.toString());
        return listaStaffDetailDTO;
    }
    
    /**
     * Método que retorna los staffs de una producción.
     * 
     * @param id id de la producción.
     * @return lista con los staffs de la producción.
     */
    @GET
    @Path("{idStaff: \\d+}/producciones/")
    public List<ProduccionDetailDTO> darProducciones(@PathParam("idStaff") Long id) {
        StaffEntity staffEntity = darEntidadStaff(id);
        List<ProduccionDetailDTO> producciones = listEntity2DetailDTOProducciones(staffEntity.getProducciones());
        return producciones;
    }
    
    private StaffEntity darEntidadStaff(Long id) {
        try {
            StaffEntity staffEntity = staffLogic.darStaff(id);
            if (staffEntity == null) {
                throw new WebApplicationException("El recurso /staff/" + id + " no existe.", 404);
            }
            return staffEntity;
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
    private List<StaffDetailDTO> listEntity2DetailDTO(List<StaffEntity> entityList) {
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
    private List<ProduccionDetailDTO> listEntity2DetailDTOProducciones(List<ProduccionEntity> entityList) {
        List<ProduccionDetailDTO> list = new ArrayList<>();
        for (ProduccionEntity entity : entityList) {
            list.add(new ProduccionDetailDTO(entity));
        }
        return list;
    }
}
