package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ProduccionDetailDTO;
import co.edu.uniandes.csw.foros.ejb.ProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.StaffProduccionesLogic;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa el recurso "staff/{id}/producciones".
 *
 * @author jf.castaneda
 */
@Path("staff")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StaffProduccionesResource {

    private static final Logger LOGGER = Logger.getLogger(StaffProduccionesResource.class.getName());

    @Inject
    private StaffProduccionesLogic staffProduccionesLogic;

    @Inject
    private ProduccionLogic produccionLogic;

    @POST
    @Path("{idStaff: \\d+}/{idProduccion: \\d+}")
    public ProduccionDetailDTO addProduccion(@PathParam("idStaff") Long idStaff, @PathParam("idProduccion") Long idProduccion) {
        LOGGER.log(Level.INFO, "StaffProduccionesResource addProduccion: input: booksId {0} , authorsId {1}", new Object[]{idStaff, idProduccion});
        //if (produccionLogic.darProduccion(idProduccion) == null) {
            //throw new WebApplicationException("El recurso /producciones/" + idProduccion + " no existe.", 404);
        //}
        //ProduccionDetailDTO detailDTO = new ProduccionDetailDTO(staffProduccionesLogic.addProduccion(idStaff, idProduccion));
        //LOGGER.log(Level.INFO, "StaffProduccionesResource addProduccion: output: {0}", detailDTO.toString());
        //return detailDTO;
        return null;
    }

}
