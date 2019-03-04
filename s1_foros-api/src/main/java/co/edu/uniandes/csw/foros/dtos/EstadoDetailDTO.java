package co.edu.uniandes.csw.foros.dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ne.ortega
 */
public class EstadoDetailDTO extends EstadoDTO implements Serializable{
    
    /**
     * Producciones de este estado.
     */
    private List<ProduccionDTO> producciones;
    
    /**
     * Constructor del Estado.
     */
    public EstadoDetailDTO(){
        
    }

    /**
     * @return the producciones
     */
    public List<ProduccionDTO> getProducciones() {
        return producciones;
    }

    /**
     * @param producciones the producciones to set
     */
    public void setProducciones(List<ProduccionDTO> producciones) {
        this.producciones = producciones;
    }
}
