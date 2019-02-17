/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.persistence.EmisionPersistence;
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
 * @author ne.ortega
 */
@RunWith(Arquillian.class)
public class EmisionPersistenceTest {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private EmisionPersistence persistence;
    
    @Inject
    UserTransaction utx;
    
    private List<EmisionEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EmisionEntity.class.getPackage())
                .addPackage(EmisionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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
    
    private void clearData() {
        em.createQuery("delete from EmisionEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            EmisionEntity entity = factory.manufacturePojo(EmisionEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createEmisionTest() {
        PodamFactory factory = new PodamFactoryImpl();
        EmisionEntity newEntity = factory.manufacturePojo(EmisionEntity.class);
        EmisionEntity result = persistence.create(newEntity);
        Assert.assertNotNull(result);
        EmisionEntity entity = em.find(EmisionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test
    public void getEmisionesTest() {
        List<EmisionEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (EmisionEntity entity : list) {
            boolean found = false;
            for (EmisionEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
      
    @Test
    public void getEmisionTest() {
        EmisionEntity entity = data.get(0);
        EmisionEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getRating(), newEntity.getRating());
    }
     
    @Test
    public void updateEmisionTest() {
        EmisionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EmisionEntity nuevo = factory.manufacturePojo(EmisionEntity.class);

        nuevo.setId(entity.getId());

        persistence.update(nuevo);

        EmisionEntity mod = em.find(EmisionEntity.class, entity.getId());

        Assert.assertEquals(nuevo.getRating(), mod.getRating());
    }
    
    @Test
    public void deleteEmisionTest() {
        EmisionEntity entity = data.get(0);
        persistence.delete(entity.getId());
        EmisionEntity borrado = em.find(EmisionEntity.class, entity.getId());
        Assert.assertNull(borrado);
    }
    
}
