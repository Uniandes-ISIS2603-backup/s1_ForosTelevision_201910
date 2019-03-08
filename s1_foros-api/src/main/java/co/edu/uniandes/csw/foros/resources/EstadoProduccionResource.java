/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ProduccionDTO;
import co.edu.uniandes.csw.foros.ejb.EstadoProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.ProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.EstadoLogic;
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
public class EstadoProduccionResource {
    
    @Inject
    private EstadoProduccionLogic estProdLogic;
    
    @Inject
    private EstadoLogic estLogic;
    
    @Inject
    private ProduccionLogic logicProd;
    
    @POST
    @Path("{prodId: \\d+}")
    public ProduccionDTO addProduccion(@PathParam("estadoId") Long estadoId, @PathParam("prodId") Long prodId) throws BusinessLogicException{
        if(logicProd.darProduccion(prodId)==null){
            throw new WebApplicationException("El estado con el id " + prodId + " no existe.", 404);
        }
        if(estLogic.getEstadoPorId(estadoId)==null){
            throw new WebApplicationException("El estado con el id " + estadoId + " no existe.", 404);
        }
        ProduccionDTO produccionDTO = new ProduccionDTO(estProdLogic.agregarProduccion(estadoId, prodId));
        return produccionDTO;
    }
     
    @GET
    public ProduccionDTO getProduccion(@PathParam("estadoId") Long estadoId) throws BusinessLogicException{
        if(estLogic.getEstadoPorId(estadoId)==null){
            throw new WebApplicationException("El estado con el id " + estadoId + " no existe.", 404);
        }
        ProduccionDTO dto = new ProduccionDTO(estProdLogic.obtenerProduccion(estadoId));
        return dto;
    }
    
    @PUT
    public ProduccionDTO updateProduccion(@PathParam("estadoId") Long estadoId, ProduccionDTO produccion)throws BusinessLogicException{
        if(estLogic.getEstadoPorId(estadoId)==null){
            throw new WebApplicationException("El estado con el id " + estadoId + " no existe.", 404);
        }
        ProduccionDTO dto = new ProduccionDTO(estProdLogic.actualizarProduccion(estadoId, produccion.toEntity()));
        return dto;
    }
    
    @DELETE
    public void removeProduccion(@PathParam("estadoId") Long estadoId) throws BusinessLogicException{
        if(estLogic.getEstadoPorId(estadoId)==null){
            throw new WebApplicationException("El estado con el id " + estadoId + " no existe.", 404);
        }
        estProdLogic.eliminarProduccion(estadoId);
    }
}