/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;


import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
import co.edu.uniandes.csw.foros.persistence.ProductoraPersistence;
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
public class ProductoraPersistenceTest {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private ProductoraPersistence persistence;
    
    @Inject
    UserTransaction utx;
    
    private List<ProductoraEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProductoraEntity.class.getPackage())
                .addPackage(ProductoraPersistence.class.getPackage())
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
        em.createQuery("delete from ProductoraEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ProductoraEntity entity = factory.manufacturePojo(ProductoraEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createProductoraTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ProductoraEntity newEntity = factory.manufacturePojo(ProductoraEntity.class);
        ProductoraEntity result = persistence.create(newEntity);
        Assert.assertNotNull(result);
        ProductoraEntity entity = em.find(ProductoraEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test
    public void getProductorasTest() {
        List<ProductoraEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ProductoraEntity entity : list) {
            boolean found = false;
            for (ProductoraEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
      
    @Test
    public void getProductoraTest() {
        ProductoraEntity entity = data.get(0);
        ProductoraEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
     
    @Test
    public void updateProductoraTest() {
        ProductoraEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ProductoraEntity nuevo = factory.manufacturePojo(ProductoraEntity.class);

        nuevo.setId(entity.getId());

        persistence.update(nuevo);

        ProductoraEntity mod = em.find(ProductoraEntity.class, entity.getId());

        Assert.assertEquals(nuevo.getNombre(), mod.getNombre());
    }
    
    @Test
    public void deleteProductoraTest() {
        ProductoraEntity entity = data.get(0);
        persistence.delete(entity.getId());
        ProductoraEntity borrado = em.find(ProductoraEntity.class, entity.getId());
        Assert.assertNull(borrado);
    }
    
}
