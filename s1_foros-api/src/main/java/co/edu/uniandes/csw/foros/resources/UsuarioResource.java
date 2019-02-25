package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ProduccionDTO;
import co.edu.uniandes.csw.foros.dtos.UsuarioDTO;
import co.edu.uniandes.csw.foros.dtos.UtilRespuesta;
import co.edu.uniandes.csw.foros.ejb.UsuarioLogic;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;


import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;

@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {

    private static final Logger LOGGER= Logger.getLogger(UsuarioResource.class.getName());
    @Inject
    private UsuarioLogic logicUser;
    
    @GET
    @Path("all")
    public List<UsuarioDTO> darUsuarios() throws BusinessLogicException {
        List<UsuarioEntity> user=logicUser.getAll();
        List<UsuarioDTO> out=new ArrayList<>();
        for(UsuarioEntity u : user){
            out.add(new UsuarioDTO(u));
        }
        return out;
    }

    @GET
    @Path("{id: \\d+}")
    public UsuarioDTO darUsuario(@PathParam("id") Long id) throws BusinessLogicException {
        return new UsuarioDTO(logicUser.find(id));
    }

    @DELETE
    @Path("{id: \\d+}")
    public UtilRespuesta<String> eliminarUsuario(@PathParam("id") Long id) {
        logicUser.delete(id);
        return new UtilRespuesta<String>(200,"Usuario eliminado");
    }

    @POST
    public UtilRespuesta<UsuarioDTO> crearUsuario(UsuarioDTO  user) throws BusinessLogicException { 
       UsuarioDTO store=new UsuarioDTO(logicUser.crearUsuario(user.toEntity()));
        return new UtilRespuesta<UsuarioDTO>(200,store);
    }

    @POST
    @Path("/login")
    public UsuarioDTO login(String jsonString) throws BusinessLogicException{
        JsonElement jelement = new JsonParser().parse(jsonString);
        JsonObject  jobject = jelement.getAsJsonObject();
        UsuarioDTO dto=new UsuarioDTO(
                logicUser.login(jobject.get("email").getAsString(),
                jobject.get("password").getAsString())
        );
        return dto;
    }

    @PUT
    public UsuarioDTO editarUsuario(UsuarioDTO user) {
       return new UsuarioDTO(logicUser.update(user.toEntity()));
    }

    @POST
    @Path("/favoritos/crear")
    public String registrarFavoritos(String jsonString){
        return jsonString;
    }

    @GET
    @Path("/{id: \\d+}/favoritos")
    public List<UsuarioDTO> darUsuariosFavoritos(@PathParam("id") Long id){
        return new ArrayList<UsuarioDTO>();
    }

    @DELETE
    @Path("/favoritos/eliminar/{id: \\d+}")
    public String elimnarFavoritos(@PathParam("id") Long id){
        return id.toString();
    }

    @POST
    @Path("/vistas/crear")
    public String registrarVistas(String jsonString){
        return jsonString;
    }

    @GET
    @Path("/{id: \\d+}/vistas")
    public ProduccionDTO darProduccionesVistas(@PathParam("id") Long id){
        return new ProduccionDTO();
    }

    @POST
    @Path("/vistas/eliminar")
    public String elimnarProduccionVista(String jsonString){
        return jsonString;
    }

    @POST
    @Path("/seguir")
    public String seguirUsuario(String jsonString) throws BusinessLogicException{
        JsonElement jelement = new JsonParser().parse(jsonString);
        JsonObject  jobject = jelement.getAsJsonObject();
       logicUser.seguirUsuario(jobject.get("id_usuario").getAsLong(),jobject.get("id_seguidor").getAsLong());
       return "Usuario Registrado";
    }

    @GET
    @Path("/{usuario: \\d+}/seguidores/{id: \\d+}")
    public List<UsuarioDTO> darSeguidoresUsuario(@PathParam("id") Long id,@PathParam("usuario") Long usuario_id){
        List<UsuarioDTO> user=new ArrayList<>();
        List<UsuarioEntity> loc=logicUser.usuariosSeguidos(id);
        for(UsuarioEntity  u: loc ){
            user.add(new UsuarioDTO(u));
        }
        return user;
    }





 
}
