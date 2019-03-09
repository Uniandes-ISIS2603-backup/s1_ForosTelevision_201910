/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.DiaDTO;
import co.edu.uniandes.csw.foros.ejb.EmisionDiasLogic;
import co.edu.uniandes.csw.foros.ejb.DiaLogic;
import co.edu.uniandes.csw.foros.entities.DiaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
public class EmisionDiasResource {
    
    @Inject
    private EmisionDiasLogic emDiaLogic;
    
    @Inject
    private DiaLogic diaLogic;
    
    @POST
    @Path("{diaId: \\d+}")
    public DiaDTO addDia(@PathParam("emisionId") Long emisionId, @PathParam("diaId") Long diaId){
        if(diaLogic.getDiaPorId(diaId)==null){
            throw new WebApplicationException("El dia con el id " + diaId + " no existe.", 404);
        }
        DiaDTO dto = new DiaDTO(emDiaLogic.agregarDia(emisionId, diaId));
        return dto;
    }
    
    @GET
    @Path("/dias")
    public List<DiaDTO> getDias(@PathParam("emisionId") Long emisionId){
        List<DiaDTO> dtos = new ArrayList<>();
        List<DiaEntity> entities = emDiaLogic.obtenerDias(emisionId);
        for(DiaEntity dia : entities){
            dtos.add(new DiaDTO(dia));
        }
        return dtos;
    }
    
    @GET
    @Path("{diaId: \\d+}")
    public DiaDTO getDia(@PathParam("emisionId") Long emisionId, @PathParam("diaId") Long diaId){
        if(diaLogic.getDiaPorId(diaId)==null){
            throw new WebApplicationException("El dia con el id " + diaId + " no existe.", 404);
        }
        DiaDTO dto = new DiaDTO(emDiaLogic.obtenerDia(emisionId, diaId));
        return dto;
    }
    
    @PUT
    public List<DiaDTO> updateDias(@PathParam("emisionId") Long emisionId, List<DiaDTO> dias){
        for(DiaDTO dto : dias){
            if(diaLogic.getDiaPorId(dto.getId())==null){
                throw new WebApplicationException("El dia con el id " + dto.getId() + " no existe.", 404);
            }
        }
        
        List<DiaEntity> entidades = new ArrayList<>();
        for(DiaDTO dto : dias){
            entidades.add(dto.toEntity());
        }
        
        List<DiaEntity> entidadesActuales = emDiaLogic.actualizarDias(emisionId, entidades);
        List<DiaDTO> dtosNuevos = new ArrayList<>();
        for(DiaEntity entity : entidadesActuales){
            dtosNuevos.add(new DiaDTO(entity));
        }
        
        return dtosNuevos;
    }
    
    @DELETE
    @Path("{produccionId: \\d+}")
    public void removeProduccion(@PathParam("emisionId") Long emisionId, @PathParam("produccionId") Long produccionId){
        emDiaLogic.eliminarDia(emisionId, produccionId);
    }
}
