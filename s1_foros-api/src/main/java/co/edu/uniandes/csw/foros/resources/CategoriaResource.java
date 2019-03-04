package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.CategoriaDTO;
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
public class CategoriaResource {

    private static final Logger LOGGER = Logger.getLogger(MultimediaResource.class.getName());

    @GET
    @Path("/categorias/(id:\\d+)")
    public CategoriaDTO darCategoria(@PathParam("id") Long id) {
        return new CategoriaDTO();
    }

    
    /**
     * Crea una categoria con la informacion dada.
     * @param jsonString string en formato json que contiene la informacion
     * necesaria para crear una categoria.
     * @return mensaje de éxito del proceso.
     */
    @POST
    public String crearCategoria(String jsonString) {
        return "categoria creada";
    }

    /**
     * Modifica la informacion de una categoria.
     *
     * @param jsonString string en formato json con la nueva info de la
     * categoria.
     * @return mensaje de éxito del proceso.
     */
    @PUT
    public String editarCategoria(String jsonString) {
        return "categoria editada";
    }

    /**
     * Elimina una categoria
     *
     * @param id id de la categoria a eliminar.
     * @return
     */
    @DELETE
    @Path("/categorias/eliminar/{id:\\d+}")
    public String eliminarCategoria(@PathParam("id") Long id) {
        return "se eliminó la cateogoria";
    }
}
