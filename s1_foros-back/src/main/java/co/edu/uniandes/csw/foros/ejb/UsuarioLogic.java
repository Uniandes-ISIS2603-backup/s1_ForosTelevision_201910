package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.UsuarioPersistence;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author bs.rincon
 */
@Stateless
public class UsuarioLogic {
    @Inject
    private UsuarioPersistence usuarioPersistence;

    @Inject
    ProductoraLogic productoraLogic;
    
    @Inject
    EmisionLogic emisionLogic; 

    /**
     * Verifica la creacion de un usuario
     *
     * @param usuario entidad de usuario
     * @return usuarioRegistrado
     * @throws BusinessLogicException no cumple con caracteristicas de negocio
     */
    public UsuarioEntity crearUsuario(UsuarioEntity usuario) throws BusinessLogicException {
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        String pasPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
        Pattern patternpas = Pattern.compile(pasPattern);
        Pattern pattern = Pattern.compile(emailPattern);
        if (usuario.getNombre() == null) {
            throw new BusinessLogicException("Debe registrase con un nombre");
        }
        //Usuario con nombre unico
        if (usuarioPersistence.findName(usuario.getNombre()).size() > 0) {
            throw new BusinessLogicException("Ya existe un usuario con el mismo nombre");
        }
        // Usuario con email unico
        if (usuarioPersistence.findEmail(usuario.getEmail()).size() > 0) {
            throw new BusinessLogicException("Ya existe un usuario con el mismo email");
        }
        // Usuario con email valido
        if (usuario.getEmail() == null) {
            throw new BusinessLogicException("Debe ingresar un email valido");
        }
        // Verificacion de email valido
        Matcher matcher = pattern.matcher(usuario.getEmail());
        if (!matcher.matches()) {
            throw new BusinessLogicException("Debe ingresar un email valido");
        }
        // Verificacion longitud clave
        if (usuario.getClave() == null) {
            throw new BusinessLogicException("Debe ingresar una clave");
        }
        // Verificacion validez clave mayor a 8 digitos con mayusculas y minusculas
        Matcher matcherpas = patternpas.matcher(usuario.getClave());
        if (!matcherpas.matches()) {
            throw new BusinessLogicException("Debe ingresar una clave  valida");
        }
        // Verifica la integridad de los roles de usuario
        if (usuario.getPrivilegio() == null) {
            throw new BusinessLogicException("Debe seleccionar algun rol");
        }
        if (!((usuario.getPrivilegio().ordinal() == UsuarioEntity.Acceso.ADMINISTRADOR.ordinal()) ||
                (usuario.getPrivilegio().ordinal() == UsuarioEntity.Acceso.USUARIO.ordinal()))) {
            throw new BusinessLogicException("Debe seleccionar un rol valido");
        }
        usuarioPersistence.create(usuario);
        return usuario;
    }

    /**
     * Relaciona un usuario con sus seguidores
     *
     * @param idUser        identificador de usuario
     * @param idUserSeguido identificador de seguido
     * @throws BusinessLogicException si el usuario no existe o si el usuario seguido no existe
     */
    public void seguirUsuario(Long idUser, Long idUserSeguido) throws BusinessLogicException {
        UsuarioEntity userMaster = usuarioPersistence.find(idUser);
        UsuarioEntity userSeguido = usuarioPersistence.find(idUserSeguido);
        if (userMaster == null) {
            throw new BusinessLogicException("Debe seguir un usuario valido");
        }
        if (userSeguido == null) {
            throw new BusinessLogicException("Debe seguir un usuario valido");
        }
        if (Long.compare(userMaster.getId(), userSeguido.getId()) == 0) {
            throw new BusinessLogicException("No puede seguirse a si mismo");
        }
        userMaster.getSeguidos().add(userSeguido);
        usuarioPersistence.update(userMaster);
    }

    /**
     * Lista de todos los usuarios
     *
     * @return usuarios registrados
     * @throws BusinessLogicException si no hay usuario registrados
     */
    public List<UsuarioEntity> getAll() throws BusinessLogicException {
        List<UsuarioEntity> users = usuarioPersistence.getAll();
        if (users.isEmpty()) throw new BusinessLogicException("No hay usuarios registrados");
        return users;
    }

    /**
     * Encuentra un usuario
     *
     * @param user identificador usuario
     * @return usuario
     * @throws BusinessLogicException no existe el usuario
     */
    public UsuarioEntity find(Long user) throws BusinessLogicException {
        UsuarioEntity u = usuarioPersistence.find(user);
        if (u == null) throw new BusinessLogicException("No existe el usuario");
        return u;
    }

    /**
     * actualiza informacion
     *
     * @param entity actualiza la informacion de usuario
     * @return instancia actualizada
     */

