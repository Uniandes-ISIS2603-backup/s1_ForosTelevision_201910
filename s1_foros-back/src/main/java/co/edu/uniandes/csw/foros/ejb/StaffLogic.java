/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.StaffEntity;
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
    public boolean rutaImagenCorrecta(String url) {
        Matcher mtch = RUTA_IMAGENES.matcher(url);
        return mtch.matches();
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

    /**
     * Método que cambia el rol de un miembro del staff.
     *
     * @param idStaff id del staff al que se le va a cambiar el rol.
     * @param nuevoRol nuevo rol del miembro del staff.
     * @return la entidad actualizada con el id que entró por parámetro.
     * @throws BusinessLogicException cuando no existe un miembro del staff con
     * idStaff | nuevoRol = null.
     */
    public StaffEntity cambiarRolStaff(Long idStaff, StaffEntity.RolStaff nuevoRol) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia el proceso de cambio de rol del miembro del staff");
        StaffEntity entidadEnDB = staffPersistence.find(idStaff);
        if (entidadEnDB == null) {
            throw new BusinessLogicException("No existe staff con tal id.");
        }
        if (nuevoRol == null) {
            throw new BusinessLogicException("El nuevo rol no existe");
        }
        entidadEnDB.setRol(nuevoRol);
        staffPersistence.update(entidadEnDB);
        LOGGER.log(Level.INFO, "Termina el proceso de cambio de rol del miembro del staff");
        return entidadEnDB;
    }

    /**
     * Método que cambia el nombre de un miembro del staff.
     *
     * @param idStaff id del staff al que se le va a cambiar el nombre.
     * @param nuevoNombre nuevo nombre del miembro del staff.
     * @return la entidad actualizada con el id que entró por parámetro.
     * @throws BusinessLogicException cuando no existe un miembro del staff con
     * idStaff | nuevoNombre = (null | "").
     */
    public StaffEntity cambiarNombreStaff(Long idStaff, String nuevoNombre) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia el proceso de cambio de nombre del miembro del staff");
        StaffEntity entidadEnDB = staffPersistence.find(idStaff);
        if (entidadEnDB == null) {
            throw new BusinessLogicException("No existe staff con tal id.");
        }
        if (nuevoNombre == null) {
            throw new BusinessLogicException("El nuevo nombre no existe");
        }
        if (nuevoNombre.equals("")) {
            throw new BusinessLogicException("El nuevo nombre es vacío");
        }
        entidadEnDB.setNombre(nuevoNombre);
        staffPersistence.update(entidadEnDB);
        LOGGER.log(Level.INFO, "Termina el proceso de cambio de nombre del miembro del staff");
        return entidadEnDB;
    }

    /**
     * Método que cambia el descripción de un miembro del staff.
     *
     * @param idStaff id del staff al que se le va a cambiar el descripción.
     * @param nuevaDescripcion nuevo descripción del miembro del staff.
     * @return la entidad actualizada con el id que entró por parámetro.
     * @throws BusinessLogicException cuando no existe un miembro del staff con
     * idStaff | nuevaDescripcion = (null | "").
     */
    public StaffEntity cambiarDescripcionStaff(Long idStaff, String nuevaDescripcion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia el proceso de cambio de descripcion del miembro del staff");
        StaffEntity entidadEnDB = staffPersistence.find(idStaff);
        if (entidadEnDB == null) {
            throw new BusinessLogicException("No existe staff con tal id.");
        }
        if (nuevaDescripcion == null) {
            throw new BusinessLogicException("La nueva descripcion no existe");
        }
        if (nuevaDescripcion.equals("")) {
            throw new BusinessLogicException("La nueva descripcion es vacía");
        }
        entidadEnDB.setDescripcion(nuevaDescripcion);
        staffPersistence.update(entidadEnDB);
        LOGGER.log(Level.INFO, "Termina el proceso de cambio de descripcion del miembro del staff");
        return entidadEnDB;
    }

    /**
     * Método que cambia el foto de un miembro del staff.
     *
     * @param idStaff id del staff al que se le va a cambiar el foto.
     * @param nuevaFoto nuevo foto del miembro del staff.
     * @return la entidad actualizada con el id que entró por parámetro.
     * @throws BusinessLogicException cuando no existe un miembro del staff con
     * idStaff | nuevaFoto = null | nuevaFoto no cumple los estándares de
     * RUTA_IMAGENES.
     */
    public StaffEntity cambiarFotoStaff(Long idStaff, String nuevaFoto) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia el proceso de cambio de foto del miembro del staff");
        StaffEntity entidadEnDB = staffPersistence.find(idStaff);
        if (entidadEnDB == null) {
            throw new BusinessLogicException("No existe staff con tal id.");
        }
        if (nuevaFoto == null) {
            throw new BusinessLogicException("La nueva foto no existe");
        }
        if (!rutaImagenCorrecta(nuevaFoto)) {
            throw new BusinessLogicException("La nueva foto es vacía");
        }
        entidadEnDB.setFoto(nuevaFoto);
        staffPersistence.update(entidadEnDB);
        LOGGER.log(Level.INFO, "Termina el proceso de cambio de foto del miembro del staff");
        return entidadEnDB;
    }

    /**
     * Método que agrega una producción a la lista de producciones en las que
     * participó un miembro del staff.
     *
     * @param idStaff id del staff al que se le agregará una nueva producción.
     * @param idProduccion id de la producción que se agregará a la lista del
     * staff.
     * @return entidad de la producción que se agregó al staff. Null si no
     * existe.
     * @throws BusinessLogicException si: no existe un miembro del staff con ese
     * id | no existe una producción con ese id | ya existe una producción con
     * ese id en la lista de producciones del miembro del staff.
     */
    public StaffEntity agregarProduccionStaff(Long idStaff, Long idProduccion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia el proceso de agregación de una producción en la que fue partícipe un miembro del staff");
        StaffEntity staffEntity = staffPersistence.find(idStaff);
        if (staffEntity == null) {
            throw new BusinessLogicException("No existe un miembro del staff con tal id");
        }
        ProduccionEntity produccionEntity = produccionPersistence.find(idProduccion);
        if (produccionEntity == null) {
            throw new BusinessLogicException("No existe producción con tal id");
        }
        if (staffEntity.getProducciones().contains(produccionEntity)) {
            throw new BusinessLogicException("Esa producción ya está dentro de las producciones en las que participó el miembro del staff");
        }
        List<ProduccionEntity> nuevaLista = new ArrayList(staffEntity.getProducciones());
        nuevaLista.add(produccionEntity);
        staffEntity.setProducciones(nuevaLista);
        staffEntity = staffPersistence.update(staffEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de agregación de una producción en la que fue partícipe un miembro del staff");
        return staffEntity;
    }

    /**
     * Método que elimina una producción de la lista de producciones en las que
     * participó un miembro del staff.
     *
     * @param idStaff id del staff al que se le eliminará la producción.
     * @param idProduccion id de la producción que se eliminará de la lista del
     * staff.
     * @return entidad de la producción que se eliminó al staff. Null si no
     * existe.
     * @throws BusinessLogicException si: no existe un miembro del staff con ese
     * id | no existe una producción con ese id | no existe una producción con
     * ese id en la lista de producciones del miembro del staff.
     */
    public StaffEntity eliminarProduccionStaff(Long idStaff, Long idProduccion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia el proceso de eliminación de una producción en la que no fue partícipe un miembro del staff");
        StaffEntity staffEntity = staffPersistence.find(idStaff);
        if (staffEntity == null) {
            throw new BusinessLogicException("No existe un miembro del staff con tal id");
        }
        ProduccionEntity produccionEntity = produccionPersistence.find(idProduccion);
        if (produccionEntity == null) {
            throw new BusinessLogicException("No existe producción con tal id");
        }
        if (!staffEntity.getProducciones().contains(produccionEntity)) {
            throw new BusinessLogicException("Esa producción no está dentro de las producciones en las que participó el miembro del staff");
        }
        List<ProduccionEntity> nuevaLista = new ArrayList(staffEntity.getProducciones());
        nuevaLista.remove(produccionEntity);
        staffEntity.setProducciones(nuevaLista);
        staffEntity = staffPersistence.update(staffEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de eliminación de una producción en la que no fue partícipe un miembro del staff");
        return staffEntity;
    }
}
