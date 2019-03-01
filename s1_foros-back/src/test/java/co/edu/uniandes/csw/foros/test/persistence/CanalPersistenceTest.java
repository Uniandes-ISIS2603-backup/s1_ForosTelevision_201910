/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
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
 
    
    @Deployment
    public static JavaArchive deployment()
    {
         return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CanalEntity.class.getPackage())
                .addPackage(CanalPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
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
    
    
}
