/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;
import co.edu.uniandes.csw.foros.dtos.EstadoDTO;
import co.edu.uniandes.csw.foros.dtos.EstadoDetailDTO;
import co.edu.uniandes.csw.foros.ejb.EstadoLogic;
import co.edu.uniandes.csw.foros.entities.EstadoEntity;
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
@Path("estados")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EstadoResource {
    
    @Inject
    private EstadoLogic logic; 
    
    @GET
    public List<EstadoDetailDTO> getAll(){
        List<EstadoDetailDTO> dtos = new ArrayList<>();
        List<EstadoEntity> entities = logic.getEstados();
        for(EstadoEntity estado : entities){
            dtos.add(new EstadoDetailDTO(estado));
        }
        return dtos;
    }
    
    @GET
    @Path("/{id: \\d+}")
    public EstadoDetailDTO getById(@PathParam("id")Long id){
        if(logic.getEstadoPorId(id)==null){
            throw new WebApplicationException("El estado con el id " + id + " no existe.", 404);
        }
        return new EstadoDetailDTO(logic.getEstadoPorId(id));
    }
    
    @POST
    public EstadoDetailDTO createEstado(EstadoDTO estado) throws BusinessLogicException{
        EstadoDetailDTO dto = new EstadoDetailDTO(logic.createEstado(estado.toEntity())); 
        return dto;
    }
    
    @PUT
    @Path("/{id: \\d+}")
    public EstadoDetailDTO updateEstado(@PathParam("id") Long id, EstadoDTO estado){
        if(logic.getEstadoPorId(id)==null){
            throw new WebApplicationException("El estado con el id " + id + " no existe.", 404);
        }
        EstadoDetailDTO dto = new EstadoDetailDTO(logic.actualizarEstado(id, estado.toEntity()));
        return dto;
    }
    
    @DELETE
    @Path("/{id: \\d+}")
    public void deleteEstado(@PathParam("id")Long id){
        if(logic.getEstadoPorId(id)==null){
            throw new WebApplicationException("El estado con el id " + id + " no existe.", 404);
        }
        logic.borrarEstado(id);
    }
    
    @Path("{estadoId: \\d+}/producciones")
    public Class<EstadoProduccionResource> getEstadoProduccionesResource(@PathParam("estadoId")Long estadoId){
        if(logic.getEstadoPorId(estadoId)==null){
            throw new WebApplicationException("El estado con el id " + estadoId + " no existe.", 404);
        }
        return EstadoProduccionResource.class;
    }
}
