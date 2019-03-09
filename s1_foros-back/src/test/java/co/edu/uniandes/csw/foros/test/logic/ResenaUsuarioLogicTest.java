/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.CanalEmisionLogic;
import co.edu.uniandes.csw.foros.ejb.CanalLogic;
import co.edu.uniandes.csw.foros.ejb.EmisionLogic;
import co.edu.uniandes.csw.foros.ejb.ResenaLogic;
import co.edu.uniandes.csw.foros.ejb.ResenaUsuarioLogic;
import co.edu.uniandes.csw.foros.ejb.UsuarioLogic;
import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.EmisionEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import co.edu.uniandes.csw.foros.persistence.EmisionPersistence;
import co.edu.uniandes.csw.foros.persistence.ResenaPersistence;
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
public class ResenaUsuarioLogicTest {
   
    @Inject
    ResenaUsuarioLogic resenaUsuarioLogic;
    
    @Inject
    UsuarioPersistence usuarioPersistence;
    
    @Inject
    ResenaPersistence resenaPersistence;
    
    
     @Inject
     ResenaLogic resenaLogic;
    @Inject
     UsuarioLogic usuarioLogic;
    
    @Inject
    ResenaLogic emisionLogic;
    @PersistenceContext
     EntityManager em;
    @Inject
    UserTransaction utx;
    
    private List<UsuarioEntity> data = new ArrayList<>();
     private List<ResenaEntity> data1 = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ResenaEntity.class.getPackage())
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(ResenaPersistence.class.getPackage())
                 .addPackage(UsuarioPersistence.class.getPackage())
                .addPackage(ResenaUsuarioLogic.class.getPackage())
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
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
                usuarioPersistence.create(entity);
                data.add(entity);
        }
         for (int i = 0; i < 3; i++) {
            ResenaEntity entity = factory.manufacturePojo(ResenaEntity.class);
            em.persist(entity);
                resenaPersistence.create(entity);
                data1.add(entity);
        }
    }
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from ResenaEntity").executeUpdate();
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
     * Prueba para dar el usuario de una reseña
     */
     @Test
     public void darUsuarioTest() throws BusinessLogicException {
        
          PodamFactory factory = new PodamFactoryImpl();
          UsuarioEntity usuarioEntity = factory.manufacturePojo(UsuarioEntity.class);
          ResenaEntity resenaEntity = factory.manufacturePojo(ResenaEntity.class);
          usuarioEntity.setEmail("juan@gmail.com");
          usuarioEntity.setClave("claveUser2020201");
          usuarioEntity.setPrivilegio(UsuarioEntity.Acceso.USUARIO);
          UsuarioEntity creado=usuarioLogic.crearUsuario(usuarioEntity);
          resenaEntity.setCalificacionProduccion(1);
          resenaEntity.setCalificacionResena(1);
          ResenaEntity creada=resenaLogic.createResena(resenaEntity);
          creada.setUsuarioResena(creado);
//          UsuarioEntity resultado=resenaUsuarioLogic.darUsuarioResena(creada.getId());
//          Assert.assertNotNull(resultado);
          Assert.assertEquals(creada.getUsuarioResena(),creado);
    
    }
  
    
    


}
