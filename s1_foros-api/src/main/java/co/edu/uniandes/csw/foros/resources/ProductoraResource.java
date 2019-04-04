package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ProductoraDTO;
import co.edu.uniandes.csw.foros.dtos.ProductoraDetailDTO;
import co.edu.uniandes.csw.foros.ejb.ProductoraLogic;
import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
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
@Path("productoras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProductoraResource {

    private static final Logger LOGGER = Logger.getLogger(ProductoraResource.class.getName());

    @Inject
    private ProductoraLogic productoraLogic;

    /**
     * Obtiene una productora según su id.
     *
     * @param id id de la productora buscada.
     * @return el DTO con la información de la producción.
     */
    @GET
    @Path("{id:\\d+}")
    public ProductoraDTO darProductora(@PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "ProductoraResource darProductora: input: {0}", id);
        ProductoraEntity productoraEntity = null;
        try {
            productoraEntity = productoraLogic.find(id);
            if (productoraEntity == null) {
                throw new WebApplicationException("El recurso/productoras/" + id + " no existe.", 404);
            }
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 412);
        }
        return new ProductoraDTO(productoraEntity);
    }

    /**
     * Crea una productora con la información dada.
     *
     * @param productoraDTO
     * @return ProductoraDTO con la información de la nueva categoria.
     */
    @POST
    public ProductoraDTO crearProductora(ProductoraDTO productoraDTO) {
        LOGGER.log(Level.INFO, "ProductoraResource crearProductora : input: {0}");
        ProductoraDTO nuevaProductora = null;
        try {
            ProductoraEntity entity = productoraLogic.crearProductora(productoraDTO.toEntity());
            nuevaProductora = new ProductoraDTO(entity);
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 412);
        }
        return nuevaProductora;
    }

    /**
     * Modifica la informacion de una productora.
     *
     * @param id
     * @param productora
     * @return ProductoraDTO la cual fue actualizada.
     * @throws co.edu.uniandes.csw.foros.exceptions.BusinessLogicException
     */
    @PUT
    @Path("{id: \\d+}")
    public ProductoraDetailDTO editarProductora(@PathParam("id") Long id, ProductoraDetailDTO productora) throws BusinessLogicException {
    LOGGER.log(Level.INFO, "ProductoraResource editarProductora: input: id: {0} , productora: {1}", new Object[]{id, productora});
        productora.setId(id);
        if (productoraLogic.getProductora(id) == null) {
            throw new WebApplicationException("El recurso /productoras/" + id + " no existe.", 404);
        }
        ProductoraDetailDTO detailDTO = new ProductoraDetailDTO(productoraLogic.editarProductora(id, productora.toEntity()));
        LOGGER.log(Level.INFO, "ProductoraResource editarProductora: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Elimina una productora
     *
     * @param id id de la productora a eliminar.
     * @return
     */
    @DELETE
    @Path("{id:\\d+}")
    public String eliminarProductora(@PathParam("id") Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProdcutoraResource eliminarProductora: input: {0}", id);
        ProductoraEntity entity = productoraLogic.getProductora(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /productoras/" + id + " no existe.", 404);
        }
        productoraLogic.borrarProductora(id);
        LOGGER.info("ProduccionResource eliminarProduccion: output: void");
        return "Se borro exitosamente la productora con id: " + id;
    }

    /**
     * Retorna las producciones de la productora.
     *
     * @param id id de la productora a la que se le consultan sus producciones.
     * @return La lista de las producciones de la productora.
     */
    @GET
    public List<ProductoraDetailDTO> darProductoras() {
        LOGGER.info("ProductoraResource darProductoras: input: void");
        List<ProductoraDetailDTO> listaProductoras = listEntity2DTO(productoraLogic.getProductoras());
        LOGGER.log(Level.INFO, "ProductoraResource darProductoras: output: {0}", listaProductoras);
        return listaProductoras;    }
    
    
    /**
     * Convierte una lista de ProductoraEntity a una lista de ProductoraDetailDTO.
     *
     * @param entityList Lista de AuthorEntity a convertir.
     * @return Lista de ProductoraEntity convertida.
     */
    private List<ProductoraDetailDTO> listEntity2DTO(List<ProductoraEntity> entityList) {
        List<ProductoraDetailDTO> list = new ArrayList<>();
        for (ProductoraEntity entity : entityList) {
            list.add(new ProductoraDetailDTO(entity));
        }
        return list;
    }
}
