/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.EmisionLogic;
import co.edu.uniandes.csw.foros.ejb.EmisionCanalLogic;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
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
 * @author ne.ortega
 */
@RunWith(Arquillian.class)
public class EmisionCanalLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EmisionLogic emLogic;
    
    @Inject
    private EmisionCanalLogic emCanalLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CanalEntity> data = new ArrayList<>();

    private List<EmisionEntity> emData = new ArrayList<>();

   
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CanalEntity.class.getPackage())
                .addPackage(EmisionLogic.class.getPackage())
                .addPackage(CanalPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

  
    @Before
    public void configTest() {
        try {
            utx.begin();
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
        em.createQuery("delete from CanalEntity").executeUpdate();
    }

  
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EmisionEntity emisiones = factory.manufacturePojo(EmisionEntity.class);
            em.persist(emisiones);
            emData.add(emisiones);
        }
        for (int i = 0; i < 3; i++) {
            CanalEntity entity = factory.manufacturePojo(CanalEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                emData.get(i).setCanal(entity);
            }
        }
    }
    
    @Test
    public void agregarCanalTest(){
        CanalEntity canal = data.get(1);
        EmisionEntity emision = emData.get(0);
        CanalEntity resp = emCanalLogic.agregarCanal(emision.getId(), canal.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getId(), canal.getId());
    }
    
    @Test
    public void obtenerProduccionTest(){
        CanalEntity canal = data.get(0);
        EmisionEntity emision = emData.get(0);
        emCanalLogic.agregarCanal(canal.getId(), emision.getId());
        CanalEntity resp = emCanalLogic.obtenerCanal(emision.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getId(), canal.getId());
    }

    @Test
    public void actualizarProduccionTest() throws BusinessLogicException{
        EmisionEntity entity = emData.get(0);
        emCanalLogic.actualizarCanal(entity.getId(), data.get(1));
        entity = emLogic.getEmisionPorId(entity.getId());
        Assert.assertEquals(entity.getCanal(), data.get(1));
    }
    
    @Test
    public void eliminarProduccionTest() throws BusinessLogicException{
        emCanalLogic.eliminarCanal(emData.get(0).getId());
        EmisionEntity response = emLogic.getEmisionPorId(emData.get(0).getId());
        Assert.assertNull(response.getCanal());
    }
}
