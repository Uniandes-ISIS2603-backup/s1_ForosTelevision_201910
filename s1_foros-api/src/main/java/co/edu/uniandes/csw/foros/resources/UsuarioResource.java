package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.ProduccionDTO;
import co.edu.uniandes.csw.foros.dtos.UsuarioDTO;
import java.util.ArrayList;


import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {

    private static final Logger LOGGER= Logger.getLogger(UsuarioResource.class.getName());

    @GET
    public List<UsuarioDTO> darUsuarios(){
        List<UsuarioDTO> retorno =new LinkedList<>();
        return retorno;
    }

    @GET
    @Path("{id: \\d+}")
    public UsuarioDTO darUsuario(@PathParam("id") Long id){
        return new UsuarioDTO();
    }

    @DELETE
    @Path("{id: \\d+}")
    public String eliminarUsuario(@PathParam("id") Long id){
        return id.toString();
    }

    @POST
    public String crearUsuario(String jsonString){
        return  jsonString;
    }

    @POST
    @Path("/usuarios/login")
    public String login(String jsonString){
        return jsonString;
    }

    @PUT
    public String editarUsuario(String jsonString) {
        return jsonString;
    }

    @POST
    @Path("/usuarios/favoritos/crear")
    public String registrarFavoritos(String jsonString){
        return jsonString;
    }

    @GET
    @Path("/usuarios/{id: \\d+}/favoritos")
    public List<UsuarioDTO> darUsuariosFavoritos(@PathParam("id") Long id){
        return new ArrayList<UsuarioDTO>();
    }

    @DELETE
    @Path("/usuarios/favoritos/eliminar/{id: \\d+}")
    public String elimnarFavoritos(@PathParam("id") Long id){
        return id.toString();
    }

    @POST
    @Path("/usuarios/vistas/crear")
    public String registrarVistas(String jsonString){
        return jsonString;
    }

    @GET
    @Path("/usuarios/{id: \\d+}/vistas")
    public ProduccionDTO darProduccionesVistas(@PathParam("id") Long id){
        return new ProduccionDTO();
    }

    @POST
    @Path("/usuarios/vistas/eliminar")
    public String elimnarProduccionVista(String jsonString){
        return jsonString;
    }

    @POST
    @Path("/usuarios/seguir")
    public String seguirUsuario(String jsonString){
        return jsonString;
    }

    @GET
    @Path("/usuarios/{usuario: \\d+}/seguidores/{id: \\d+}")
    public UsuarioDTO darSeguidoresUsuario(@PathParam("id") Long id,@PathParam("usuario") Long usuario_id){
        return new UsuarioDTO();
    }






}
