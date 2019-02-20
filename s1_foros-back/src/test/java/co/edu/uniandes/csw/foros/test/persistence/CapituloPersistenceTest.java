/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.CapituloEntity;
import co.edu.uniandes.csw.foros.persistence.CapituloPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author ne.ortega
 */
@RunWith(Arquillian.class)
public class CapituloPersistenceTest {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private CapituloPersistence capitulo;
    
    @Inject
    UserTransaction utx;
    
    private List<CapituloEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CapituloEntity.class.getPackage())
                .addPackage(CapituloPersistence.class.getPackage())
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
        em.createQuery("delete from CapituloEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CapituloEntity entity = factory.manufacturePojo(CapituloEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createCapituloTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CapituloEntity newEntity = factory.manufacturePojo(CapituloEntity.class);
        CapituloEntity result = capitulo.create(newEntity);
        Assert.assertNotNull(result);
        CapituloEntity entity = em.find(CapituloEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test
    public void getCapitulosTest() {
        List<CapituloEntity> list = capitulo.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CapituloEntity entity : list) {
            boolean found = false;
            for (CapituloEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getCapituloTest() {
        CapituloEntity entity = data.get(0);
        CapituloEntity newEntity = capitulo.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getDuracion(), newEntity.getDuracion());
    }
    
    @Test
    public void getCapituloByNameTest(){
        CapituloEntity entity = data.get(0);
        CapituloEntity newEntity = capitulo.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getDuracion(), newEntity.getDuracion());
    }
    
    @Test
    public void updateCapituloTest() {
        CapituloEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CapituloEntity nuevo = factory.manufacturePojo(CapituloEntity.class);

        nuevo.setId(entity.getId());

        capitulo.update(nuevo);

        CapituloEntity mod = em.find(CapituloEntity.class, entity.getId());

        Assert.assertEquals(nuevo.getNombre(), mod.getNombre());
    }
    
    @Test
    public void deleteCapituloTest() {
        CapituloEntity entity = data.get(0);
        capitulo.delete(entity.getId());
        CapituloEntity borrado = em.find(CapituloEntity.class, entity.getId());
        Assert.assertNull(borrado);
    }
}
