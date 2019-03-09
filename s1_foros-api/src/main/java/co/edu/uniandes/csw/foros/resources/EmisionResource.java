/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;
import co.edu.uniandes.csw.foros.dtos.EmisionDetailDTO;
import co.edu.uniandes.csw.foros.ejb.EmisionLogic;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
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
@Path("emisiones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EmisionResource {
    
    @Inject
    private EmisionLogic logic;
    
    @GET
    public List<EmisionDetailDTO> getAll(){       
        List<EmisionDetailDTO> emisionesDTO = new ArrayList<>();
        List<EmisionEntity> emisiones = logic.getEmisiones();
        for(EmisionEntity emision : emisiones){
            emisionesDTO.add(new EmisionDetailDTO(emision));
        }
        return emisionesDTO;
    }
    
    @GET
    @Path("/{id: \\d+}")
    public EmisionDetailDTO getById(@PathParam("id")Long id) throws BusinessLogicException {
        if(logic.getEmisionPorId(id)==null){
            throw new WebApplicationException("La emisión con el id " + id + " no existe.", 404);
        }
        EmisionDetailDTO emision = new EmisionDetailDTO(logic.getEmisionPorId(id));
        return emision;
    }
    
    @POST
    public EmisionDetailDTO createEmision(EmisionDetailDTO emision) throws BusinessLogicException {
        EmisionDetailDTO emisionNueva = new EmisionDetailDTO(logic.createEmision(emision.toEntity()));
        return emisionNueva;
    }
    
    @PUT
    @Path("/{id: \\d+}")
    public EmisionDetailDTO updateEmision(@PathParam("id") Long id, EmisionDetailDTO emision) throws BusinessLogicException{
        if(logic.getEmisionPorId(id)==null){
            throw new WebApplicationException("La emisión con el id " + id + " no existe.", 404);
        }
        EmisionDetailDTO emisionActualizada = new EmisionDetailDTO(logic.actualizarEmision(id, emision.toEntity()));
        return emisionActualizada;
    }
    
    @DELETE
    @Path("/{id: \\d+}")
    public void deleteEmision(@PathParam("id")Long id) throws BusinessLogicException{
        if(logic.getEmisionPorId(id)==null){
            throw new WebApplicationException("La emisión con el id " + id + " no existe.", 404);
        }
        logic.borrarEmision(id);
    }
    
    @Path("{emisionId: \\d+}/producciones")
    public Class<EmisionProduccionesResource> getEmisionProduccionesResource(@PathParam("emisionId")Long emisionId) throws BusinessLogicException{
        if(logic.getEmisionPorId(emisionId)==null){
            throw new WebApplicationException("La emisión con el id " + emisionId + " no existe.", 404);
        }
        return EmisionProduccionesResource.class;
    }
    
    @Path("{emisionId: \\d+}/dias")
    public Class<EmisionDiasResource> getEmisionDiasResource(@PathParam("emisionId")Long emisionId) throws BusinessLogicException{
        if(logic.getEmisionPorId(emisionId)==null){
            throw new WebApplicationException("La emisión con el id " + emisionId + " no existe.", 404);
        }
        return EmisionDiasResource.class;
    }
}
