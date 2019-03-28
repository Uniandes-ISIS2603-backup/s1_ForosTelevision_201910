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

/**
 *
 * @author Jhonattan Fonseca.
 */
@Path("productoras")
@Produces("application/json")
@Consumes("application/json")
public class ProductoraResource {

    private static final Logger LOGGER = Logger.getLogger(MultimediaResource.class.getName());

    @Inject
    private ProductoraLogic productoraLogic;

    /**
     * Obtiene una productora según su id.
     *
     * @param id id de la productora buscada.
     * @return el DTO con la información de la producción.
     */
    @GET
    @Path("/productoras/{id:\\d+}")
    public ProductoraDTO darProductora(@PathParam("id") Long id) {
        return new ProductoraDTO();
    }

    /**
     * Crea una productora con la información dada.
     *
     * @return mensaje de éxito del proceso.
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
     * @return ProductoraDTO la cual fue actualizada.
     */
    @PUT
    @Path("{id: \\d+}")
    public ProductoraDTO editarProductora(@PathParam("id") Long id, ProductoraDTO productoraDTO) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "ProductoraResource editarProductora: input: id: {0} , productora: {1}", new Object[]{id, productoraDTO.toString()});
        productoraDTO.setId(id);
        if (productoraLogic.getProductora(id) == null) {
            throw new WebApplicationException("El recurso /productoras/" + id + " no existe.", 404);
        }
        ProductoraDetailDTO productoraDetailDTO;
        try {
            productoraDetailDTO = new ProductoraDetailDTO(productoraLogic.editarProduccion(id, productoraDTO.toEntity()));
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 412);
        }
        LOGGER.log(Level.INFO, "ProdcutoraREsource editarProductora: output: {0}", productoraDetailDTO.toString());
        return productoraDetailDTO;
    }

    /**
     * Elimina una productora
     *
     * @param id id de la productora a eliminar.
     * @return
     */
    @DELETE
    @Path("{id:\\d+}")
    public void eliminarProductora(@PathParam("id") Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProdcutoraResource eliminarProductora: input: {0}", id);
        ProductoraEntity entity = productoraLogic.getProductora(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /productoras/" + id + " no existe.", 404);
        }
        productoraLogic.borrarProductora(id);
        LOGGER.info("ProduccionResource eliminarProduccion: output: void");
    }

    /**
     * Retorna las producciones de la productora.
     *
     * @param id id de la productora a la que se le consultan sus producciones.
     * @return La lista de las producciones de la productora.
     */
    @GET
    @Path("/productoras/(id:\\d+)/producciones")
    public List<ProductoraDTO> darProducciones(@PathParam("id") Long id) {
        return new ArrayList<>();
    }
}
