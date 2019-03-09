/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.dtos;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.StaffEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que se encarga de las relaciones múltiples del staff.
 *
 * @author jf.castaneda
 */
public class StaffDetailDTO extends StaffDTO {

    /**
     * Relación con cero o más producciones.
     */
    private List<ProduccionDTO> producciones;

    /**
     * Constructor vacío de la clase.
     */
    public StaffDetailDTO() {
        super();
    }

    /**
     * Constructor que recibe una entidad por parámetro.
     *
     * @param staffEntity entidad del staff.
     */
    public StaffDetailDTO(StaffEntity staffEntity) {
        super(staffEntity);
        if (staffEntity.getProducciones() != null) {
            producciones = new ArrayList<>();
            staffEntity.getProducciones().forEach((produccionEntity) -> {
                producciones.add(new ProduccionDTO(produccionEntity));
            });
        }
    }

    /**
     * Método que transforma el DTO en una entidad.
     *
     * @return entidad del staff.
     */
    @Override
    public StaffEntity toEntity() {
        StaffEntity staffEntity = super.toEntity();
        if (producciones != null) {
            List<ProduccionEntity> produccionesEntity = new ArrayList<>();
            producciones.forEach((produccionDTO) -> {
                produccionesEntity.add(produccionDTO.toEntity());
            });
            staffEntity.setProducciones(produccionesEntity);
        }
        return staffEntity;
    }

    /**
     * Getter de las producciones.
     *
     * @return producciones del miembro del staff.
     */
    public List<ProduccionDTO> getProducciones() {
        return producciones;
    }

    /**
     * Setter de las producciones.
     *
     * @param producciones nuevas producciones.
     */
    public void setProducciones(List<ProduccionDTO> producciones) {
        this.producciones = producciones;
    }
}
