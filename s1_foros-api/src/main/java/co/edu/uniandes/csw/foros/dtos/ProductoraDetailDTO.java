package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Jhonattan Fonseca.
 */
public class ProductoraDetailDTO extends ProductoraDTO implements Serializable {

    private List<ProduccionDTO> producciones;

    public ProductoraDetailDTO()
    {
        super();
    }
    
    public ProductoraDetailDTO(ProductoraEntity entity)
    {
        super(entity);
        if( entity != null)
        {
            producciones = new ArrayList<>();
            for( ProduccionEntity entityProduccion: entity.getProduccciones()){
                producciones.add( new ProduccionDTO(entityProduccion));
            }
        }
    }
    
    
    /**
     * Retorna la lista de produciones de la producciones.
     *
     * @return
     */
    public List<ProduccionDTO> getProducciones() {
        return producciones;
    }

    /**
     * Sobreescribe la lista de producciones de la producciones.
     *
     * @param producciones
     */
    public void setProducciones(List<ProduccionDTO> producciones) {
        this.producciones = producciones;
    }

     /**
     * Convierte un objeto ProductoraDTO a ProductoraEntity incluyendo los
     * atributos de ProductoraDTO.
     *
     * @return Nueva objeto ProductoraEntity.
     *
     */
    @Override
    public ProductoraEntity toEntity() {
        ProductoraEntity productoraEntity = super.toEntity();
        if (producciones != null) {
            List<ProduccionEntity> produccionEntity = new ArrayList<>();
            for (ProduccionDTO dtoProduccion : producciones) {
                produccionEntity.add(dtoProduccion.toEntity());
            }
            productoraEntity.setProducciones(produccionEntity);
        }
        
        return productoraEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
