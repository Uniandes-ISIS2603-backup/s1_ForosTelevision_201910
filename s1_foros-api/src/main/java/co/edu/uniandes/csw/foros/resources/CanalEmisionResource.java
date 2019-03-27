/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.UtilRespuesta;
import co.edu.uniandes.csw.foros.ejb.CanalEmisionLogic;
import co.edu.uniandes.csw.foros.ejb.CanalLogic;
import co.edu.uniandes.csw.foros.ejb.EmisionLogic;
import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author mi.carrascal
 */
//    @Path("canal/{canalId: \\d+}/emision")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
public class CanalEmisionResource {
    
    @Inject
    private EmisionLogic emLogic;
    
    @Inject
    private CanalLogic canLogic;
    
    @Inject
    private CanalEmisionLogic relacionLogic;
    
    
    @GET
    @Path("/emisiones")
    public List<EmisionEntity> darEmisiones(@PathParam("id")Long idCanal)
    {
        return relacionLogic.darEmisiones(idCanal);
        
    }
    
    @DELETE
    @Path("{canalId: \\d+}")
    public UtilRespuesta<String>  eliminarEmision(@PathParam("id") Long idCanal,Long idEmision) throws BusinessLogicException
    {
        relacionLogic.eliminarEmision(idEmision, idCanal);
       return new UtilRespuesta(200,"se eliminó la emision");
    }
    
    @PUT
    @Path("{canalId: \\d+}")
    public UtilRespuesta<String>  actualizarEmision(Long idEmision,EmisionEntity emision) throws BusinessLogicException
    {
        relacionLogic.updateEmision(idEmision, idEmision, emision);
       return new UtilRespuesta(200,"se eliminó la emision");
    }
//    @PUT
//    public EmisionDetailDTO actualizarCanal(@PathParam("emisionId") Long emisionId, CanalDTO canal) throws BusinessLogicException{
//        
//        if(emLogic.getEmisionPorId(emisionId)==null){
//            throw new WebApplicationException("La emision con el id" + emisionId + " no existe.", 404);
//        }
//        
//        EmisionDetailDTO emision = new EmisionDetailDTO(relacionLogic.actualizarCanal(emisionId, canal.toEntity()));
//        return emision;
    }
    
    
