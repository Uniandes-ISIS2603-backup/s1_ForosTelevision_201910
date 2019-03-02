package co.edu.uniandes.csw.foros.dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ne.ortega
 */
public class EmisionDetailDTO extends EmisionDTO implements Serializable {
    
    /**
     * Producciones de esta emisión.
     */
    private List<ProduccionDTO> producciones;
    
    /**
     * Días de la emisión.
     */
    private List<DiaDTO> dias;
    
    /**
     * Constructor de la clase.
     */
    public EmisionDetailDTO(){
        
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

    /**
     * @return the dias
     */
    public List<DiaDTO> getDias() {
        return dias;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(List<DiaDTO> dias) {
        this.dias = dias;
    } 
}
