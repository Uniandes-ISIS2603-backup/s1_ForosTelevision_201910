package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
import co.edu.uniandes.csw.foros.persistence.StaffPersistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author jf.castaneda, bs.rincon
 */
@Stateless 
public class ProduccionLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ProduccionLogic.class.getName());
    
    /**
     * Inyección de un objeto que trata la persistencia de las producciones.
     */
    @Inject
    private ProduccionPersistence produccionPersistence;

    /**
     * Inyección de los miembros del staff.
     */
    @Inject
    private StaffPersistence staffPersistence;

    /**
     * Método con el cual se crea una producción.
     *
     * @param produccionEntity entidad de la producción a ser creada.
     * @return la producción recién creada.
     * @throws BusinessLogicException cuando alguno de los atributos no cumple las reglas de negocio.
     */
    public ProduccionEntity crearProduccion(ProduccionEntity produccionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la producción.");
        comprobarReglasDeNegocio(produccionEntity);
        produccionEntity = produccionPersistence.create(produccionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la producción");
        return produccionEntity;
    }

    /**
     * Método que retorna todas las producciones.
     *
     * @return lista con todas las producciones.
     */
    public List<ProduccionEntity> darTodasProducciones() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las producciones");
        List<ProduccionEntity> producciones = produccionPersistence.getAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las producciones");
        return producciones;
    }

    /**
     * Método que retorna una producción según su id.
     *
     * @param idProduccion id de la producción a retornar.
     * @return la entidad de la producción. Si no existe, null.
     * @throws BusinessLogicException cuando el id que entra por parámetro es nulo.
     */
    public ProduccionEntity darProduccion(Long idProduccion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la producción con id = {0}", idProduccion);
        if (idProduccion == null) {
            LOGGER.log(Level.SEVERE, "La producción con el id = {0} no existe", idProduccion);
        }
        ProduccionEntity produccionEntity = produccionPersistence.find(idProduccion);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la producción con id = {0}", idProduccion);
        return produccionEntity;
    }

    /**
     * Método que edita la información de una producción.
     *
     * @param idProduccion el id de la producción a editar.
     * @param produccionEntity la nueva información de la producción.
     * @return la entidad de la producción editada.
     * @throws BusinessLogicException cuando alguno de los atributos no cumple con las reglas de negocio.
     */
    public ProduccionEntity editarProduccion(Long idProduccion, ProduccionEntity produccionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la producción con id = {0}", idProduccion);
        if(idProduccion == null) {
            throw new BusinessLogicException("El id de la producción debe existir");
        }
        comprobarReglasDeNegocio(produccionEntity);
        ProduccionEntity newEntity = produccionPersistence.update(produccionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el libro con id = {0}", produccionEntity.getId());
        return newEntity;
    }

    /**
     * Método que elimina una producción.
     *
     * @param idProduccion id de la producción a eliminar.
     * @return producción que se eliminó.
     * @throws BusinessLogicException si no existe la entidad.
     */
    public ProduccionEntity eliminarProduccion(Long idProduccion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminación de una producción");
        ProduccionEntity produccionEntity = produccionPersistence.find(idProduccion);
        if (produccionEntity == null) {
            throw new BusinessLogicException("No existe una producción con ese id.");
        }
        staffPersistence.delete(idProduccion);
        LOGGER.log(Level.INFO, "Termina proceso de eliminación del miembro del staff.");
        return produccionEntity;
    }

    /**
     * Método que comprueba las reglas de negocio.
     *
     * @param produccionEntity entidad a comprobar.
     * @throws BusinessLogicException cuando alguno de los atributos no cumple con las reglas de negocio.
     */
    private void comprobarReglasDeNegocio(ProduccionEntity produccionEntity) throws BusinessLogicException {
        // Validación atributo nombre.
        if(produccionEntity.getNombre() == null) {
            throw new BusinessLogicException("El nombre de la producción debe existir.");
        }
        if(produccionEntity.getNombre().equals("")) {
            throw new BusinessLogicException("El nombre de la producción no debe ser vacío.");
        }
        // Validación atributo descripción.
        if(produccionEntity.getDescripcion() == null) {
            throw new BusinessLogicException("La descripción de la producción debe existir.");
        }
        if(produccionEntity.getDescripcion().equals("")) {
            throw new BusinessLogicException("La descripción de la producción no debe ser vacía.");
        }
        // Validación atibuto clasificación.
        if(produccionEntity.getClasificacionAudiencia() == null) {
            throw new BusinessLogicException("La clasificación de la audiencia no puede ser nula.");
        }
        // Validación atributo calificación promedio.
        if(produccionEntity.getCalificacionPromedio() < 0) {
            throw new BusinessLogicException("La calificación promedio es menor a 0.");
        }
        if(produccionEntity.getCalificacionPromedio() > 1000) {
            throw new BusinessLogicException("La calificación promedio es mayor a 1000.");
        }
    }

}
