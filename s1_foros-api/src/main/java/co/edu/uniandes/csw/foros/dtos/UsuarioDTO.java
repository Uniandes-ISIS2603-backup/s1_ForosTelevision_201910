package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que represnta los usuarios en la plataforma
 */
public class UsuarioDTO implements Serializable {

    

    private Long id;
    private String nombre;
    private String email;  
    private String clave;
    private UsuarioEntity.Acceso privilegio;
    
    /**
     * Constructor de serialzacion
     */
    public UsuarioDTO(){}
    
    public UsuarioDTO(UsuarioEntity user){
        this.id=user.getId();
        this.email=user.getEmail();
        this.clave=user.getClave();
        this.privilegio=user.getPrivilegio();
        this.nombre=user.getNombre();
    }

    public UsuarioEntity toEntity(){
        UsuarioEntity user=new UsuarioEntity();
        user.setId(getId());
        user.setClave(getClave());
        user.setEmail(getEmail());
        user.setPrivilegio(getPrivilegio());
        user.setNombre(getNombre());
        return user;
    }
    
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public UsuarioEntity.Acceso getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(UsuarioEntity.Acceso privilegio) {
        this.privilegio = privilegio;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
