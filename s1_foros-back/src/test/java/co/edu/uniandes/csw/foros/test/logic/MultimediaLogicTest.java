package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.MultimediaLogic;
import co.edu.uniandes.csw.foros.entities.MultimediaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.MultimediaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author bsrincon
 */
@RunWith(Arquillian.class)
public class MultimediaLogicTest {
    
    @Inject
    private MultimediaLogic logic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<MultimediaEntity> data = new ArrayList<>();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MultimediaEntity.class.getPackage())
                .addPackage(MultimediaPersistence.class.getPackage())
                .addPackage(MultimediaLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from MultimediaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            MultimediaEntity entity = factory.manufacturePojo(MultimediaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un recurso Multimedia.
     * @throws co.edu.uniandes.csw.foros.exceptions.BusinessLogicException
     */
    @Test
    public void createMultimediaTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setPortada(newEntity.getPortada()+".png");
        MultimediaEntity result = logic.crearRecursoMultimedia(newEntity);
        Assert.assertNotNull(result);
        MultimediaEntity entity = em.find(MultimediaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Prueba para crear un recurso multimedia null.
     * @throws co.edu.uniandes.csw.foros.exceptions.BusinessLogicException
     */
    @Test(expected=BusinessLogicException.class)
    public void createMultimediaTestFileNull() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setPortada(null);
        MultimediaEntity result = logic.crearRecursoMultimedia(newEntity);
    }
     
    /**
     * Prueba para crear un recurso video un archivo de formato  soportado y agregarlo
     * a multimedia.
     * @throws co.edu.uniandes.csw.foros.exceptions.BusinessLogicException
     */
    @Test
    public void createMultimediaTestFileAddVideo() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setPortada(newEntity.getPortada()+".png");
        MultimediaEntity result = logic.crearRecursoMultimedia(newEntity);
        logic.adicionarVideo(result.getId(), "https://s3-sa-east-1.amazonaws.com/forostv/game.pp4");
    }
     /**
     * Prueba para crear un recurso imagen un archivo de formato  soportado y agregarlo
     * a multimedia.
     * @throws co.edu.uniandes.csw.foros.exceptions.BusinessLogicException
     */
    @Test
    public void createMultimediaTestFileAddImg() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setPortada(newEntity.getPortada()+".png");
        MultimediaEntity result = logic.crearRecursoMultimedia(newEntity);
        logic.adicionarImagen(result.getId(), "https://s3-sa-east-1.amazonaws.com/forostv/game.jpg");
    } 
     
    
     /**
     * Prueba para crear un recurso imagen un archivo de formato  no soportado y agregarlo
     * a multimedia.
     * @throws co.edu.uniandes.csw.foros.exceptions.BusinessLogicException
     */
    @Test(expected=BusinessLogicException.class)
    public void createMultimediaTestFileAddVideoInvalid() throws BusinessLogicException {
        logic.adicionarVideo(-1L, "https://s3-sa-east-1.amazonaws.com/forostv/game.slsls");
    }
    
    @Test(expected=BusinessLogicException.class)
    public void darRecursosTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setPortada(newEntity.getPortada()+".jpg");
        MultimediaEntity result = logic.crearRecursoMultimedia(newEntity);
        Assert.assertNull(logic.darRecursosMultimediaProduccion(result.getId()));
    } 
}

