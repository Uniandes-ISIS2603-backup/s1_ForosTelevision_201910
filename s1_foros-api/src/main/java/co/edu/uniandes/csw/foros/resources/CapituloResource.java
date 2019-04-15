package co.edu.uniandes.csw.foros.resources;
import co.edu.uniandes.csw.foros.dtos.CapituloDTO;
import co.edu.uniandes.csw.foros.ejb.CapituloLogic;
import co.edu.uniandes.csw.foros.entities.CapituloEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
/**
 *
 * @author ne.ortega
 */
@Path("capitulos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CapituloResource {
    
    /**
     * Clase de Lógica encargada del recurso capítulo.
     */
    @Inject
    private CapituloLogic logic;
    
    /**
     * 
     * @return 
     */
    @GET
    public List<CapituloDTO> getAll(){
        List<CapituloDTO> list = new ArrayList<>();
        List<CapituloEntity> entidades = logic.getCapitulos();
        if(!entidades.isEmpty()){
            for(CapituloEntity entity : entidades){
                CapituloDTO dto = new CapituloDTO(entity);
                list.add(dto);
            }
        }  
        return list;
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    @GET
    @Path("/{id: \\d+}")
    public CapituloDTO getById(@PathParam("id")Long id){
        CapituloEntity entity = logic.getCapituloPorId(id);
        if(entity == null){
            throw new WebApplicationException("El capítulo con el id: " + id + " no existe.", 404);
        }
        CapituloDTO dto = new CapituloDTO(entity);
        return dto;
    }
    
    /**
     * 
     * @param capitulo
     * @return
     * @throws BusinessLogicException 
     */
    @POST
    public CapituloDTO createCapitulo(CapituloDTO capitulo) throws BusinessLogicException{
        CapituloDTO dto = new CapituloDTO(logic.createCapitulo(capitulo.toEntity()));
        return dto;
    }
    
    /**
     * 
     * @param id
     * @param capitulo
     * @return 
     */
    @PUT
    @Path("/{id: \\d+}")
    public CapituloDTO updateCapitulo(@PathParam("id") Long id, CapituloDTO capitulo){
        CapituloEntity entity = logic.getCapituloPorId(id);
        if(entity == null){
            throw new WebApplicationException("El capítulo con el id: " + id + " no existe.", 404);
        }
        capitulo.setId(id);
        CapituloDTO dto = new CapituloDTO(logic.actualizarCapitulo(id, capitulo.toEntity()));
        return dto;
    }
    
    /**
     * 
     * @param id 
     */
    @DELETE
    @Path("/{id: \\d+}")
    public void deleteCapitulo(@PathParam("id")Long id){
        if(logic.getCapituloPorId(id)==null){
            throw new WebApplicationException("El capítulo con el id: " + id + " no existe.", 404);
        }
        logic.borrarCapitulo(id);    
    }
}
