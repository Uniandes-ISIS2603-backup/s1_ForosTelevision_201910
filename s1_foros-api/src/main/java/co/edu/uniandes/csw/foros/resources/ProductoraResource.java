/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ProductoraDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author Jhonattan Fonseca.
 */
public class ProductoraResource {

    private static final Logger LOGGER = Logger.getLogger(MultimediaResource.class.getName());

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
    public String crearProductora(String jsonString) {
        return "Productora creada";
    }

    /**
     * Modifica la informacion de una productora.
     *
     * @param jsonString string en formato json con la nueva info de la
     * productora.
     * @return mensaje de éxito del proceso.
     */
    @PUT
    public String editarProductora(String jsonString) {
        return "productora editada";
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
     * @param id id de la productora a la que se le consultan sus producciones.
     * @return La lista de las producciones de la productora.
     */
    @GET
    @Path("/productoras/(id:\\d+)/producciones")
    public List<ProductoraDTO> darProducciones(@PathParam("id") Long id) {
        return new ArrayList<>();
    }
}
