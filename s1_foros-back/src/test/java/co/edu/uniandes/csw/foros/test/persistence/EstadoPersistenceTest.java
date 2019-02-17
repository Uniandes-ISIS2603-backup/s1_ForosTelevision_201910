/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.EstadoEntity;
import co.edu.uniandes.csw.foros.persistence.EstadoPersistence;
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
public class EstadoPersistenceTest {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private EstadoPersistence persistence;
    
    @Inject
    UserTransaction utx;
    
    private List<EstadoEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EstadoEntity.class.getPackage())
                .addPackage(EstadoPersistence.class.getPackage())
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
        em.createQuery("delete from EstadoEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            EstadoEntity entity = factory.manufacturePojo(EstadoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createEstadoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        EstadoEntity newEntity = factory.manufacturePojo(EstadoEntity.class);
        EstadoEntity result = persistence.create(newEntity);
        Assert.assertNotNull(result);
        EstadoEntity entity = em.find(EstadoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test
    public void getEstadosTest() {
        List<EstadoEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (EstadoEntity entity : list) {
            boolean found = false;
            for (EstadoEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
      
    @Test
    public void getEstadoTest() {
        EstadoEntity entity = data.get(0);
        EstadoEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getEstado(), newEntity.getEstado());
    }
     
    @Test
    public void updateEstadoTest() {
        EstadoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EstadoEntity nuevo = factory.manufacturePojo(EstadoEntity.class);

        nuevo.setId(entity.getId());

        persistence.update(nuevo);

        EstadoEntity mod = em.find(EstadoEntity.class, entity.getId());

        Assert.assertEquals(nuevo.getEstado(), mod.getEstado());
    }
    
    @Test
    public void deleteEstadoTest() {
        EstadoEntity entity = data.get(0);
        persistence.delete(entity.getId());
        EstadoEntity borrado = em.find(EstadoEntity.class, entity.getId());
        Assert.assertNull(borrado);
    }
    
}
