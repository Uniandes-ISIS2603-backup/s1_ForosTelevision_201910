/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.CapituloLogic;
import co.edu.uniandes.csw.foros.entities.CapituloEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CapituloPersistence;
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
public class CapituloLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CapituloPersistence persistence;
    
    @Inject
    private CapituloLogic logic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<CapituloEntity> data = new ArrayList<>();
    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CapituloEntity.class.getPackage())
                .addPackage(CapituloPersistence.class.getPackage())
                .addPackage(CapituloLogic.class.getPackage())
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
        em.createQuery("delete from CapituloEntity").executeUpdate();
    }

 
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CapituloEntity entity = factory.manufacturePojo(CapituloEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void crearCapituloTest() throws BusinessLogicException{
        CapituloEntity nuevo = factory.manufacturePojo(CapituloEntity.class);
        CapituloEntity resultado = logic.createCapitulo(nuevo);
        Assert.assertNotNull(resultado);
        CapituloEntity capitulo = em.find(CapituloEntity.class, resultado.getId());
        Assert.assertEquals(resultado.getId(), capitulo.getId());
        Assert.assertEquals(resultado.getNombre(), capitulo.getNombre());
        Assert.assertEquals(resultado.getDuracion(), capitulo.getDuracion());
    }
    
    @Test
    public void obtenerCapitulosTest(){
        List<CapituloEntity> capitulos = logic.getCapitulos();
        Assert.assertEquals(data.size(), capitulos.size());
        for(CapituloEntity capitulo : capitulos){
            boolean encontrado = false;
            for(CapituloEntity capituloData : data){
                if(capitulo.getId().equals(capituloData.getId())){
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }
    
    @Test
    public void obtenerCapitulo(){
        CapituloEntity capituloData = data.get(0);
        CapituloEntity capitulo = logic.getCapituloPorId(capituloData.getId());
        Assert.assertNotNull(capitulo);
        Assert.assertEquals(capituloData.getId(), capitulo.getId());
        Assert.assertEquals(capituloData.getNombre(), capitulo.getNombre());
        Assert.assertEquals(capituloData.getDuracion(), capitulo.getDuracion());   
    }
    
    @Test
    public void obtenerCapituloNombre(){
        CapituloEntity capituloData = data.get(0);
        CapituloEntity capitulo = logic.getCapituloPorNombre(capituloData.getNombre());
        Assert.assertNotNull(capitulo);
        Assert.assertEquals(capituloData.getId(), capitulo.getId());
        Assert.assertEquals(capituloData.getNombre(), capitulo.getNombre());
        Assert.assertEquals(capituloData.getDuracion(), capitulo.getDuracion());   
    }
    
    @Test
    public void actualizarCapitulo(){
        CapituloEntity capitulo = data.get(0);
        CapituloEntity capituloNuevo = factory.manufacturePojo(CapituloEntity.class);
        
        capituloNuevo.setId(capitulo.getId());
        logic.actualizarCapitulo(capituloNuevo.getId(), capituloNuevo);
        CapituloEntity respuesta = em.find(CapituloEntity.class, capitulo.getId());
        
        Assert.assertEquals(respuesta.getId(), capituloNuevo.getId());
        Assert.assertEquals(respuesta.getNombre(), capituloNuevo.getNombre());
        Assert.assertEquals(respuesta.getDuracion(), capituloNuevo.getDuracion());
    }
    
    @Test
    public void borrarCapitulo(){
        CapituloEntity capitulo = data.get(0);
        logic.borrarCapitulo(capitulo.getId());
        CapituloEntity respuesta = em.find(CapituloEntity.class, capitulo.getId());
        Assert.assertNull(respuesta);
    }
    
    @Test(expected=BusinessLogicException.class)
    public void reglasDeNegocio() throws BusinessLogicException{
        CapituloEntity capitulo = factory.manufacturePojo(CapituloEntity.class);
        capitulo.setDescripcion("");
        logic.createCapitulo(capitulo);
        
        CapituloEntity capitulo1 = factory.manufacturePojo(CapituloEntity.class);
        capitulo1.setNombre("");
        logic.createCapitulo(capitulo1);
        
        CapituloEntity capitulo2 = factory.manufacturePojo(CapituloEntity.class);
        capitulo2.setDuracion(-1);
        logic.createCapitulo(capitulo2);
    }
}
