/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;
import co.edu.uniandes.csw.foros.dtos.EmisionDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.enterprise.context.RequestScoped;
/**
 *
 * @author ne.ortega
 */
@Path("emisiones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EmisionResource {
    
    private final static Logger LOGGER = Logger.getLogger(EmisionResource.class.getName());
    
    @GET
    public List<EmisionDTO> getAll(){
        return new ArrayList<>();
    }
    
    @GET
    @Path("/{id: \\d+}")
    public EmisionDTO getById(@PathParam("id")Long id){
        return new EmisionDTO();
    }
    
    @POST
    public EmisionDTO createEmision(EmisionDTO emision){
        return emision;
    }
    
    @PUT
    @Path("/{id: \\d+}")
    public EmisionDTO updateEmision(@PathParam("id") Long id, EmisionDTO emision){
        return emision;
    }
    
    @DELETE
    @Path("/{id: \\d+}")
    public void deleteEmision(@PathParam("id")Long id){
        
    }
}
