package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author bsrincon
 */
@Entity
public class MultimediaEntity extends BaseEntity implements Serializable{
    
    private String portada;
    
     /**
     * Lista de video asociado
     */
    private String video;
     
    /**
     * Galeria de imagenes asociadas
     */
    @PodamExclude
    @OneToMany(fetch=LAZY)
    private List<ArchivoEntity> imagenes; 
    
  
    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    /**
     * @return the videos
     */
    public String getVideos() {
        return video;
    }

    /**
     * @param videos the videos to set
     */
    public void setVideos(String videos) {
        this.video = videos;
    }

    /**
     * @return the imagenes
     */
    public List<ArchivoEntity> getImagenes() {
        return imagenes;
    }

    /**
     * @param imagenes the imagenes to set
     */
    public void setImagenes(List<ArchivoEntity> imagenes) {
        this.imagenes = imagenes;
    }
    
}
