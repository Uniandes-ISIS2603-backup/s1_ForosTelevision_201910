/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.CapituloDTO;
import co.edu.uniandes.csw.foros.dtos.CategoriaDTO;
import co.edu.uniandes.csw.foros.dtos.EmisionDTO;
import co.edu.uniandes.csw.foros.dtos.MultimediaDTO;
import co.edu.uniandes.csw.foros.dtos.ProduccionDTO;
import co.edu.uniandes.csw.foros.dtos.ProductoraDTO;
import co.edu.uniandes.csw.foros.dtos.ResenaDTO;
import co.edu.uniandes.csw.foros.dtos.StaffDTO;
import co.edu.uniandes.csw.foros.ejb.ProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.StaffLogic;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            produccionLogic.darProduccion(id);
        } catch(BusinessLogicException ble) {
            throw new WebApplicationException(ble.getMessage(), 412);
        }
        if (staffEntity == null) {
            throw new WebApplicationException("El recurso /books/" + id + " no existe.", 404);
        }
        StaffDetailDTO staffDetailDTO = new StaffDetailDTO(staffEntity);
        LOGGER.log(Level.INFO, "StaffResource getStaff: output: {0}", staffDetailDTO.toString());
        return staffDetailDTO;
    }

    /**
     * Método que crea una producción.
     *
     * @param jsonString string en formato json con la información de la
     * producción
     * @return mensaje de éxito.
     */
    @POST
    public String crearProduccion(String jsonString) {
        return "producción creada";
    }

    /**
     * Método que actualiza la información de una producción.
     *
     * @param jsonString string en formato json con la nueva información de la
     * producción.
     * @return mensaje de éxito.
     */
    @PUT
    public String editarProduccion(String jsonString) {
        return "producción editada";
    }

    /**
     * Método que elimina una producción.
     *
     * @param id id de la producción a eliminar.
     * @return mensaje de éxito.
     */
    @DELETE
    @Path("/producciones/eliminar/{id: \\d+}")
    public String eliminarProduccion(@PathParam("id") Long id) {
        return "producción eliminada";
    }

    /**
     * Método que retorna el archivo multimedia asociado a la producción
     *
     * @param id id de la producción a retornar su multimedia.
     * @return el DTO con la multimedia de la producción.
     */
    @GET
    @Path("/producciones/{id: \\d+}/multimedia")
    public MultimediaDTO darMultimediaProduccion(@PathParam("id") Long id) {
        return new MultimediaDTO();
    }

    /**
     * Método que retorna los miembros del staff de una producción.
     *
     * @param id id de la producción a retornar sus miembros del staff.
     * @return una lista con los DTO de los miembros del staff de la producción.
     */
    @GET
    @Path("/producciones/{id: \\d+}/staff")
    public List<StaffDTO> darStaffProduccion(@PathParam("id") Long id) {
        return new ArrayList<>();
    }

    /**
     * Método que retorna las categorías de una producción.
     *
     * @param id id de la producción a retornar sus categorías.
     * @return una lista con los DTO de las categorías de la producción.
     */
    @GET
    @Path("/producciones/{id: \\d+}/categorias")
    public List<CategoriaDTO> darCategoriasProduccion(@PathParam("id") Long id) {
        return new ArrayList<>();
    }

    /**
     * Método que retorna los capítulos de una producción.
     *
     * @param id id de la producción a retornar sus capítulos.
     * @return una lista con los DTO de los capítulos de la producción.
     */
    @GET
    @Path("/producciones/{id: \\d+}/capitulos")
    public List<CapituloDTO> darCapitulosProduccion(@PathParam("id") Long id) {
        return new ArrayList<>();
    }

    /**
     * Método que retorna las emisiones de una producción.
     *
     * @param id id de la producción a retornar sus emisiones.
     * @return una lista con los DTO de las emisiones de la producción.
     */
    @GET
    @Path("/producciones/{id: \\d+}/emisiones")
    public List<EmisionDTO> darEmisionesProduccion(@PathParam("id") Long id) {
        return new ArrayList<>();
    }

    /**
     * Método que retorna la productora de una producción.
     *
     * @param id de la producción a retornar su productora.
     * @return el DTO con la productora de la producción.
     */
    @GET
    @Path("/producciones/{id: \\d+}/productora")
    public ProductoraDTO darProductoraProduccion(@PathParam("id") Long id) {
        return new ProductoraDTO();
    }

    /**
     * Método que retorna las reseñas de una producción.
     *
     * @param id de la producción a retornar sus reseñas.
     * @return una lista con los DTO de las reseñas de la producción.
     */
    @GET
    @Path("/producciones/{id: \\d+}/resenas")
    public List<ResenaDTO> darResenasProduccion(@PathParam("id") Long id) {
        return new ArrayList<>();
    }

}
