/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.StaffEntity;
import co.edu.uniandes.csw.foros.enums.RolStaff;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
import co.edu.uniandes.csw.foros.persistence.StaffPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;

/**
 * Clase de un miembro del staff dentro de la capa de lógica.
 *
 * @author jf.castaneda
 */
public class StaffLogic {

    private static final Logger LOGGER = Logger.getLogger(StaffLogic.class.getName());

    /**
     * Inyección de objeto que trata la persistencia del staff.
     */
    @Inject
    private StaffPersistence staffPersistence;

    /**
     * Inyección de un objeto que trata la persistencia de las producciones.
     */
    @Inject
    private ProduccionPersistence produccionPersistence;

    /**
     * Patrón de una imagen.
     */
    private static final Pattern RUTA_IMAGENES = Pattern.compile("([^\\s]+(\\.(?i)(png|jpg))$)");

    /**
     * Método que valida que la ruta de una imagen sea correcta.
     *
     * @param url ruta de la imagen a agregar.
     * @return true si es correcta, false si no.
     */
    private boolean rutaImagenCorrecta(String url) {
        Matcher mtch = RUTA_IMAGENES.matcher(url);
        return mtch.matches();
    }

    /**
     * Método que busca un miembro del staff según su id.
     *
     * @param id id del miembro del staff a ser buscado.
     * @return entidad del staff, o null si no existe.
     */
    public StaffEntity darStaff(Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de búsqueda de un miembro del staff con id " + id);
        if(id == null) {
            throw new BusinessLogicException("El id del staff debe existir");
        }
        return staffPersistence.find(id);
    }

    public List<StaffEntity> darTodosStaff() {
        LOGGER.log(Level.INFO, "Inicia proceso de búsqueda de los miembros del staff");
        return staffPersistence.getAll();
    }

    /**
     * Método que se asegura que una entidad de staff tenga lo necesario para
     * ser creada, para después crearla.
     *
     * @param staffEntity la entidad del staff a crear
     * @return la entidad que entró por parámetro.
     * @throws BusinessLogicException cuando alguno de los atributos de la
     * entidad que entra parámetro cumple con alguna de las siguientes
     * propiedades: rol = null | nombre = (null | "") | foto = null | foto no
     * sigue los estándares de RUTA_IMAGENES | descripción = (null | "") |
     * producciones = null.
     */
    public StaffEntity crearStaff(StaffEntity staffEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del miembro del staff.");
        // Validación atributo rol.
        if (staffEntity.getRol() == null) {
            throw new BusinessLogicException("El rol de un miembro del staff debe existir.");
        }
        // Validación atributo nombre.
        if (staffEntity.getNombre() == null) {
            throw new BusinessLogicException("El nombre del miembro del staff debe existir.");
        }
        if (staffEntity.getNombre().equals("")) {
            throw new BusinessLogicException("El nombre de un miembro del Staff no puede ser vacío.");
        }
        // Validación atributo foto.
        if (staffEntity.getFoto() == null) {
            throw new BusinessLogicException("La ruta de la foto del miembro del staff debe existir.");
        }
        if (!rutaImagenCorrecta(staffEntity.getFoto())) {
            throw new BusinessLogicException("La ruta de la foto no coincide con la ruta de una imagen.");
        }
        // Validación atributo descripción.
        if (staffEntity.getDescripcion() == null) {
            throw new BusinessLogicException("La descripción del miembro del staff debe existir.");
        }
        if (staffEntity.getDescripcion().equals("")) {
            throw new BusinessLogicException("La descripción del miembro del staff no puede ser vacía.");
        }
        // Validación relación con producción.
        if (staffEntity.getProducciones() == null) {
            throw new BusinessLogicException("Las producciones de un miembro del staff deben exisitir.");
        }
        staffPersistence.create(staffEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del miembro del staff");
        return staffEntity;
    }

    /**
     * Método que edita un staff.
     *
     * @param idStaff id del staff a modificar.
     * @param nuevaStaffEntity entidad del staff con la nueva información.
     * @return entidad modificada.
     * @throws BusinessLogicException cuando algún atributo es erróneo.
     */
    public StaffEntity editarStaff(Long idStaff, StaffEntity nuevaStaffEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el staff con id = {0}", idStaff);
        if(idStaff == null) {
            throw new BusinessLogicException("El id del staff a actualizar debe existir");
        }
        if (nuevaStaffEntity.getRol() == null) {
            throw new BusinessLogicException("El nuevo rol no existe");
        }
        if (nuevaStaffEntity.getNombre() == null) {
            throw new BusinessLogicException("El nuevo nombre no existe");
        }
        if (nuevaStaffEntity.getNombre().equals("")) {
            throw new BusinessLogicException("El nuevo nombre es vacío");
        }
        if (nuevaStaffEntity.getDescripcion() == null) {
            throw new BusinessLogicException("La nueva descripcion no existe");
        }
        if (nuevaStaffEntity.getDescripcion().equals("")) {
            throw new BusinessLogicException("La nueva descripcion es vacía");
        }
        if (nuevaStaffEntity.getFoto() == null) {
            throw new BusinessLogicException("La nueva foto no existe");
        }
        if (!rutaImagenCorrecta(nuevaStaffEntity.getFoto())) {
            throw new BusinessLogicException("La nueva foto es vacía");
        }
        if(nuevaStaffEntity.getProducciones() == null) {
            throw new BusinessLogicException("Las nuevas producciones no existen");
        }
        StaffEntity staffEntity = staffPersistence.update(nuevaStaffEntity);
        return staffEntity;
    }

    /**
     * Método que elimina un miembro del staff.
     *
     * @param idStaff id del staff a eliminar.
     * @return entidad del staff que se eliminó.
     * @throws BusinessLogicException si no existe un miembro del staff con ese
     * id.
     */
    public StaffEntity eliminarStaff(Long idStaff) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminación del miembro del staff.");
        StaffEntity staffEntity = staffPersistence.find(idStaff);
        if (staffEntity == null) {
            throw new BusinessLogicException("No existe un miembro del staff con ese id.");
        }
        staffPersistence.delete(idStaff);
        LOGGER.log(Level.INFO, "Termina proceso de eliminación del miembro del staff.");
        return staffEntity;
    }
}
