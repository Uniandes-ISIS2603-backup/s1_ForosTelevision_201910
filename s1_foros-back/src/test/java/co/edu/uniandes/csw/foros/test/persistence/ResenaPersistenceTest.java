
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import co.edu.uniandes.csw.foros.persistence.ResenaPersistence;
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
 * @author mi.carrascal
 */
@RunWith(Arquillian.class)
public class ResenaPersistenceTest {
    
    
    @Inject
    private ResenaPersistence rp;
    
    @PersistenceContext
    private EntityManager em; 
    
    @Inject
    UserTransaction utx;
 
    private List<ResenaEntity> data = new ArrayList<>();
 
    
    @Deployment
    public static JavaArchive deployment()
    {
         return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CanalEntity.class.getPackage())
                .addPackage(CanalPersistence.class.getPackage())
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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ResenaEntity entity = factory.manufacturePojo(ResenaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    
    
    @Test
    public void createResenaTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ResenaEntity newEntity = factory.manufacturePojo(ResenaEntity.class);
        ResenaEntity result = rp.create(newEntity);
        Assert.assertNotNull(result);
        ResenaEntity entity = em.find(ResenaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getFecha(), entity.getFecha());
        Assert.assertEquals(newEntity.getCalificacionProduccion(), entity.getCalificacionProduccion());
    }
    
     /**
     * Prueba para consultar un Author.
     */
    @Test
    public void getResenaTest() {
        ResenaEntity entity = data.get(0);
        ResenaEntity newEntity = rp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
  
    /**
     * Prueba para consultar la lista de Reseñas
     */
//    @Test
//    public void findAllTest() {
//        List<ResenaEntity> list = rp.findAll();
//        Assert.assertEquals(data.size(), list.size());
//        for (ResenaEntity ent : list)+ {
//            boolean found = false;++
//            for (ResenaEntity entity : data) {
//                if (ent.getId().equals(entity.getId())) {
//                    found = true;
//                }
//            }
//            Assert.assertTrue(found);
//        }
//    }

//   

    /**
     * Prueba para actualizar una reseña
     */
    @Test
    public void updateResenaTest() {
        ResenaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ResenaEntity newEntity = factory.manufacturePojo(ResenaEntity.class);
        newEntity.setId(entity.getId());
        rp.update(newEntity);
        ResenaEntity resp = em.find(ResenaEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), resp.getId());
    }

    /**
     * Prueba para eliminar una reseña
     */
    @Test
    public void deleteResenaTest() {
        ResenaEntity entity = data.get(0);
        rp.delete(entity.getId());
        ResenaEntity deleted = em.find(ResenaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
