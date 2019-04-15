/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.EmisionLogic;
import co.edu.uniandes.csw.foros.ejb.EmisionDiasLogic;
import co.edu.uniandes.csw.foros.entities.DiaEntity;
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
public class EmisionDiasLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private EmisionLogic emLogic;
    
    @Inject
    private EmisionDiasLogic emDiasLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<EmisionEntity> data = new ArrayList<>();
    
    private List<DiaEntity> diasData = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class) 
                .addPackage(EmisionEntity.class.getPackage())
                .addPackage(EmisionLogic.class.getPackage())
                .addPackage(EmisionPersistence.class.getPackage())
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
        em.createQuery("delete from DiaEntity").executeUpdate();
        em.createQuery("delete from EmisionEntity").executeUpdate();
    }
    
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DiaEntity dias = factory.manufacturePojo(DiaEntity.class);
            em.persist(dias);
            diasData.add(dias);
        }
        for (int i = 0; i < 3; i++) {
            EmisionEntity entity = factory.manufacturePojo(EmisionEntity.class);
            em.persist(entity);
            data.add(entity);
            
            diasData.get(i).setEmision(entity);
        }
    }
    
    @Test
    public void agregarDiasTest(){
        EmisionEntity entity = data.get(0);
        DiaEntity diaEntity = diasData.get(1);
        DiaEntity response = emDiasLogic.agregarDia(entity.getId(), diaEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(diaEntity.getId(), response.getId());
    }
    
    @Test
    public void obtenerDiasTest(){
         if(data.size()>0){
          EmisionEntity entity = data.get(0);
        DiaEntity diaEntity = diasData.get(1);
        DiaEntity response = emDiasLogic.agregarDia(entity.getId(), diaEntity.getId());
        List<DiaEntity> dias = emDiasLogic.obtenerDias(response.getId());
        Assert.assertEquals(dias.size(), 1);
         }
    }
    
    @Test
    public void obtenerDiaTest(){
          if(data.size()>0){
               EmisionEntity entity = data.get(0);
        DiaEntity diaEntity = diasData.get(1);
        DiaEntity response = emDiasLogic.agregarDia(entity.getId(), diaEntity.getId());
        if(response!=null){
            DiaEntity dia = diasData.get(0);
        DiaEntity resultado = emDiasLogic.obtenerDia(response.getId(), dia.getId());
        Assert.assertEquals(resultado.getId(), dia.getId());
        Assert.assertEquals(resultado.getNombre(), dia.getNombre());
          }
       
    }
        }
        
    
    @Test
    public void actualizarDiasTest() throws BusinessLogicException{
        if(data.size()>0){
            EmisionEntity entity = data.get(0);
            List<DiaEntity> list = diasData.subList(1, 2);
            emDiasLogic.actualizarDias(entity.getId(), list);
            entity = emLogic.getEmisionPorId(entity.getId());
             Assert.assertEquals(list.size(), entity.getDias().size());
        }
    }
    
    @Test
    public void eliminarDiaTest(){
        if(diasData.size()>0){
         DiaEntity dia = diasData.get(0);
        emDiasLogic.eliminarDia(data.get(0).getId(), dia.getId());
        Assert.assertFalse(data.get(0).getDias().contains(dia));
        }
    }
}
