/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.CanalLogic;
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
 * @author mi.carrascal
 */
@RunWith(Arquillian.class)
public class CanalLogicTest {
    
    /**
     * Inyección de la dependencia a la clase CanalLogic cuyos
     * métodos se van a probar
     */
    @Inject
    private CanalLogic canalLogic;
    @Inject
    private CanalPersistence persistence;
    @PersistenceContext
    private EntityManager em;
    @Inject
    UserTransaction utx;

  private List<CanalEntity> data = new ArrayList<>();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CanalEntity.class.getPackage())
                .addPackage(CanalLogic.class.getPackage())
                .addPackage(CanalPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
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
//    
       /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from CanalEntity").executeUpdate();
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
//    
//    
//    /**
//     *
//     * @throws BusinessLogicException
//     */
    @Test
    public void createCanalTest() throws BusinessLogicException
    {
        PodamFactory factory = new PodamFactoryImpl();
        CanalEntity newCanalEntity=factory.manufacturePojo(CanalEntity.class);
        CanalEntity result= canalLogic.createCanal(newCanalEntity);
        Assert.assertNotNull(result);
        System.out.println("result es " + result.getNombre());
        CanalEntity entity=em.find(CanalEntity.class,result.getId());
        Assert.assertEquals(newCanalEntity.getId(),entity.getId());
//        Assert.assertEquals(newCanalEntity.getNombre(),entity.getNombre());
  }
//    
    @Test(expected=BusinessLogicException.class)
    public void createCanalConNombre() throws BusinessLogicException
    {
       PodamFactory factory = new PodamFactoryImpl();
        CanalEntity newCanalEntity =factory.manufacturePojo(CanalEntity.class);
         newCanalEntity.setNombre(data.get(0).getNombre());
        canalLogic.createCanal(newCanalEntity);
    }
    
    
//  
  

    
}
