package co.edu.uniandes.csw.foros.resources;
import co.edu.uniandes.csw.foros.dtos.DiaDTO;
import co.edu.uniandes.csw.foros.ejb.DiaLogic;
import co.edu.uniandes.csw.foros.entities.DiaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
/**
 *
 * @author ne.ortega
 */
@Path("dias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class DiaResource {
    
    private final static Logger LOGGER = Logger.getLogger(DiaResource.class.getName());
    
    @Inject
    private DiaLogic logic;
    
    @GET
    public List<DiaDTO> getAll(){
        List<DiaDTO> dtos = new ArrayList<>();
        List<DiaEntity> entities = logic.getDias();
        for(DiaEntity entity : entities){
            dtos.add(new DiaDTO(entity));
        }
        return dtos;
    }
    
    @GET
    @Path("/{id: \\d+}")
    public DiaDTO getById(@PathParam("id")Long id){
        if(logic.getDiaPorId(id)==null){
            throw new WebApplicationException("El dia con el id "+id+" no existe.", 404);
        }
        return new DiaDTO(logic.getDiaPorId(id));
    }
    
    @POST
    public DiaDTO createDia(DiaDTO dia) throws BusinessLogicException{    
        DiaDTO dto = new DiaDTO(logic.createDia(dia.toEntity()));
        return dto;
    }
    
    @PUT
    @Path("/{id: \\d+}")
    public DiaDTO updateDia(@PathParam("id") Long id, DiaDTO dia){
        if(logic.getDiaPorId(id)==null){
            throw new WebApplicationException("El dia con el id "+id+" no existe.", 404);
        }
        return new DiaDTO(logic.actualizarDia(id, dia.toEntity()));
    }
    
    @DELETE
    @Path("/{id: \\d+}")
    public void deleteDia(@PathParam("id")Long id){
        if(logic.getDiaPorId(id)==null){
            throw new WebApplicationException("El dia con el id "+id+" no existe.", 404);
        }
        logic.borrarDia(id);
    }
}
