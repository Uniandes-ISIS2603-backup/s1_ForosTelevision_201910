/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.CanalEmisionLogic;
import co.edu.uniandes.csw.foros.ejb.CanalLogic;
import co.edu.uniandes.csw.foros.ejb.EmisionLogic;
import co.edu.uniandes.csw.foros.ejb.UsuarioLogic;
import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import co.edu.uniandes.csw.foros.persistence.EmisionPersistence;
import co.edu.uniandes.csw.foros.persistence.UsuarioPersistence;
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
public class CanalEmisionLogicTest {
    
    
    @Inject
    CanalEmisionLogic canalEmisionLogic;
    
    @Inject
    CanalPersistence canalPersistence;
    
    @Inject
    EmisionPersistence emisionPersistence;
    
    @Inject
     CanalLogic canalLogic;
    
    @Inject
    EmisionLogic emisionLogic;
    @PersistenceContext
     EntityManager em;
    @Inject
    UserTransaction utx;
    
    private List<CanalEntity> data = new ArrayList<>();
     private List<EmisionEntity> data1 = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CanalEntity.class.getPackage())
                .addPackage(EmisionEntity.class.getPackage())
                .addPackage(CanalPersistence.class.getPackage())
                 .addPackage(EmisionPersistence.class.getPackage())
                .addPackage(CanalEmisionLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CanalEntity entity = factory.manufacturePojo(CanalEntity.class);
                canalPersistence.create(entity);
                data.add(entity);
        }
         for (int i = 0; i < 3; i++) {
            EmisionEntity entity = factory.manufacturePojo(EmisionEntity.class);
            em.persist(entity);
                emisionPersistence.create(entity);
                data1.add(entity);
        }
    }
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from CanalEntity").executeUpdate();
        em.createQuery("delete from EmisionEntity").executeUpdate();
    }
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
     * Prueba para agregar una emision a un canal
     */
    @Test
    public void agregarEmisionTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
          EmisionEntity emisionEntity = factory.manufacturePojo(EmisionEntity.class);
          CanalEntity canalEntity = factory.manufacturePojo(CanalEntity.class);
          EmisionEntity resultEmision=emisionLogic.createEmision(emisionEntity);
        CanalEntity resultCanal=canalLogic.createCanal(canalEntity);
        EmisionEntity agregada=canalEmisionLogic.agregarEmision(emisionEntity.getId(),canalEntity.getId());
        EmisionEntity agregada2=canalEmisionLogic.obtenerEmision(emisionEntity.getId(),canalEntity.getId());
         Assert.assertNotNull(agregada);
        Assert.assertNotNull(agregada2);
        Assert.assertEquals(agregada2.getId(),agregada.getId());
        
    }
    
    
     /**
     * Prueba para agregar una emision a un canal
     */
    @Test
    public void eliminarEmisionTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
          EmisionEntity emisionEntity = factory.manufacturePojo(EmisionEntity.class);
          CanalEntity canalEntity = factory.manufacturePojo(CanalEntity.class);
          EmisionEntity resultEmision=emisionLogic.createEmision(emisionEntity);
        CanalEntity resultCanal=canalLogic.createCanal(canalEntity);
        EmisionEntity agregada=canalEmisionLogic.agregarEmision(resultEmision.getId(),resultCanal.getId());
        canalEmisionLogic.eliminarEmision(resultEmision.getId(),resultCanal.getId());
         Assert.assertNull(canalEmisionLogic.obtenerEmision(emisionEntity.getId(),canalEntity.getId()));        
    }
    
     /**
     * Prueba para dar todas las emisiones de un canal
     */
    @Test
    public void darEmisionesTest() throws BusinessLogicException {
        
        PodamFactory factory = new PodamFactoryImpl();
          EmisionEntity emisionEntity = factory.manufacturePojo(EmisionEntity.class);
          EmisionEntity nuevo=factory.manufacturePojo(EmisionEntity.class);
          CanalEntity canalEntity = factory.manufacturePojo(CanalEntity.class);
          EmisionEntity resultEmision=emisionLogic.createEmision(emisionEntity);
        CanalEntity resultCanal=canalLogic.createCanal(canalEntity);
        EmisionEntity agregada=canalEmisionLogic.agregarEmision(emisionEntity.getId(),canalEntity.getId());
        List<EmisionEntity> resultado=canalEmisionLogic.darEmisiones(resultCanal.getId());
        Assert.assertTrue(resultado.contains(resultEmision));
        
             
    }
    /**
     * Prueba para actualizar una emision de un canal
     */
    @Test
     public void updateEmisionTest() throws BusinessLogicException {
        
        PodamFactory factory = new PodamFactoryImpl();
          EmisionEntity emisionEntity = factory.manufacturePojo(EmisionEntity.class);
          EmisionEntity nuevo = factory.manufacturePojo(EmisionEntity.class);
          
          CanalEntity canalEntity = factory.manufacturePojo(CanalEntity.class);
          EmisionEntity resultEmision=emisionLogic.createEmision(emisionEntity);
        CanalEntity resultCanal=canalLogic.createCanal(canalEntity);
        EmisionEntity agregada=canalEmisionLogic.agregarEmision(emisionEntity.getId(),canalEntity.getId());
        EmisionEntity actualizada=canalEmisionLogic.updateEmision(resultCanal.getId(),resultEmision.getId(), nuevo);
        EmisionEntity resultado=canalEmisionLogic.obtenerEmision(actualizada.getId(),resultCanal.getId());
        Assert.assertEquals(agregada,resultado);
        
             
    }
     
     /**
     * Prueba para dar una emision de un canal
     */
     @Test
     public void darEmisionTest() throws BusinessLogicException {
        
        PodamFactory factory = new PodamFactoryImpl();
          EmisionEntity emisionEntity = factory.manufacturePojo(EmisionEntity.class);
          EmisionEntity nuevo = factory.manufacturePojo(EmisionEntity.class);
          
          CanalEntity canalEntity = factory.manufacturePojo(CanalEntity.class);
          EmisionEntity resultEmision=emisionLogic.createEmision(emisionEntity);
        CanalEntity resultCanal=canalLogic.createCanal(canalEntity);
        EmisionEntity agregada=canalEmisionLogic.agregarEmision(emisionEntity.getId(),canalEntity.getId());
        EmisionEntity resultado=canalEmisionLogic.obtenerEmision(agregada.getId(),resultCanal.getId());
        Assert.assertEquals(agregada,resultado);       
    }
    
}
