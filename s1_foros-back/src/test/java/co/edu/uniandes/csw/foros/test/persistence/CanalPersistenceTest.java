/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
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
public class CanalPersistenceTest {
    
    @Inject
    private CanalPersistence cp;
    
    @PersistenceContext
    private EntityManager em; 
   
    @Inject
    UserTransaction utx;
 
    private List<CanalEntity> data = new ArrayList<>();
    
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
        em.createQuery("delete from CanalEntity").executeUpdate();
    }
    
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
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CanalEntity entity = factory.manufacturePojo(CanalEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createCanalTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        CanalEntity newCanalEntity=factory.manufacturePojo(CanalEntity.class);
       
        CanalEntity ce =cp.create(newCanalEntity);
        Assert.assertNotNull(ce);
        CanalEntity entity=em.find(CanalEntity.class,ce.getId());
        Assert.assertEquals(newCanalEntity.getNombre(),entity.getNombre());
    }
    
      /**
     * Prueba para consultar un Canal
     */
    @Test
    public void getCanalTest() {
        CanalEntity entity = data.get(0);
        CanalEntity newEntity = cp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
    /**
     * Prueba para consultar la lista de Canales
     */
    @Test
    public void findAllTest() {
        List<CanalEntity> list = cp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CanalEntity ent : list) {
            boolean found = false;
            for (CanalEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
       /**
     * Prueba para eliminar un canal
     */
    @Test
    public void deleteCanalTest() {
        
        CanalEntity entity = data.get(0);
         cp.delete(entity.getId());
        CanalEntity deleted = em.find(CanalEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
     /**
     * Prueba para actualizar un canal
     */
    @Test
    public void updateCanalTest() {
        CanalEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CanalEntity newEntity = factory.manufacturePojo(CanalEntity.class);
        newEntity.setId(entity.getId());
        cp.update(newEntity);
        CanalEntity resp = em.find(CanalEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

}
