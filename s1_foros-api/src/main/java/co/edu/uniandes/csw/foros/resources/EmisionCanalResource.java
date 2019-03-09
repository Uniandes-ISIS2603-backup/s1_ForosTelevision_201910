/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.CanalDTO;
import co.edu.uniandes.csw.foros.dtos.EmisionDetailDTO;
import co.edu.uniandes.csw.foros.ejb.CanalLogic;
import co.edu.uniandes.csw.foros.ejb.EmisionCanalLogic;
import co.edu.uniandes.csw.foros.ejb.EmisionLogic;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ne.ortega
 */
@Path("emisiones/{emisionId: \\d+}/canal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmisionCanalResource {
    
    @Inject
    private EmisionLogic emLogic;
    
    @Inject
    private CanalLogic canLogic;
    
    @Inject
    private EmisionCanalLogic relacionLogic;
    
    @PUT
    public EmisionDetailDTO actualizarCanal(@PathParam("emisionId") Long emisionId, CanalDTO canal) throws BusinessLogicException{
        
        if(emLogic.getEmisionPorId(emisionId)==null){
            throw new WebApplicationException("La emision con el id" + emisionId + " no existe.", 404);
        }
        
        EmisionDetailDTO emision = new EmisionDetailDTO(relacionLogic.actualizarCanal(emisionId, canal.toEntity()));
        return emision;
    }
}
