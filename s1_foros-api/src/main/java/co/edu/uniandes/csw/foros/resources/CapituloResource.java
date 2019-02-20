/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;
import co.edu.uniandes.csw.foros.dtos.CapituloDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.enterprise.context.RequestScoped;
/**
 *
 * @author ne.ortega
 */
@Path("capitulos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CapituloResource {
    
    @GET
    public List<CapituloDTO> getAll(){
        return new ArrayList<>();
    }
    
    @GET
    @Path("/{id: \\d+}")
    public CapituloDTO getById(@PathParam("id")Long id){
        return new CapituloDTO();
    }
    
    @POST
    public CapituloDTO createCapitulo(CapituloDTO capitulo){
        return capitulo;
    }
    
    @PUT
    @Path("/{id: \\d+}")
    public CapituloDTO updateCapitulo(@PathParam("id") Long id, CapituloDTO capitulo){
        return capitulo;
    }
    
    @DELETE
    @Path("/{id: \\d+}")
    public void deleteCapitulo(@PathParam("id")Long id){
        
    }
}
