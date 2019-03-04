package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.CategoriaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class CategoriaDTO implements Serializable {

    private Long id;
    private String nombre;

    /**
     * Constructor por defecto.
     */
    public CategoriaDTO() {

    }

    public CategoriaDTO(CategoriaEntity categoria) {
        if (categoria != null) {
            this.id = categoria.getId();
            this.nombre = categoria.getNombre();
        }
    }

    /**
     * Retorna el id de la categoria.
     *
     * @return el id de la categoria.
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el id de la categoria.
     *
     * @param id nuevo id de la categoria.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el nombre de la categoria.
     *
     * @return el nombre de la categoria.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la categoria.
     *
     * @param nombre el nuevo nombre de la categoria.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaEntity toEntity() {

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(this.id);
        categoriaEntity.setNombre(this.nombre);
        return categoriaEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
