/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ProduccionDTO;
import co.edu.uniandes.csw.foros.dtos.StaffDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author jf.castaneda
 */
public class StaffResource {

    private static final Logger LOGGER = Logger.getLogger(MultimediaResource.class.getName());

    /**
     * Método que retorna un miembro del staff.
     *
     * @param id id del miembro del staff a retornar.
     * @return el DTO con la información del miembro del staff.
     */
    @GET
    @Path("/staff/{id: \\d+}")
    public StaffDTO darRecursosStaff(@PathParam("id") Long id) {
        return new StaffDTO();
    }

    /**
     * Método que crea un miembro de staff.
     *
     * @param jsonString string en formato json con la información del miembro
     * del staff.
     * @return mensaje de éxito.
     */
    @POST
    public String crearStaff(String jsonString) {
        return "staff creado";
    }

    /**
     * Método que actualiza la información de un miembro del staff.
     *
     * @param jsonString string en formato json con la nueva información del
     * miembro del staff.
     * @return mensaje de éxito.
     */
    @PUT
    public String editarStaff(String jsonString) {
        return "staff editado";
    }

    /**
     * Método que elimina un miembro del staff.
     *
     * @param id id del staff a eliminar.
     * @return mensaje de éxito.
     */
    @DELETE
    @Path("/staff/eliminar/{id: \\d+}")
    public String eliminarStaff(@PathParam("id") Long id) {
        return "staff eliminado";
    }

    /**
     * Método que retorna las producciones en las que ha participado el miembro
     * del staff.
     *
     * @param id id del staff a retornar sus producciones.
     * @return lista con los DTO de las producciones donde ha participado.
     */
    @GET
    @Path("/staff/{id: \\d+}/producciones")
    public List<ProduccionDTO> darProduccionesStaff(@PathParam("id") Long id) {
        return new ArrayList<>();
    }
}
