/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.resources;

/**
 *
 * @author mi.carrascal
 */
  
import co.edu.uniandes.csw.foros.dtos.UsuarioDTO;
import co.edu.uniandes.csw.foros.ejb.CanalLogic;
import co.edu.uniandes.csw.foros.ejb.EmisionLogic;
import co.edu.uniandes.csw.foros.ejb.ResenaUsuarioLogic;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;


public class ResenaUsuarioResource {
    
    @Inject
    private EmisionLogic emLogic;
    
    @Inject
    private CanalLogic canLogic;
    
    @Inject
    private ResenaUsuarioLogic relacionLogic;

    
     @GET
    public UsuarioDTO darUsuario(Long idCanal)
    {
        UsuarioEntity usuario=relacionLogic.darUsuarioResena(idCanal);
        UsuarioDTO usuarioDTO=new UsuarioDTO(usuario);
        
        return usuarioDTO;
        
    }
    
}
