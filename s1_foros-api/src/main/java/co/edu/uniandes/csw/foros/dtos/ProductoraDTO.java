package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Jhonattan Fonseca
 */
public class ProductoraDTO implements Serializable {

    private Long id;
    private String nombre;

    /**
     * Constructor por defecto.
     */
    public ProductoraDTO() {

    }

    public ProductoraDTO(ProductoraEntity productoraEntity) {
        if (productoraEntity != null) {
            this.id = productoraEntity.getId();
            this.nombre = productoraEntity.getNombre();
        }
    }

    /**
     *
     * @return el id de una Productora.
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el id de la productora.
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de la productora.
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la productora.
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Convierte un DTO a Entity
     *
     * @return Un entity con los valores del DTO.
     */
    public ProductoraEntity toEntity() {
        ProductoraEntity productoraEntity = new ProductoraEntity();
        productoraEntity.setId(this.id);
        productoraEntity.setNombre(this.nombre);
        return productoraEntity;
    }

    
   
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
