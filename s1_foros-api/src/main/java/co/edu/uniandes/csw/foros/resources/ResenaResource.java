package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ResenaDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author estudiante
 */
@Path("resenas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ResenaResource {
    
    private static final Logger LOGGER= Logger.getLogger(ResenaResource.class.getName());
    
    @POST
    public ResenaDTO crearResena(ResenaDTO resenaDTO)
    {
        return resenaDTO;
    }
    
    
    
    
}
