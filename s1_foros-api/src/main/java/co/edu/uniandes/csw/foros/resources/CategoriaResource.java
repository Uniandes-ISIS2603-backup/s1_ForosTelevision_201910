package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.CategoriaDTO;
import co.edu.uniandes.csw.foros.dtos.CategoriaDetailDTO;
import co.edu.uniandes.csw.foros.ejb.CategoriaLogic;
import co.edu.uniandes.csw.foros.entities.CategoriaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Jhonattan Fonseca.
 */
@Path("categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CategoriaResource {
    
    private static final Logger LOGGER = Logger.getLogger(MultimediaResource.class.getName());
    
    @Inject
    private CategoriaLogic categoriaLogic;

    /**
     * Obtiene una categoria según su id.
     *
     * @param id id de la categoria buscada.
     * @return el DTO con la información de la categoria..
     */
    @GET
    @Path("{id:\\d+}")
    public CategoriaDTO darCategoria(@PathParam("id") Long id) {
        
        LOGGER.log(Level.INFO, "CategoríaResource darCategoria: input {0}", id);
        CategoriaEntity entity = null;
        try {
            entity = categoriaLogic.find(id);
            if (entity == null) {
                throw new WebApplicationException("El recurso/categorias/" + id + " no existe.", 404);
            }
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 412);
        }
        return new CategoriaDTO(entity);
    }

    /**
     * Crea una categoria con la información dada.
     *
     * @param categoriaDTO
     * @return CategoriaDTO con la información de la nueva categoria.
     */
    @POST
    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        LOGGER.log(Level.INFO, "CategoriaResource crearCategoria : input: {0}");
        CategoriaDTO nuevaCategoria = null;
        try {
            CategoriaEntity entity = categoriaLogic.crearCategoria(categoriaDTO.toEntity());
            nuevaCategoria = new CategoriaDTO(entity);
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 412);
        }
        return nuevaCategoria;
    }

    /**
     * Modifica la informacion de una productora.
     *
     * @param id
     * @param categoria
     * @return ProductoraDTO la cual fue actualizada.
     * @throws co.edu.uniandes.csw.foros.exceptions.BusinessLogicException
     */
    @PUT
    @Path("{id: \\d+}")
    public CategoriaDetailDTO editarProductora(@PathParam("id") Long id, CategoriaDetailDTO categoria) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CategoriaResource editarCategoria: input: id: {0} , categoria : {1}", new Object[]{id, categoria});
        categoria.setId(id);
        if (categoriaLogic.getCategoria(id) == null) {
            throw new WebApplicationException("El recurso /categorias/" + id + " no existe.", 404);
        }
        CategoriaDetailDTO detailDTO = new CategoriaDetailDTO(categoriaLogic.editarCategoria(id, categoria.toEntity()));
        LOGGER.log(Level.INFO, "CategoriaResoruce editarCategoria: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Elimina una Categoria por su id.
     *
     * @param id id de la categoria a eliminar.
     * @return
     */
    @DELETE
    @Path("{id:\\d+}")
    public String eliminarProductora(@PathParam("id") Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProdcutoraResource eliminarcategoria: input: {0}", id);
        CategoriaEntity entity = categoriaLogic.getCategoria(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /productoras/" + id + " no existe.", 404);
        }
        categoriaLogic.borrarCategoria(id);
        LOGGER.info("ProduccionResource eliminarProduccion: output: void");
        return "Se borro exitosamente la categoria con id: " + id;
    }
    
    @GET
    public List<CategoriaDetailDTO> darCategorias() {
        LOGGER.info("CategoriaREsource darCategorias: input: void");
        List<CategoriaDetailDTO> listacategoria = listEntity2DTO(categoriaLogic.getCategorias());
        LOGGER.log(Level.INFO, "CategoriaResource darCategorias: input: {0}", listacategoria);
        return listacategoria;
    }
    
    
    /**
     * Convierte una lista de CategoriaEntity a una lista de CategoriaDetailDTO.
     *
     * @param entityList Lista de CategoriaEntity a convertir.
     * @return Lista de AuthorDetailDTO convertida.
     */
    private List<CategoriaDetailDTO> listEntity2DTO(List<CategoriaEntity> entityList) {
        List<CategoriaDetailDTO> list = new ArrayList<>();
        for (CategoriaEntity entity : entityList) {
            list.add(new CategoriaDetailDTO(entity));
        }
        return list;
    }
}
