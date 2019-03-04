package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.ArchivoEntity;
import co.edu.uniandes.csw.foros.entities.MultimediaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa los recursos multimedia asociados a una produccion
 */
public class MultimediaDTO implements Serializable {

    private Long id;
    private String portada;
    private String video;
    private List<ArchivoDTO> imagenes;

    public MultimediaDTO(){

    }
    
    public MultimediaDTO(MultimediaEntity res){
        this.id=res.getId();
        this.portada=res.getPortada();
        this.video=res.getVideos();
        this.imagenes=new ArrayList<>();
        for(ArchivoEntity ent:res.getImagenes()){
            this.imagenes.add(new ArchivoDTO(ent));
        }
    }
    
    public MultimediaEntity toEntity(){
       MultimediaEntity ent= new MultimediaEntity();
       ent.setId(id);
       ent.setPortada(portada);
       ent.setVideos(video);
       return ent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getVideos() {
        return video;
    }

    public void setVideos(String videos) {
        this.video = videos;
    }

    public List<ArchivoDTO> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ArchivoDTO> imagenes) {
        this.imagenes = imagenes;
    }
}
