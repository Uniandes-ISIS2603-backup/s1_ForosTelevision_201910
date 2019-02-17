/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;
import co.edu.uniandes.csw.foros.dtos.DiaDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.enterprise.context.RequestScoped;
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
    
    @GET
    public List<DiaDTO> getAll(){
        return new ArrayList<>();
    }
    
    @GET
    @Path("/{id: \\d+}")
    public DiaDTO getById(@PathParam("id")Long id){
        return new DiaDTO();
    }
    
    @POST
    public DiaDTO createDia(DiaDTO dia){
        return dia;
    }
    
    @PUT
    @Path("/{id: \\d+}")
    public DiaDTO updateDia(@PathParam("id") Long id, DiaDTO dia){
        return dia;
    }
    
    @DELETE
    @Path("/{id: \\d+}")
    public void deleteDia(@PathParam("id")Long id){
        
    }
    
}
