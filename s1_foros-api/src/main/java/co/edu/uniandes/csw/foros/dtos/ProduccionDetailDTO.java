package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que se encarga de las relaciones múltiples de una producción.
 *
 * @author jf.castaneda
 */
public class ProduccionDetailDTO extends ProduccionDTO {

    /**
     * Relación de producción con su staff.
     */
    private List<StaffDTO> staff;

    /**
     * Relación de producción con sus categorías.
     */
    private List<CategoriaDTO> categorias;

    /**
     * Relación de producción con sus capítulos.
     */
    private List<CapituloDTO> capitulos;

    /**
     * Relación de producción con sus emisiones.
     */
    private List<EmisionDTO> emisiones;

    /**
     * Relación de producción con sus reseñas.
     */
    private List<ResenaDTO> resenas;

    /**
     * Constructor vacío de la clase.
     */
    public ProduccionDetailDTO() {
        super();
    }

    /**
     * Constructor que recibe una entidad por parámetro.
     *
     * @param produccionEntity entidad de la producción.
     */
    public ProduccionDetailDTO(ProduccionEntity produccionEntity) {
        super(produccionEntity);
        if (produccionEntity.getStaff() != null) {
            staff = new ArrayList<>();
            produccionEntity.getStaff().forEach((staffEntity) -> {
                staff.add(new StaffDTO(staffEntity));
            });
        }
        if (produccionEntity.getCategorias() != null) {
            categorias = new ArrayList<>();
            produccionEntity.getCategorias().forEach((categoriaEntity) -> {
                categorias.add(new CategoriaDTO(categoriaEntity));
            });
        }
        if (produccionEntity.getCapitulos() != null) {
            capitulos = new ArrayList<>();
            produccionEntity.getCapitulos().forEach((capituloEntity) -> {
                capitulos.add(new CapituloDTO(capituloEntity));
            });
        }
        if(produccionEntity.getEmisiones() != null) {
            emisiones = new ArrayList<>();
            produccionEntity.getEmisiones().forEach((emisionEntity) -> {
                emisiones.add(new EmisionDTO((emisionEntity)));
            });
        }
        if(produccionEntity.getResenas() != null) {
            resenas = new ArrayList<>();
            produccionEntity.getResenas().forEach((resenaEntity) -> {
                resenas.add(new ResenaDTO(resenaEntity));
            });
        }
    }

    /**
     * Método que convierte el DTO en una entidad.
     *
     * @return la entidad de la producción.
     */
    @Override
    public ProduccionEntity toEntity() {
        ProduccionEntity produccionEntity = super.toEntity();
        if(staff != null) {
            List<StaffEntity> staffEntity = new ArrayList<>();
            staff.forEach((staffDTO) -> {
                staffEntity.add(staffDTO.toEntity());
            });
            produccionEntity.setStaff(staffEntity);
        }
        if(categorias != null) {
            List<CategoriaEntity> categoriasEntity = new ArrayList<>();
            categorias.forEach((categoriaDTO) -> {
                categoriasEntity.add(categoriaDTO.toEntity());
            });
            produccionEntity.setCategorias(categoriasEntity);
        }
        if(capitulos != null) {
            List<CapituloEntity> capitulosEntity = new ArrayList<>();
            capitulos.forEach(capituloDTO -> {
                capitulosEntity.add(capituloDTO.toEntity());
            });
            produccionEntity.setCapitulos(capitulosEntity);
        }
        if(emisiones != null) {
            List<EmisionEntity> emisionesEntity = new ArrayList<>();
            emisiones.forEach(emisionDTO -> {
                emisionesEntity.add(emisionDTO.toEntity());
            });
            produccionEntity.setEmisiones(emisionesEntity);
        }
        if(resenas != null) {
            List<ResenaEntity> resenasEntity = new ArrayList<>();
            resenas.forEach(resenaDTO -> {
                resenasEntity.add(resenaDTO.toEntity());
            });
            produccionEntity.setResenas(resenasEntity);
        }
        return produccionEntity;
    }

    /**
     * Getter del staff.
     *
     * @return staff de la producción.
     */
    public List<StaffDTO> getStaff() {
        return staff;
    }

    /**
     * Setter del staff.
     *
     * @param staff nuevo staff.
     */
    public void setStaff(List<StaffDTO> staff) {
        this.staff = staff;
    }

    /**
     * Getter de las categorías.
     *
     * @return categorías de la producción.
     */
    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    /**
     * Setter de las categorías.
     *
     * @param categorias nuevas categorías.
     */
    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    /**
     * Getter de los capítulos.
     *
     * @return capítulos de la producción.
     */
    public List<CapituloDTO> getCapitulos() {
        return capitulos;
    }

    /**
     * Setter de los capítulos.
     *
     * @param capitulos nuevos capítulos.
     */
    public void setCapitulos(List<CapituloDTO> capitulos) {
        this.capitulos = capitulos;
    }

    /**
     * Getter de las emisiones.
     *
     * @return emisiones de la producción.
     */
    public List<EmisionDTO> getEmisiones() {
        return emisiones;
    }

    /**
     * Setter de las emisiones.
     *
     * @param emisiones nuevas emisiones.
     */
    public void setEmisiones(List<EmisionDTO> emisiones) {
        this.emisiones = emisiones;
    }

    /**
     * Getter de las reseñas.
     *
     * @return reseñas de la producción.
     */
    public List<ResenaDTO> getResenas() {
        return resenas;
    }

    /**
     * Setter de las reseñas.
     *
     * @param resenas nuevas reseñas.
     */
    public void setResenas(List<ResenaDTO> resenas) {
        this.resenas = resenas;
    }
}
