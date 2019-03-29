/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;
import co.edu.uniandes.csw.foros.dtos.CanalDTO;
import co.edu.uniandes.csw.foros.dtos.UtilRespuesta;
import co.edu.uniandes.csw.foros.ejb.CanalLogic;
import co.edu.uniandes.csw.foros.entities.CanalEntity;
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
@Path("canales")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped


public class CanalResource {
    
    @Inject
    CanalLogic canalLogic;
    
    @Inject
    private CanalLogic logica;
    
    private static final Logger LOGGER= Logger.getLogger(UsuarioResource.class.getName());
    
    @POST
    public CanalDTO crearCanal(CanalDTO canalDTO) throws BusinessLogicException
    {
    
        CanalEntity canalEntity=canalDTO.toEntity();
        canalEntity =logica.createCanal(canalEntity);
        return new CanalDTO (canalEntity);
    }
    
    @PUT
    @Path("{id: \\d+}")
    public CanalDTO updateCanal(CanalDTO canalDTO,@PathParam("id") Long idCanal) throws BusinessLogicException
    {
        //Busca el id del canal a actualizar
        CanalEntity entity=logica.darCanal(idCanal);
        //Convierte el DTO a Entity
        CanalEntity nuevoCanal=canalDTO.toEntity();
        logica.actualizarCanal(nuevoCanal,idCanal);
         return canalDTO;
    }
    
    @GET
    @Path("{id: \\d+}")
    public CanalEntity getCanal(@PathParam("id") Long idCanal) throws BusinessLogicException
    {
        CanalEntity canal=logica.darCanal(idCanal);
        return canal;    
    }
    
    
        
         /**
     * Lista de todos los canales
     *
     * @return Lista de todos los canales
     * @throws BusinessLogicException
     */
    @GET
    @Path("all")
    public List<CanalDTO> darCanales() throws BusinessLogicException {
        List<CanalEntity> user = canalLogic.getAll();
        List<CanalDTO> out = new ArrayList<>();
        for (CanalEntity u : user) {
            CanalDTO canalDTO=new CanalDTO(u);
            out.add(canalDTO);
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
    public UtilRespuesta<String>  eliminarCanal(@PathParam("id") Long idCanal) throws BusinessLogicException
    {
      logica.eliminarCanal(idCanal);
       return new UtilRespuesta(200,"se eliminó el canal");
    }
    
   
    
    
    
    
    
    
    
    
    
}
