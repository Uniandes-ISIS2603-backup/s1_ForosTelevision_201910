/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.DiaEntity;
import co.edu.uniandes.csw.foros.persistence.DiaPersistence;
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
public class DiaPersistenceTest {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private DiaPersistence persistence;
    
    @Inject
    UserTransaction utx;
    
    private List<DiaEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DiaEntity.class.getPackage())
                .addPackage(DiaPersistence.class.getPackage())
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
        em.createQuery("delete from DiaEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            DiaEntity entity = factory.manufacturePojo(DiaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createDiaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        DiaEntity newEntity = factory.manufacturePojo(DiaEntity.class);
        DiaEntity result = persistence.create(newEntity);
        Assert.assertNotNull(result);
        DiaEntity entity = em.find(DiaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test
    public void getDiasTest() {
        List<DiaEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (DiaEntity entity : list) {
            boolean found = false;
            for (DiaEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
      
    @Test
    public void getDiaTest() {
        DiaEntity entity = data.get(0);
        DiaEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
     
    @Test
    public void updateDiaTest() {
        DiaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        DiaEntity nuevo = factory.manufacturePojo(DiaEntity.class);

        nuevo.setId(entity.getId());

        persistence.update(nuevo);

        DiaEntity mod = em.find(DiaEntity.class, entity.getId());

        Assert.assertEquals(nuevo.getNombre(), mod.getNombre());
    }
    
    @Test
    public void deleteDiaTest() {
        DiaEntity entity = data.get(0);
        persistence.delete(entity.getId());
        DiaEntity borrado = em.find(DiaEntity.class, entity.getId());
        Assert.assertNull(borrado);
    }
}
