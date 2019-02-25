/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.EmisionLogic;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
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
public class EmisionLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private EmisionPersistence persistence;
    
    @Inject
    private EmisionLogic logic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<EmisionEntity> data = new ArrayList<>();
    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EmisionEntity.class.getPackage())
                .addPackage(EmisionPersistence.class.getPackage())
                .addPackage(EmisionLogic.class.getPackage())
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
    public void crearEmisionTest() throws BusinessLogicException{
        EmisionEntity nuevo = factory.manufacturePojo(EmisionEntity.class);
        EmisionEntity resultado = logic.createEmision(nuevo);
        Assert.assertNotNull(resultado);
        EmisionEntity emision = em.find(EmisionEntity.class, resultado.getId());
        Assert.assertEquals(resultado.getId(), emision.getId());
        Assert.assertEquals(resultado.getRating(),emision.getRating()); 
    }
    
    @Test
    public void obtenerEmisionesTest(){
        List<EmisionEntity> emisiones = logic.getEmisiones();
        Assert.assertEquals(data.size(), emisiones.size());
        for(EmisionEntity emision : emisiones){
            boolean encontrado = false;
            for(EmisionEntity emisionData : data){
                if(emision.getId().equals(emisionData.getId())){
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }
    
    @Test
    public void obtenerEmision(){
        EmisionEntity emisionData = data.get(0);
        EmisionEntity emision = logic.getEmisionPorId(emisionData.getId());
        Assert.assertNotNull(emision);
        Assert.assertEquals(emisionData.getId(), emision.getId());
        Assert.assertEquals(emisionData.getRating(),emision.getRating());   
    }
    
   
    @Test
    public void actualizarCapitulo(){
        EmisionEntity emision = data.get(0);
        EmisionEntity emisionNueva = factory.manufacturePojo(EmisionEntity.class);
        
        emisionNueva.setId(emision.getId());
        logic.actualizarEmision(emisionNueva.getId(), emisionNueva);
        EmisionEntity respuesta = em.find(EmisionEntity.class, emision.getId());
        
        Assert.assertEquals(respuesta.getId(), emisionNueva.getId());
        Assert.assertEquals(respuesta.getRating(), emisionNueva.getRating());
    }
    
    @Test
    public void borrarEmision(){
        EmisionEntity emision = data.get(0);
        logic.borrarEmision(emision.getId());
        EmisionEntity respuesta = em.find(EmisionEntity.class, emision.getId());
        Assert.assertNull(respuesta);
    }
    
    @Test(expected=BusinessLogicException.class)
    public void reglasDeNegocio() throws BusinessLogicException{
        EmisionEntity emision = data.get(0);
        logic.createEmision(emision);
    }
    
}
