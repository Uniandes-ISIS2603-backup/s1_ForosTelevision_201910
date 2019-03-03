package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.MultimediaDTO;
import co.edu.uniandes.csw.foros.dtos.UtilRespuesta;
import co.edu.uniandes.csw.foros.ejb.MultimediaLogic;
import co.edu.uniandes.csw.foros.entities.MultimediaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.util.logging.Logger;
import javax.inject.Inject;

@Path("multimedia")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MultimediaResource {
    
    @Inject
    private MultimediaLogic logicMultimedia;

    private static final Logger LOGGER= Logger.getLogger(MultimediaResource.class.getName());
    
    /**
     * Retorna los recursos asociados a alguna produccion
     * @param id identificador de recurso
     * @return entidad de recursos
     * @throws BusinessLogicException si no  exitste la produccion 
     */
    @GET
    @Path("/produccion/{id: \\d+}")
    public MultimediaEntity darRecursosMultimedia(@PathParam("id")Long id) throws BusinessLogicException{
        return logicMultimedia.darRecursosMultimediaProduccion(id);
    }

    /**
     * Crea un recurso multimedia
     * @param multimediaDTO entidad de datos
     * @return estado de operacion
     * @throws BusinessLogicException si el formato de archivos no es adecuado
     */
    @POST
    public UtilRespuesta<String> crearMultimedia(MultimediaDTO multimediaDTO) throws BusinessLogicException{
        logicMultimedia.crearRecursoMultimedia(multimediaDTO.toEntity());
        return new UtilRespuesta<>(200,"Recurso multimedia creado");
    }
    
    /**
     * Agrega una imagen a la galeria
     * @param json identificador de recurso y url de imagen
     * @return estado de operacion
     * @throws BusinessLogicException ulr o recurso no encontrado 
     */
    @POST
    @Path("cambiar/imagen")
    public UtilRespuesta<String> adicionarImagenMultimedia(String json) throws BusinessLogicException{
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject inobj = jelement.getAsJsonObject();
        logicMultimedia.adicionarImagen(inobj.get("id_multimedia").getAsLong(),inobj.get("imagen").getAsString());
        return new UtilRespuesta<>(200,"Imagen editada");
    }
    
    /**
     * Cambiar video 
     * @param json identificador de recurso y url de video
     * @return estado de operacion
     * @throws BusinessLogicException url de formato no valido 
     */
    @POST
    @Path("cambiar/video")
    public UtilRespuesta<String> cambiarVideoMultimedia(String json) throws BusinessLogicException{
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject inobj = jelement.getAsJsonObject();
        logicMultimedia.adicionarVideo(inobj.get("id_multimedia").getAsLong(),inobj.get("video").getAsString());
        return new UtilRespuesta<>(200,"Recurso de video editado");
    }

}
