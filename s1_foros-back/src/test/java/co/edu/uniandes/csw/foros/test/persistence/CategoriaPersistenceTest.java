/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.CategoriaEntity;
import co.edu.uniandes.csw.foros.persistence.CategoriaPersistence;
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
 * @author Jhonattan Fonseca.
 */
@RunWith(Arquillian.class)
public class CategoriaPersistenceTest {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private CategoriaPersistence persistence;
    
    @Inject
    UserTransaction utx;
    
    private List<CategoriaEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CategoriaEntity.class.getPackage())
                .addPackage(CategoriaPersistence.class.getPackage())
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
        em.createQuery("delete from CategoriaEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CategoriaEntity entity = factory.manufacturePojo(CategoriaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createCategoriaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);
        CategoriaEntity result = persistence.create(newEntity);
        Assert.assertNotNull(result);
        CategoriaEntity entity = em.find(CategoriaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test
    public void getCatgeoriasTest() {
        List<CategoriaEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CategoriaEntity entity : list) {
            boolean found = false;
            for (CategoriaEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
      
    @Test
    public void getCategoriaTest() {
        CategoriaEntity entity = data.get(0);
        CategoriaEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
     
    @Test
    public void updateCategoriaTest() {
        CategoriaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CategoriaEntity nuevo = factory.manufacturePojo(CategoriaEntity.class);

        nuevo.setId(entity.getId());

        persistence.update(nuevo);

        CategoriaEntity mod = em.find(CategoriaEntity.class, entity.getId());

        Assert.assertEquals(nuevo.getNombre(), mod.getNombre());
    }
    
    @Test
    public void deleteCategoriaTest() {
        CategoriaEntity entity = data.get(0);
        persistence.delete(entity.getId());
        CategoriaEntity borrado = em.find(CategoriaEntity.class, entity.getId());
        Assert.assertNull(borrado);
    }
    
    
    
}
