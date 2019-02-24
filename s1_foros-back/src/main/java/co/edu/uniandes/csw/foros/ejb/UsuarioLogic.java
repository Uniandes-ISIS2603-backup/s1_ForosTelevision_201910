package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.UsuarioPersistence;
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
        String pasPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
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
            throw new BusinessLogicException("Debe ingresar una clave  valida");
        }
        Matcher matcherpas = patternpas.matcher(usuario.getClave());
        if (!matcherpas.matches()){
            throw new BusinessLogicException("Debe ingresar una clave  valida");
        }
        if (usuario.getPrivilegio()==null){
            throw new BusinessLogicException("Debe seleccionar un rol valido");
        }
        if(!(usuario.getPrivilegio()==(UsuarioEntity.Acceso.ADMINISTRADOR) ||
                usuario.getPrivilegio()==(UsuarioEntity.Acceso.USUARIO))){
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
    
}
