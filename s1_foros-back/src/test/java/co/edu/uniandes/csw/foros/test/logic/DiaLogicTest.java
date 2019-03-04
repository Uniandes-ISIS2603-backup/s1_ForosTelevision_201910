/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.DiaLogic;
import co.edu.uniandes.csw.foros.entities.DiaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
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
public class DiaLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private DiaPersistence persistence;
    
    @Inject
    private DiaLogic logic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<DiaEntity> data = new ArrayList<>();
    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DiaEntity.class.getPackage())
                .addPackage(DiaPersistence.class.getPackage())
                .addPackage(DiaLogic.class.getPackage())
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
    public void createDiaTest() throws BusinessLogicException{
        DiaEntity dia = factory.manufacturePojo(DiaEntity.class);
        dia.setNombre("Lunes");
        DiaEntity prueba = logic.createDia(dia);
        Assert.assertNotNull(prueba);
        DiaEntity resultado = em.find(DiaEntity.class, dia.getId());
        Assert.assertEquals(prueba.getId(), resultado.getId());
        Assert.assertEquals(prueba.getNombre(), resultado.getNombre());
    }
    
    @Test
    public void getDiasTest(){
        List<DiaEntity> dias = logic.getDias();
        Assert.assertEquals(data.size(), dias.size());
        for(DiaEntity diaList : dias){
            boolean esta = false;
            for(DiaEntity diaData : data){
                if(diaList.getId().equals(diaData.getId())){
                    esta = true;
                }
            }
            Assert.assertTrue(esta);
        }
    }
    
    @Test
    public void getDiaPorIdTest(){
        DiaEntity dia = data.get(0);
        DiaEntity respuesta = logic.getDiaPorId(dia.getId());
        Assert.assertEquals(respuesta.getId(), dia.getId());
        Assert.assertEquals(respuesta.getNombre(), dia.getNombre());
    }
    
    @Test
    public void actualizarDiaTest(){
        DiaEntity dia = data.get(0);
        dia.setNombre("Martes");
        DiaEntity respuesta = logic.actualizarDia(dia.getId(), dia);
        Assert.assertEquals(respuesta.getId(), dia.getId());
        Assert.assertEquals(respuesta.getNombre(), dia.getNombre());
    }
    
    @Test
    public void borrarDiaTest(){
        DiaEntity dia = data.get(0);
        logic.borrarDia(dia.getId());
        Assert.assertNull(em.find(DiaEntity.class, dia.getId()));
    }
    
    @Test(expected=BusinessLogicException.class)
    public void reglasDeNegocioTest() throws BusinessLogicException{
        DiaEntity dia = factory.manufacturePojo(DiaEntity.class);
        dia.setNombre("Test");
        logic.createDia(dia);
    }
    
}
