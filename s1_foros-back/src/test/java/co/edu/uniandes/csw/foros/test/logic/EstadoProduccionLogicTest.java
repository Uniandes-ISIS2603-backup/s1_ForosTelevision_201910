/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.EstadoProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.EstadoLogic;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.EstadoEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
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
public class EstadoProduccionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EstadoLogic estLogic;
    
    @Inject
    private EstadoProduccionLogic estProdLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ProduccionEntity> data = new ArrayList<>();

    private List<EstadoEntity> estData = new ArrayList<>();

   
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProduccionEntity.class.getPackage())
                .addPackage(EstadoLogic.class.getPackage())
                .addPackage(ProduccionPersistence.class.getPackage())
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
        em.createQuery("delete from EstadoEntity").executeUpdate();
        em.createQuery("delete from ProduccionEntity").executeUpdate();
    }

  
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EstadoEntity emisiones = factory.manufacturePojo(EstadoEntity.class);
            em.persist(emisiones);
            estData.add(emisiones);
        }
        for (int i = 0; i < 3; i++) {
            ProduccionEntity entity = factory.manufacturePojo(ProduccionEntity.class);
            em.persist(entity);
            data.add(entity);
            
            estData.get(i).setProduccion(entity);
            
        }
    }
    
    @Test
    public void agregarProduccionTest(){
        ProduccionEntity produccion = data.get(1);
        EstadoEntity estado = estData.get(0);
        ProduccionEntity resp = estProdLogic.agregarProduccion(estado.getId(), produccion.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getId(), produccion.getId());
    }
    
    @Test
    public void obtenerProduccionTest(){
        ProduccionEntity prod = data.get(0);
        EstadoEntity est = estData.get(0);
        estProdLogic.agregarProduccion(prod.getId(), est.getId());
        ProduccionEntity resp = estProdLogic.obtenerProduccion(est.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getId(), prod.getId());
    }

    @Test
    public void actualizarProduccionTest() throws BusinessLogicException{
        EstadoEntity entity = estData.get(0);
        estProdLogic.actualizarProduccion(entity.getId(), data.get(1));
        entity = estLogic.getEstadoPorId(entity.getId());
        Assert.assertEquals(entity.getProduccion(), data.get(1));
    }
    
    @Test
    public void eliminarProduccionTest() throws BusinessLogicException{
        estProdLogic.eliminarProduccion(estData.get(0).getId());
        EstadoEntity response = estLogic.getEstadoPorId(estData.get(0).getId());
        Assert.assertNull(response.getProduccion());
    }
    
}
