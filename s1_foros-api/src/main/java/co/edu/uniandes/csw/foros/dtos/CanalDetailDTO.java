/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.entities.CanalEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class CanalDetailDTO extends CanalDTO implements Serializable{
    private List<EmisionDTO> emisiones;

    public CanalDetailDTO()
    {
        super();
    }
    
    public CanalDetailDTO(CanalEntity entity)
    {
        super(entity);
        if( entity != null)
        {
            emisiones = new ArrayList<>();
            for( EmisionEntity entityEmision: entity.getEmisiones()){
                emisiones.add( new EmisionDTO(entityEmision));
            }
        }
    }
    
    
    /**
     * Retorna la lista de produciones de la producciones.
     *
     * @return
     */
    public List<EmisionDTO> getEmisiones() {
        return emisiones;
    }

    /**
     * Sobreescribe la lista de producciones de la producciones.
     *
     * @param producciones
     */
    public void setEmisiones(List<EmisionDTO> emisiones) {
        this.emisiones =emisiones;
    }

     /**
     * Convierte un objeto ProductoraDTO a ProductoraEntity incluyendo los
     * atributos de ProductoraDTO.
     *
     * @return Nueva objeto ProductoraEntity.
     *
     */
    @Override
    public CanalEntity toEntity() {
        CanalEntity canalEntity = super.toEntity();
        if (emisiones != null) {
            List<EmisionEntity> emisionEntity = new ArrayList<>();
            for (EmisionDTO dtoProduccion : emisiones) {
                emisionEntity.add(dtoProduccion.toEntity());
            }
            canalEntity.setEmisiones(emisionEntity);
        }
        
        return canalEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
