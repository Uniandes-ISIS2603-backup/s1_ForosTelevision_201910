package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.UsuarioPersistence;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;
/**
 *
 * @author bs.rincon
 */
@Stateless 
public class UsuarioLogic {
    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    public UsuarioEntity crearUsuario(UsuarioEntity usuario)throws BusinessLogicException{
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        String pasPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
        Pattern patternpas = Pattern.compile(pasPattern);
        Pattern pattern = Pattern.compile(emailPattern);
        if(usuario.getNombre()==null){
            throw new BusinessLogicException("Debe registrase con un nombre");
        }
        if(usuarioPersistence.findName(usuario.getNombre()).size()>0){
            throw new BusinessLogicException("Ya existe un usuario con el mismo nombre");
        }
         if(usuarioPersistence.findEmail(usuario.getEmail()).size()>0){
            throw new BusinessLogicException("Ya existe un usuario con el mismo email");
        }
       
        if (usuario.getEmail()==null){
            throw new BusinessLogicException("Debe ingresar un email valido");
        }
         Matcher matcher = pattern.matcher(usuario.getEmail());
        if (!matcher.matches()){
            throw new BusinessLogicException("Debe ingresar un email valido");
        }
       
        if (usuario.getClave()==null){
            throw new BusinessLogicException("Debe ingresar una clave");
        }
        Matcher matcherpas = patternpas.matcher(usuario.getClave());
        if (!matcherpas.matches()){
            throw new BusinessLogicException("Debe ingresar una clave  valida");
        }
        if (usuario.getPrivilegio()==null){
            throw new BusinessLogicException("Debe seleccionar algun rol");
        }
        if(!(( usuario.getPrivilegio().ordinal()==UsuarioEntity.Acceso.ADMINISTRADOR.ordinal()) ||
             (usuario.getPrivilegio().ordinal()==UsuarioEntity.Acceso.USUARIO.ordinal()))){
         throw new BusinessLogicException("Debe seleccionar un rol valido");
        }
        usuarioPersistence.create(usuario);
        return usuario;
    }
    
    /**
     *
     * @param idUser
     * @param idUserSeguido
     * @throws BusinessLogicException 
     */
    public void seguirUsuario(Long idUser,Long idUserSeguido)throws BusinessLogicException{
        UsuarioEntity userMaster=usuarioPersistence.find(idUser);
        UsuarioEntity userSeguido=usuarioPersistence.find(idUserSeguido);
        if(userMaster==null){
            throw new BusinessLogicException("Debe seguir un usuario valido");
        }
        if(userSeguido==null){
            throw new BusinessLogicException("Debe seguir un usuario valido");
        }
        if(Long.compare(userMaster.getId(), userSeguido.getId())==0){
            throw new BusinessLogicException("No puede seguirse a si mismo");
        }
        userMaster.getSeguidos().add(userSeguido);
        usuarioPersistence.update(userMaster);
    }
    
    public List<UsuarioEntity> getAll()throws BusinessLogicException{
        List<UsuarioEntity> users=usuarioPersistence.getAll();
        if(users.size()==0) throw new BusinessLogicException("No hay usuarios registrados");
        return users;
    }

    public UsuarioEntity find(Long user)throws BusinessLogicException
    {
        UsuarioEntity u= usuarioPersistence.find(user);
        if (u==null ) throw new BusinessLogicException("No existe el usuario");
        return u;
    }

    public UsuarioEntity update(UsuarioEntity entity){
        return usuarioPersistence.update(entity);
    }

    public void  delete(Long id){
        usuarioPersistence.delete(id);
    }

    public UsuarioEntity login(String email, String pass) throws BusinessLogicException{
        UsuarioEntity l=usuarioPersistence.findEmail(email).get(0);
        if(l==null) throw new BusinessLogicException("Email no registrado");
        if(l.getClave().compareTo(pass)!=0){
            throw new BusinessLogicException("Email o contraseña incorrectas");
        }
        return l;
    }
    
    public List<UsuarioEntity> usuariosSeguidos(Long userId){
        UsuarioEntity user=usuarioPersistence.find(userId);
        return user.getSeguidos();
    }
    
    
}
