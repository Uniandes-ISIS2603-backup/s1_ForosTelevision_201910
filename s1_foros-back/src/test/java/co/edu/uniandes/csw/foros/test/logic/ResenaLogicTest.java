/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.CanalLogic;
import co.edu.uniandes.csw.foros.ejb.ResenaLogic;
import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import java.util.ArrayList;
import java.util.Date;
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
 * @author mi.carrrascal
 */
@RunWith(Arquillian.class)
public class ResenaLogicTest {
    
    /**
     * Inyección de la dependencia a la clase CanalLogic cuyos
     * métodos se van a probar
     */
    @Inject
    private ResenaLogic resenaLogic;  
   
    @PersistenceContext
    private EntityManager em;
    @Inject
    UserTransaction utx;

  private List<ResenaEntity> data = new ArrayList<>();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CanalEntity.class.getPackage())
                .addPackage(CanalPersistence.class.getPackage())
                .addPackage(CanalLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba
     * 
     */
     @Before
     public void configTest()
     {
         try
         {
             utx.begin();
             clearData();
             insertData();
             utx.commit();
 }
        catch(Exception e)
         {
             e.printStackTrace();
             try{
                 utx.rollback();
             }catch(Exception e1)
             {
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
            ResenaEntity entity = factory.manufacturePojo(ResenaEntity.class);
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
    public void createResenaTest() throws BusinessLogicException
    {
        PodamFactory factory = new PodamFactoryImpl();
        ResenaEntity newResenaEntity=factory.manufacturePojo(ResenaEntity.class);
        ResenaEntity result= resenaLogic.createResenaDescripcion(newResenaEntity);
        Assert.assertNotNull(result);
        ResenaEntity entity=em.find(ResenaEntity.class,result.getId());
        Assert.assertEquals(newResenaEntity.getId(),entity.getId());
        
  }
     
    
    
        @Test
    public void createResenaCalificacionProduccion() throws BusinessLogicException
    {
        PodamFactory factory = new PodamFactoryImpl();
        ResenaEntity newResenaEntity=factory.manufacturePojo(ResenaEntity.class);
        Integer calf=(Integer)Math.abs(newResenaEntity.getCalificacionProduccion());
        ResenaEntity result= resenaLogic.createResenacalificacionProduccion(newResenaEntity,calf);
        ResenaEntity entity=em.find(ResenaEntity.class,result.getId());
        Assert.assertEquals(newResenaEntity.getId(),entity.getId());
    }

    
    @Test
    public void createResenaCalificacionResenaTest() throws BusinessLogicException
    {
        PodamFactory factory = new PodamFactoryImpl();
        ResenaEntity newResenaEntity=factory.manufacturePojo(ResenaEntity.class);
        Integer calf=(Integer)Math.abs(newResenaEntity.getCalificacionProduccion());
        ResenaEntity result= resenaLogic.createResenacalificacionResena(newResenaEntity,calf);
        ResenaEntity entity=em.find(ResenaEntity.class,result.getId());
        Assert.assertEquals(newResenaEntity.getId(),entity.getId());
    }
    
    @Test
    public void createResenaFechaTest() throws BusinessLogicException
    {
        PodamFactory factory = new PodamFactoryImpl();
        ResenaEntity newResenaEntity=factory.manufacturePojo(ResenaEntity.class);
        Date fecha=newResenaEntity.getFecha();
        ResenaEntity result= resenaLogic.createResenaFecha(newResenaEntity, fecha);
        ResenaEntity entity=em.find(ResenaEntity.class,result.getId());
        Assert.assertEquals(newResenaEntity.getId(),entity.getId());
    }
}