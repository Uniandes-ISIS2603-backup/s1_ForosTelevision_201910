package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.ArchivoEntity;
import java.io.Serializable;
/**
 *
 * @author bs.rincon
 */
public class ArchivoDTO implements Serializable{
    
    private Long id;
    private String url;
    
    public ArchivoDTO(){}
    
    public ArchivoDTO(ArchivoEntity ent){
           this.id=ent.getId();
           this.url=ent.getUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
