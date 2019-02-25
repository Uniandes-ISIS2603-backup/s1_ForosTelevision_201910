/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.EstadoLogic;
import co.edu.uniandes.csw.foros.entities.EstadoEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.EstadoPersistence;
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
public class EstadoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private EstadoPersistence persistence;
    
    @Inject
    private EstadoLogic logic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<EstadoEntity> data = new ArrayList<>();
    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EstadoEntity.class.getPackage())
                .addPackage(EstadoPersistence.class.getPackage())
                .addPackage(EstadoLogic.class.getPackage())
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
        em.createQuery("delete from EstadoEntity").executeUpdate();
    }

 
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            EstadoEntity entity = factory.manufacturePojo(EstadoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void crearEstadoTest() throws BusinessLogicException{
        EstadoEntity nuevo = factory.manufacturePojo(EstadoEntity.class);
        nuevo.setEstado(1);
        EstadoEntity resultado = logic.createEstado(nuevo);
        Assert.assertNotNull(resultado);
        EstadoEntity emision = em.find(EstadoEntity.class, resultado.getId());
        Assert.assertEquals(resultado.getId(), emision.getId());
        Assert.assertEquals(resultado.getEstado(),emision.getEstado()); 
    }
    
    @Test
    public void obtenerEstadosTest(){
        List<EstadoEntity> estados = logic.getEstados();
        Assert.assertEquals(data.size(), estados.size());
        for(EstadoEntity estado : estados){
            boolean encontrado = false;
            for(EstadoEntity estadoData : data){
                if(estado.getId().equals(estadoData.getId())){
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }
    
    @Test
    public void obtenerEstado(){
        EstadoEntity estadoData = data.get(0);
        EstadoEntity estado = logic.getEstadoPorId(estadoData.getId());
        Assert.assertNotNull(estado);
        Assert.assertEquals(estadoData.getId(), estado.getId());
        Assert.assertEquals(estadoData.getEstado(),estado.getEstado());   
    }
    
   
    @Test
    public void actualizarEstado(){
        EstadoEntity estado = data.get(0);
        EstadoEntity estadoNuevo = factory.manufacturePojo(EstadoEntity.class);
        
        estadoNuevo.setId(estado.getId());
        logic.actualizarEstado(estadoNuevo.getId(), estadoNuevo);
        EstadoEntity respuesta = em.find(EstadoEntity.class, estado.getId());
        
        Assert.assertEquals(respuesta.getId(), estadoNuevo.getId());
        Assert.assertEquals(respuesta.getEstado(), estadoNuevo.getEstado());
    }
    
    @Test
    public void borrarEmision(){
        EstadoEntity estado = data.get(0);
        logic.borrarEstado(estado.getId());
        EstadoEntity respuesta = em.find(EstadoEntity.class, estado.getId());
        Assert.assertNull(respuesta);
    }
    
    @Test(expected=BusinessLogicException.class)
    public void reglasDeNegocio() throws BusinessLogicException{
        EstadoEntity estado = factory.manufacturePojo(EstadoEntity.class);
        estado.setEstado(-1);
        logic.createEstado(estado);
    }
}
