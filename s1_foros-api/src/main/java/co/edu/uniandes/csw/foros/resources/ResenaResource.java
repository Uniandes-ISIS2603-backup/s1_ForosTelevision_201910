package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ResenaDTO;
import co.edu.uniandes.csw.foros.dtos.UtilRespuesta;
import co.edu.uniandes.csw.foros.ejb.ResenaLogic;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author mi.carrascal
 */
@Path("resenas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ResenaResource {
    
    private static final Logger LOGGER= Logger.getLogger(ResenaResource.class.getName());
    @Inject
    private ResenaLogic resenaLogic;
    
      
    @POST
    @Path("/{usuario_id: \\d+}/{produccion_id: \\d+}")
    public ResenaDTO crearResena(@PathParam("usuario_id")Long user_id,@PathParam("produccion_id")Long produccion_id,ResenaDTO resenaDTO) throws BusinessLogicException
    {   
        ResenaDTO aRetornar=null;
        ResenaEntity resenaEntity=resenaDTO.toEntity();
        resenaEntity =resenaLogic.createResena(user_id,produccion_id,resenaEntity);
        aRetornar=new ResenaDTO(resenaEntity);
        return aRetornar;
    }
        
    @PUT
     @Path("{id: \\d+}")

    public ResenaDTO updateResena(ResenaDTO resenaDTO,@PathParam("id") Long resenaId) throws BusinessLogicException
    {
        //Busca el id del canal a actualizar
        ResenaEntity entity=resenaLogic.find(resenaId);
        //Convierte el DTO a Entity
        ResenaEntity nuevaResena=resenaDTO.toEntity();
        resenaLogic.update(entity);
         return resenaDTO;
    }
    
    @GET
    @Path("{id: \\d+}")
    public ResenaEntity getResena(@PathParam("id") Long idCanal) throws BusinessLogicException
    {
        ResenaEntity resena=resenaLogic.find(idCanal);
        return resena;    
    }
    
    
        
         /**
     * Lista de todos los canales
     *
     * @return Lista de todos los canales
     * @throws BusinessLogicException
     */
    @GET
    @Path("all")
    public List<ResenaDTO> darResenas() throws BusinessLogicException {
        List<ResenaEntity> user = resenaLogic.getAll();
        List<ResenaDTO> out = new ArrayList<>();
        for (ResenaEntity u : user) {
            ResenaDTO resenaDTO=new ResenaDTO(u);
            out.add(resenaDTO);
        }
        return out;
    }
   
     /**
     * Elimina un canal
     *
     * @param id de un canal
     * @return respuesta
     */
    @DELETE
    @Path("{id: \\d+}")
    public UtilRespuesta<String>  eliminarCanal(@PathParam("id") Long resenaId) throws BusinessLogicException
    {
      resenaLogic.delete(resenaId);
       return new UtilRespuesta(200,"se eliminó la resena");
    }
    
    
    
    
}
