package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.MultimediaDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.util.logging.Logger;

@Path("multimedia")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MultimediaResource {

    private static final Logger LOGGER= Logger.getLogger(MultimediaResource.class.getName());

    @GET
    @Path("/produccion/{id: \\d+}")
    public MultimediaDTO darRecursosMultimedia(@PathParam("id")Long id){
        return new MultimediaDTO();
    }

    @POST
    public String crearMultimedia(MultimediaDTO multimediaDTO){
        return "multimedia creado";
    }

    @PUT
    public String editarMultimedia(MultimediaDTO multimediaDTO){
        return "multimedia creado";
    }
}
