package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.DiaEntity;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
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
    private ProduccionDTO produccion;
    
    /**
     * Días de la emisión.
     */
    private List<DiaDTO> dias;
   
    /**
     * Canal de la emisión.
     */
    private CanalDTO canal;
    
    /**
     * Constructor de la clase.
     */
    public EmisionDetailDTO(){
        super();
    }
    
    public EmisionDetailDTO(EmisionEntity entity){
        super(entity);
        if(entity!=null){
            if(entity.getCanal()!=null){
                this.canal = new CanalDTO(entity.getCanal());
            }
            if(!entity.getDias().isEmpty()){
                for(DiaEntity dia : entity.getDias()){
                    this.dias.add(new DiaDTO(dia));
                }
            }
            if(entity.getProduccion()!=null){
                    this.produccion = new ProduccionDTO(entity.getProduccion());
            }          
        }
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public EmisionEntity toEntity(){
        EmisionEntity entity = super.toEntity();
        if(!this.getDias().isEmpty()){
            for(DiaDTO dia : this.getDias()){
                entity.getDias().add(dia.toEntity());
            }
        }
        if(this.produccion!=null){
            entity.setProduccion(produccion.toEntity());
        }
        if(this.getCanal()!=null){
            entity.setCanal(this.getCanal().toEntity());
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

    /**
     * @return the canal
     */
    public CanalDTO getCanal() {
        return canal;
    }

    /**
     * @param canal the canal to set
     */
    public void setCanal(CanalDTO canal) {
        this.canal = canal;
    }
}
