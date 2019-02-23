package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import javax.persistence.Entity;
/**
 *
 * @author bsrincon
 */
@Entity
public class ArchivoEntity extends BaseEntity implements Serializable {
    
    private String url;
    
    public ArchivoEntity(){
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
