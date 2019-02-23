package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.MultimediaEntity;
import co.edu.uniandes.csw.foros.persistence.MultimediaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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

public class MultimediaPersistenceTest {
    
    @Inject
    private MultimediaPersistence MultimediaPersistence;

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
     */
    @Test
    public void createUsuarioTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        MultimediaEntity result = MultimediaPersistence.create(newEntity);
        Assert.assertNotNull(result);
        MultimediaEntity entity = em.find(MultimediaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Prueba para consultar la lista de recursos.
     */
    @Test
    public void getAllTest() {
        List<MultimediaEntity> list = MultimediaPersistence.getAll();
        Assert.assertEquals(data.size(), list.size());
        for (MultimediaEntity ent : list) {
            boolean found = false;
            for (MultimediaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Recurso.
     */
    @Test
    public void getAuthorTest() {
        MultimediaEntity entity = data.get(0);
        MultimediaEntity newEntity = MultimediaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * Prueba para actualizar un recurso.
     */
    @Test
    public void updateAuthorTest() {
        MultimediaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setId(entity.getId());
        MultimediaPersistence.update(newEntity);
        MultimediaEntity resp = em.find(MultimediaEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
    /**
     * Prueba para eliminar un recurso.
     */
    @Test
    public void deleteAuthorTest() {
        MultimediaEntity entity = data.get(0);
        MultimediaPersistence.delete(entity.getId());
        MultimediaEntity deleted = em.find(MultimediaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
