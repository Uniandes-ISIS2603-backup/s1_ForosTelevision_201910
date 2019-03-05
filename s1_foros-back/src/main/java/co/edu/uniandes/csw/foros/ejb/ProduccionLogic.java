package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
import co.edu.uniandes.csw.foros.persistence.StaffPersistence;

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
    
    public void crearProduccion(ProduccionEntity produccionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del una producción.");
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
        // Validación relación multimedia.
    }
    
     public ProduccionEntity darProduccion(Long idProduccion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Retorna una producción.");
        ProduccionEntity prod=produccionPersistence.find(idProduccion);
        if(prod==null) throw new BusinessLogicException("Produccion no registrada");
        return prod;
     }

     public void darProduccion(Long idProduccion, Long idStaff) throws BusinessLogicException {
        if(idProduccion == null) {
            throw new BusinessLogicException("El id de la producción debe existir.");
        }
        if(idStaff == null) {
            throw new BusinessLogicException("El id del staff debe exisir.");
        }
        ProduccionEntity produccionEntity = produccionPersistence.find(idProduccion);
     }
}
