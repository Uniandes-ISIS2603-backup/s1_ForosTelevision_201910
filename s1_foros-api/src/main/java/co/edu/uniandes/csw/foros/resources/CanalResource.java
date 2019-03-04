/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;
import co.edu.uniandes.csw.foros.dtos.CanalDTO;
import co.edu.uniandes.csw.foros.dtos.UsuarioDTO;
import co.edu.uniandes.csw.foros.ejb.CanalLogic;
import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
@Path("canales")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CanalResource {
    @Inject
    private CanalLogic logica;
     private static final Logger LOGGER= Logger.getLogger(UsuarioResource.class.getName());
    
    //Prueba postman
    @POST
    public CanalDTO crearCanal(CanalDTO canalDTO) throws BusinessLogicException
    {
    
        CanalEntity canalEntity=canalDTO.toEntity();
        canalEntity =logica.createCanal(canalEntity);
        return new CanalDTO (canalEntity);
    }
    
    @PUT
    public String updateRating(Double nuevoRating)
    {
        return "Rating actualizado";
    }
    
    @GET
    public String getNombre()
    {
        return "nombre";
    }
    
    
    @GET
    @Path("{id: \\d+}")
    public UsuarioDTO darUsuario(@PathParam("id") Long id){
        return new UsuarioDTO();
    }
    
    
    
    
    
    
}
