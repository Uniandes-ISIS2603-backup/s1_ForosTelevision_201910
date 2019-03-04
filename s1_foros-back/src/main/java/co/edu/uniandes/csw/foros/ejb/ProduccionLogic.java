package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
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
    
    @Inject
    private ProduccionPersistence produccionPersistence;
    
     public ProduccionEntity find(Long  idProduccion)throws BusinessLogicException{
        ProduccionEntity prod=produccionPersistence.find(idProduccion);
        if(prod==null) throw new BusinessLogicException("Produccion no registrada");
        return prod;
     }

}
