package co.edu.uniandes.csw.foros.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author Jhonattan Fonseca.
 */
@Entity
public class ProductoraEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToMany(fetch=LAZY,mappedBy = "productora")
    private List<ProduccionEntity> producciones;

    private String nombre;

    /**
     *
     * @return El nombre de la productora.
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
     *
     * @return Las producciones de un productora.
     */
    public List<ProduccionEntity> getProduccciones() {
        return producciones;
    }

    /**
     * Sobre escribe la lista de producciones de una Productora.
     *
     * @param producciones
     */
    public void setProducciones(List<ProduccionEntity> producciones) {
        this.producciones = producciones;
    }


}
