package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ProductoraDTO;
import co.edu.uniandes.csw.foros.dtos.ProductoraDetailDTO;
import co.edu.uniandes.csw.foros.ejb.ProductoraLogic;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import org.springframework.integration.handler.LoggingHandler;

/**
 *
 * @author Jhonattan Fonseca.
 */
public class ProductoraResource {

    private static final Logger LOGGER = Logger.getLogger(MultimediaResource.class.getName());

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
     * @param jsonString string en dormato json que contiene la informacion
     * necesaria para crear una productora.
     * @return mensaje de éxito del proceso.
     */
    @POST
    public String crearProductor(ProductoraDTO productoraDTO) {
        LOGGER.log(Level.INFO, "ProductoraResource crearProductora : input: {0}");
        ProductoraDTO nuevaProductora;
        try {
            nuevaProductora = new ProductoraDTO(productoraLogic.crearProductora(productoraDTO.toEntity()));
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 412);
        }
        return "Productora creada";
    }

    /**
     * Modifica la informacion de una productora.
     *
     * @param <error>
     * @param <error>
     * @param jsonString string en formato json con la nueva info de la
     * productora.
     * @return mensaje de éxito del proceso.
     */
    @PUT
    @Path("{id: \\d+}")
    public ProductoraDTO editarProductora(@PathParam("id") Long id, ProductoraDTO productoraDTO) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "ProductoraResource editarProductora: input: id: {0} , productora: {1}", new Object[]{id, productoraDTO.toString()});
        productoraDTO.setId(id);
        if(productoraLogic.getProductora(id) == null)
        {
            throw new WebApplicationException("El recurso /productoras/" + id + " no existe.", 404);
        }
        ProductoraDetailDTO productoraDetailDTO;
        try
        {
            productoraDetailDTO = new ProductoraDetailDTO(productoraLogic.editarProduccion(id, productoraDTO.toEntity()));
        }
        catch(BusinessLogicException e)
        {
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
    @Path("/productoras/eliminar/{id:\\d+}")
    public String eliminarProductora(@PathParam("id") Long id) {
        return "se eliminó la productora";
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
