package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jhonattan Fonseca.
 */
public class ProductoraDetailDTO extends ProductoraDTO implements Serializable {

    private List<ProduccionDTO> producciones;

    public ProductoraDetailDTO(ProductoraEntity entity)
    {
        super(entity);
        if(entity.getProducciones() != null)
        {
            producciones = new ArrayList<>();
            entity.getProducciones().forEach((produccionEntity) ->
            {
              producciones.add(new ProduccionDTO(produccionEntity));
            });
        }
    }
    
    
    /**
     * Retorna la lista de produciones de la productora.
     *
     * @return
     */
    public List<ProduccionDTO> getProducciones() {
        return producciones;
    }

    /**
     * Sobreescribe la lista de producciones de la productora.
     *
     * @param producciones
     */
    public void setProducciones(List<ProduccionDTO> producciones) {
        this.producciones = producciones;
    }

}
