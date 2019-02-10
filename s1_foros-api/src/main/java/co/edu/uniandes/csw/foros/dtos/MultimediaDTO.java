package co.edu.uniandes.csw.foros.dtos;

import java.io.Serializable;

/**
 * Clase que representa los recursos multimedia asociados a una produccion
 */
public class MultimediaDTO implements Serializable {

    private Long id;
    private String portada;
    private String videos;
    private String imagenes;

    public MultimediaDTO(){

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
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getImagenes() {
        return imagenes;
    }

    public void setImagenes(String imagenes) {
        this.imagenes = imagenes;
    }
}
