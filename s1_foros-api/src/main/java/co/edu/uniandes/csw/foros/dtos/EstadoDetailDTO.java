package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.EstadoEntity;
import java.io.Serializable;

/**
 *
 * @author ne.ortega
 */
public class EstadoDetailDTO extends EstadoDTO implements Serializable{
    
    /**
     * Produccion de este estado.
     */
    private ProduccionDTO produccion;
    
    /**
     * Constructor del Estado.
     */
    public EstadoDetailDTO(){
        super();
    }
    
    public EstadoDetailDTO(EstadoEntity entity){
        super(entity);
        if(entity!=null){
            if(entity.getProduccion()!=null){
                this.produccion = new ProduccionDTO(entity.getProduccion());
            }
        }
    }
    
    @Override
    public EstadoEntity toEntity(){
        EstadoEntity entity = super.toEntity();
        if(this.produccion!=null){
            entity.setProduccion(this.produccion.toEntity());
        }
        return entity;
    }

    /**
     * @return the producciones
     */
    public ProduccionDTO getProduccion() {
        return produccion;
    }

    /**
     * @param produccion the producciones to set
     */
    public void setProduccion(ProduccionDTO produccion) {
        this.produccion = produccion;
    }
}