    public UsuarioEntity update(UsuarioEntity entity) {
        return usuarioPersistence.update(entity);
    }

    public void delete(Long id) {
        usuarioPersistence.delete(id);
    }

    /**
     * Verifica las credenciales de un usuario
     *
     * @param email email registrado
     * @param pass  contreseña generada
     * @return Informacion de usuario
     * @throws BusinessLogicException error en validacion de credenciales
     */

    public UsuarioEntity login(String email, String pass) throws BusinessLogicException {
        List<UsuarioEntity> lista=usuarioPersistence.findEmail(email);
        if(lista.isEmpty()) throw new BusinessLogicException("Email no registrado");
        UsuarioEntity l = lista.get(0);
        if (l == null) throw new BusinessLogicException("Email no registrado");
        if (l.getClave().compareTo(pass) != 0) {
            throw new BusinessLogicException("Email o contraseña incorrectas");
        }
        return l;
    }

    public List<UsuarioEntity> usuariosSeguidos(Long userId) {
        UsuarioEntity user = usuarioPersistence.find(userId);
        return user.getSeguidos();
    }

    /**
     * Registra la productora favorita de un usuario;
     *
     * @param idUsuario    identificador usuario
     * @param idProductora identificador produccion
     * @throws BusinessLogicException no existe el usuario o productora
     */
    public void registrarProductoraFavorito(Long idUsuario, Long idProductora) throws BusinessLogicException {
        UsuarioEntity user = this.find(idUsuario);
        ProductoraEntity produccion = productoraLogic.find(idProductora);
        user.getProductorasFav().add(produccion);
        this.update(user);
    }

    /**
     * Elimina una productora favorita
     *
     * @param user         identificador usuario
     * @param idProduccion indentificador produccion
     * @throws BusinessLogicException si la productora no existe o el usuario
     */
    public void eliminarProductoraFavorito(Long user, Long idProduccion) throws BusinessLogicException {
        UsuarioEntity us = this.find(user);
        List<ProductoraEntity> ls = us.getProductorasFav();
        boolean estado=false;
        for (ProductoraEntity cl : ls) {
            if (Objects.equals(cl.getId(), idProduccion)) {
                ls.remove(cl);
                estado=true;
            }
        }
        if(!estado) throw new BusinessLogicException("Recurso de produccion no eliminado");
        this.update(us);
    }
    /**
     * Lista de emisiones programadas del usuario
     * @param user indentificador de usuario
     * @return lista de emisiones
     * @throws BusinessLogicException el usuario no esta registrado 
     */
    public List<EmisionEntity> darEmisiones(Long user) throws BusinessLogicException{
        List<EmisionEntity> dat= this.find(user).getParrilla();
        if(dat.isEmpty()) throw  new BusinessLogicException("No hay Emsiones registradas");
     return dat;
    }
    
    /**
     * Elimina una emision del usuario
     * @param user usuario
     * @param emisionId identificador de emision
     * @throws BusinessLogicException si el usuario no existe
     */
    public void eliminarEmision(Long user, Long emisionId) throws BusinessLogicException{
     UsuarioEntity us = this.find(user);
        List<EmisionEntity> ls = us.getParrilla();
        boolean find=false;
        for (EmisionEntity cl : ls) {
            if (Objects.equals(cl.getId(), emisionId)) {
                ls.remove(cl);
                find=true;
            }
        }
        if(!find) throw  new BusinessLogicException("No existe el recurso de emision");
        this.update(us);
    }
    /**
     * Registra un horario de emision para el usuario
     * @param user identificador de usuario
     * @param emisionId identificador de emision
     * @throws BusinessLogicException  si no encuentra el usuario o la emision
     */
    public void registrarEmision(Long user, Long emisionId)throws BusinessLogicException{
        UsuarioEntity us = this.find(user);
        us.getParrilla().add(emisionLogic.getEmisionPorId(emisionId));
        this.update(us);
    }

    /**
     * Dar productoras favoritas
     *
     * @param id Id de producciones
     * @return
     * @throws BusinessLogicException
     */
    public List<ProductoraEntity> darProductoraFavoritas(Long id) throws BusinessLogicException {
         List<ProductoraEntity> dat=this.find(id).getProductorasFav();
         if(dat.isEmpty()) throw  new BusinessLogicException("No hay producciones registradas");
        return dat;
    }

    /**
     * Retorna una lista de usuarios seguidos
     *
     * @param id usuario principal
     * @return lista de usuarios seguidos
     * @throws BusinessLogicException
     */
    public List<UsuarioEntity> darUsuariosFavoritos(Long id) throws BusinessLogicException {
        List<UsuarioEntity>  data=this.find(id).getSeguidos();
         if(data.isEmpty()) throw  new BusinessLogicException("No hay usuarios favoritos");
        return data;
    }
}
