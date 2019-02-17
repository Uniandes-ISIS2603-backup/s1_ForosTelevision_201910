/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;
import co.edu.uniandes.csw.foros.dtos.EstadoDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.enterprise.context.RequestScoped;
/**
 *
 * @author ne.ortega
 */
@Path("estados")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EstadoResource {
    
    private final static Logger LOGGER = Logger.getLogger(EstadoResource.class.getName());

    @GET
    public List<EstadoDTO> getAll(){
        return new ArrayList<>();
    }
    
    @GET
    @Path("/{id: \\d+}")
    public EstadoDTO getById(@PathParam("id")Long id){
        return new EstadoDTO();
    }
    
    @POST
    public EstadoDTO createEstado(EstadoDTO estado){
        return estado;
    }
    
    @PUT
    @Path("/{id: \\d+}")
    public EstadoDTO updateEstado(@PathParam("id") Long id, EstadoDTO estado){
        return estado;
    }
    
    @DELETE
    @Path("/{id: \\d+}")
    public void deleteEstado(@PathParam("id")Long id){
        
    }
}
