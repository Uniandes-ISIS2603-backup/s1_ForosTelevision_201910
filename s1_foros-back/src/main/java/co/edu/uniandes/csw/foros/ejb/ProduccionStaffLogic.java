package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.StaffEntity;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
import co.edu.uniandes.csw.foros.persistence.StaffPersistence;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase relación entre producción y staff.
 *
 * @auther jf.castaneda
 */
@Stateless
public class ProduccionStaffLogic {

    private static final Logger LOGGER = Logger.getLogger(ProduccionStaffLogic.class.getName());

    @Inject
    private ProduccionPersistence produccionPersistence;

    @Inject
    private StaffPersistence staffPersistence;

    public void eliminarStaff(Long staffId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el staff con id = {0}", staffId);
        StaffEntity staffEntity = staffPersistence.find(staffId);
        //ProduccionEntity produccionEntity = produccionPersistence.find(staffEntity.getEditorial().getId());
        //bookEntity.setEditorial(null);
        //editorialEntity.getBooks().remove(bookEntity);
        //LOGGER.log(Level.INFO, "Termina proceso de borrar la Editorial del libro con id = {0}", bookEntity.getId());
    }

}
