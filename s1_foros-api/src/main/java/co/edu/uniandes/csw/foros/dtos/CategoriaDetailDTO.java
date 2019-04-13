/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.CategoriaEntity;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jhonattan Fonseca.
 */
public class CategoriaDetailDTO extends CategoriaDTO implements Serializable {

    private List<ProduccionDTO> producciones;

    public CategoriaDetailDTO() {
        super();
    }

    public CategoriaDetailDTO(CategoriaEntity entity) {
        super(entity);
        if (entity != null) {
            producciones = new ArrayList<>();
            for (ProduccionEntity entityProduccion : entity.getProduccciones()) {
                producciones.add(new ProduccionDTO(entityProduccion));
            }
        }
    }

    /**
     * Retorna la lista de producciones de la categoria.
     *
     * @return Lista de producciones d el acategoria.
     */
    public List<ProduccionDTO> getProducciones() {
        return producciones;
    }

    /**
     * Remplaza la lista de producciones de la categoria, por la lista dada.
     *
     * @param nuevasProducciones lista dada de nuevas producciones.
     */
    public void setProducciones(List<ProduccionDTO> nuevasProducciones) {
        this.producciones = nuevasProducciones;
    }

    
    /**
     * Convierte un objeto CategoriaDTO a CAtegoriaEntity incluyendo sus atributos
     * de CategoriaDTO.
     * 
     * @return Nuevo Objeto de CategoriaEntity.
     */
    @Override
    public CategoriaEntity toEntity() {
        CategoriaEntity categoriaEntity = super.toEntity();
        if (producciones != null) {
            List<ProduccionEntity> produccionesEntity = new ArrayList<>();
            for (ProduccionDTO dto : producciones) {
                produccionesEntity.add(dto.toEntity());
            }

            categoriaEntity.setProducciones(produccionesEntity);
        }
        return categoriaEntity;
    }

}
