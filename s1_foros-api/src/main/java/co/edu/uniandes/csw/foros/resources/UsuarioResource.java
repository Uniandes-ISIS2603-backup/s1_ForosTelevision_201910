package co.edu.uniandes.csw.foros.resources;

import co.edu.uniandes.csw.foros.dtos.EmisionDTO;
import co.edu.uniandes.csw.foros.dtos.ProductoraDTO;
import co.edu.uniandes.csw.foros.dtos.UsuarioDTO;
import co.edu.uniandes.csw.foros.dtos.UtilRespuesta;
import co.edu.uniandes.csw.foros.ejb.UsuarioLogic;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
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

    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    @Inject
    private UsuarioLogic logicUser;

    /**
     * Lista de todos los usuarios
     *
     * @return Lista de todos los usuarios
     * @throws BusinessLogicException
     */
    @GET
    @Path("all")
    public List<UsuarioDTO> darUsuarios() throws BusinessLogicException {
        List<UsuarioEntity> user = logicUser.getAll();
        List<UsuarioDTO> out = new ArrayList<>();
        for (UsuarioEntity u : user) {
            out.add(new UsuarioDTO(u));
        }
        return out;
    }

    /**
     * Usuario registrado
     *
     * @param id de Usuario
     * @return usuarioDTO
     * @throws BusinessLogicException
     */
    @GET
    @Path("{id: \\d+}")
    public UsuarioDTO darUsuario(@PathParam("id") Long id) throws BusinessLogicException {
        return new UsuarioDTO(logicUser.find(id));
    }

    /**
     * Elimina un usuario
     *
     * @param id de usuario
     * @return respuesta
     */
    @DELETE
    @Path("{id: \\d+}")
    public UtilRespuesta<String> eliminarUsuario(@PathParam("id") Long id) {
        logicUser.delete(id);
        return new UtilRespuesta<>(200, "Usuario eliminado");
    }

    /**
     * Crea un nuevo usuario
     *
     * @param user usuario
     * @return respuesta de operacion
     * @throws BusinessLogicException
     */
    @POST
    public UtilRespuesta<UsuarioDTO> crearUsuario(UsuarioDTO user) throws BusinessLogicException {
        UsuarioDTO store = new UsuarioDTO(logicUser.crearUsuario(user.toEntity()));
        return new UtilRespuesta<>(200, store);
    }

    /**
     * Verifica las credenciales de usuario
     *
     * @param jsonString nombre y contraseña
     * @return validacion y datos de usuario
     * @throws BusinessLogicException
     */
    @POST
    @Path("login")
    public UsuarioDTO login(String jsonString) throws BusinessLogicException {
        JsonElement jelement = new JsonParser().parse(jsonString);
        JsonObject jobject = jelement.getAsJsonObject();
        UsuarioDTO dto = new UsuarioDTO(
                logicUser.login(jobject.get("email").getAsString(),
                        jobject.get("password").getAsString())
        );
        return dto;
    }

    /**
     * Edita la informacion de un usuario
     *
     * @param user identificador de usuario
     * @return DTO modificado
     */
    @PUT
    public UsuarioDTO editarUsuario(UsuarioDTO user) {
        return new UsuarioDTO(logicUser.update(user.toEntity()));
    }

    /**
     * Registra una productora a lista de favoritos
     *
     * @param json id_usuario y id_productora
     * @return asociacion de usuario y favorito
     * @throws BusinessLogicException
     */
    @POST
    @Path("favoritos/productora/registrar")
    public UtilRespuesta<String> registrarFavoritos(String json) throws BusinessLogicException {
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject jobject = jelement.getAsJsonObject();
        logicUser.registrarProductoraFavorito(jobject.get("id_usuario").getAsLong(), jobject.get("id_productora").getAsLong());
        return new UtilRespuesta<>(200, "Registro produccion realizado");
    }

    /**
     * Retorna lista de usuario seguidos
     *
     * @param id usuario
     * @return lista de usuarios seguidos por un usuario
     * @throws BusinessLogicException
     */
    @GET
    @Path("{id: \\d+}/favoritos")
    public List<UsuarioDTO> darUsuarioSeguidos(@PathParam("id") Long id) throws BusinessLogicException {
        List<UsuarioDTO> dto = new ArrayList<>();
        List<UsuarioEntity> users = logicUser.darUsuariosFavoritos(id);
        for (UsuarioEntity as : users) {
            dto.add(new UsuarioDTO(as));
        }
        return dto;
    }

    /**
     * Elimina un usuario de la lista de favoritos
     *
     * @param idfav identificador de usuario
     * @param id    identificador de usuario a eliminar
     * @return resultado de operacion
     * @throws BusinessLogicException
     */
    @POST
    @Path("{idfav: \\d+}/favoritos/eliminar/{id: \\d+}")
    public UtilRespuesta<String> elimnarFavoritos(@PathParam("idfav") Long idfav, @PathParam("id") Long id) throws BusinessLogicException {
        logicUser.eliminarProductoraFavorito(idfav, id);
        return new UtilRespuesta<>(200, "Produccion eliminada");
    }

    /**
     * Registra la vista de una produccion a un  usuario
     *
     * @param jsonString identificador de usuario y identifcador produccion
     * @return resultado de operacion
     * @throws BusinessLogicException
     */
    @POST
    @Path("productora/crear")
    public UtilRespuesta<String> registrarVistas(String jsonString) throws BusinessLogicException {
        JsonElement elemento = new JsonParser().parse(jsonString);
        JsonObject obj = elemento.getAsJsonObject();
        logicUser.registrarProductoraFavorito(obj.get("id_user").getAsLong(), obj.get("id_produccion").getAsLong());
        return new UtilRespuesta<>(200, "Registro realizado productora");
    }
    
    
    /**
     * Registra el horario de emision de una produccion de interes para el usuario
     *
     * @param jsonString identificador de usuario y identifcador horario
     * @return resultado de operacion
     * @throws BusinessLogicException
     */
    @POST
    @Path("emision/crear")
    public UtilRespuesta<String> registrarEmsion(String jsonString) throws BusinessLogicException {
        JsonElement elemento = new JsonParser().parse(jsonString);
        JsonObject obj = elemento.getAsJsonObject();
        logicUser.registrarEmision(obj.get("id_user").getAsLong(), obj.get("id_emision").getAsLong());
        return new UtilRespuesta<>(200, "Registro realizado emision");
    }
    
    /**
     * Retorna horario de emisiones favoritas
     *
     * @param id identificador de usuario
     * @return lista de emisiiones favoritas
     * @throws BusinessLogicException
     */
    @GET
    @Path("{id: \\d+}/emisiones")
    public List<EmisionDTO> darHorarioEmisiones(@PathParam("id") Long id) throws BusinessLogicException {
        List<EmisionDTO> dto = new ArrayList();
        List<EmisionEntity> entity = logicUser.darEmisiones(id);
        for (EmisionEntity ent : entity) {
            dto.add(new EmisionDTO(ent));
        }
        return dto;
    }
    /**
     * Retorna lista de productoras favoritas
     *
     * @param id identificador de usuario
     * @return lista de producciones favoritas
     * @throws BusinessLogicException
     */
    @GET
    @Path("{id: \\d+}/productoras")
    public List<ProductoraDTO> darProduccionesVistas(@PathParam("id") Long id) throws BusinessLogicException {
        List<ProductoraDTO> dto = new ArrayList();
        List<ProductoraEntity> entity = logicUser.darProductoraFavoritas(id);
        for (ProductoraEntity ent : entity) {
            dto.add(new ProductoraDTO(ent));
        }
        return dto;
    }

    /**
     * Elimina la infomrmacion de interaccion con un usuario
     *
     * @param jsonString
     * @return
     * @throws BusinessLogicException
     */

    @POST
    @Path("vistas/eliminar")
    public UtilRespuesta<String> elimnarProduccionVista(String jsonString) throws BusinessLogicException {

        return new UtilRespuesta<>(200, "Produccion eliminada");
    }

    /**
     * Relaciona un seguidor y un usuario
     *
     * @param jsonString identicador usuario, identificador usuario
     * @return respuesta de operacion
     * @throws BusinessLogicException
     */
    @POST
    @Path("seguir")
    public UtilRespuesta<String> seguirUsuario(String jsonString) throws BusinessLogicException {
        JsonElement jelement = new JsonParser().parse(jsonString);
        JsonObject jobject = jelement.getAsJsonObject();
        logicUser.seguirUsuario(jobject.get("id_usuario").getAsLong(), jobject.get("id_seguidor").getAsLong());
        return new UtilRespuesta<>(200, "Usuario registrado");
    }

    /**
     * Lista de seguidores de un usuario
     *
     * @param id identificador de usuario
     * @return lista de seguidores de usuario
     */
    @GET
    @Path("{usuario: \\d+}/seguidores")
    public List<UsuarioDTO> darSeguidoresUsuario(@PathParam("usuario") Long id) {
        List<UsuarioDTO> user = new ArrayList<>();
        List<UsuarioEntity> loc = logicUser.usuariosSeguidos(id);
        for (UsuarioEntity u : loc) {
            user.add(new UsuarioDTO(u));
        }
        return user;
    }

}
