/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ProduccionDetailDTO;
import co.edu.uniandes.csw.foros.ejb.EmisionProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.EmisionLogic;
import co.edu.uniandes.csw.foros.ejb.ProduccionLogic;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmisionProduccionesResource {
    
    @Inject
    private EmisionProduccionLogic emProdLogic;
    
    @Inject
    private ProduccionLogic logicProd;
    
    @Inject
    private EmisionLogic emLogic;
    
    @POST
    @Path("{prodId: \\d+}")
    public ProduccionDetailDTO agregarProduccion(@PathParam("emisionId") Long emisionId, @PathParam("prodId") Long prodId) throws BusinessLogicException{
        if(emLogic.getEmisionPorId(emisionId)==null){
            throw new WebApplicationException("La emisión con el id " + emisionId + " no existe.", 404);
        }
        ProduccionDetailDTO produccionDTO = new ProduccionDetailDTO(emProdLogic.agregarProduccion(emisionId, prodId));
        return produccionDTO;
    }
       
    @GET
    @Path("{produccionId: \\d+}")
    public ProduccionDetailDTO obtenerProduccion(@PathParam("emisionId") Long emisionId, @PathParam("produccionId") Long produccionId) throws BusinessLogicException{
        if(logicProd.darProduccion(produccionId)==null){
            throw new WebApplicationException("La produccion con el id " + produccionId + " no existe.", 404);
        }
        ProduccionDetailDTO dto = new ProduccionDetailDTO(emProdLogic.obtenerProduccion(emisionId));
        return dto;
    }
    
    @PUT
    public ProduccionDetailDTO actualizarProduccion(@PathParam("emisionId") Long emisionId, ProduccionDetailDTO produccion)throws BusinessLogicException{
        if(emLogic.getEmisionPorId(emisionId)==null){
            throw new WebApplicationException("La emisión con el id " + emisionId + " no existe.", 404);
        }
        ProduccionDetailDTO dto = new ProduccionDetailDTO(emProdLogic.actualizarProduccion(emisionId, produccion.toEntity()));
        return dto;
    }
    
    @DELETE
    public void removeProduccion(@PathParam("emisionId") Long emisionId)throws BusinessLogicException{
        if(emLogic.getEmisionPorId(emisionId)==null){
            throw new WebApplicationException("La emisión con el id " + emisionId + " no existe.", 404);
        }
        emProdLogic.eliminarProduccion(emisionId);
    }
}
