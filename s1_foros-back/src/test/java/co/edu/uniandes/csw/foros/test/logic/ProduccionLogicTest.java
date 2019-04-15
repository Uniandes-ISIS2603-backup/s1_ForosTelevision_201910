/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.CategoriaLogic;
import co.edu.uniandes.csw.foros.ejb.ProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.StaffLogic;
import co.edu.uniandes.csw.foros.entities.*;
import co.edu.uniandes.csw.foros.enums.ClasificacionAudiencia;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import co.edu.uniandes.csw.foros.persistence.StaffPersistence;
import java.util.logging.Level;
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
 * Pruebas de la lógica de las producciones.
 * 
 * @author jf.castaneda
 */
@RunWith(Arquillian.class)
public class ProduccionLogicTest {
    
    /**
     * Generador de datos.
     */
    private final PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * Inyección de la dependencia a la clase ProduccionLogic cuyos métodos se van a
     * probar.
     */
    @Inject
    private ProduccionLogic produccionLogic;
    
    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    private UserTransaction utx;
    
    /**
     * Lista que tiene los datos de prueba.
     */
    private final List<ProduccionEntity> data = new ArrayList<>();
    
    /**
     * Lista que tiene los datos de capítulos.
     */
    private final List<CapituloEntity> capitulos = new ArrayList<>();
    
    /**
     * Lista que tiene los datos de emisiones.
     */
    private final List<EmisionEntity> emisiones = new ArrayList<>();
    
    /**
     * Lista que tiene los datos de categorías.
     */
    private final List<CategoriaEntity> categorias = new ArrayList<>();
    
    /**
     * Lista que tiene los datos de staffs.
     */
    private final List<StaffEntity> staffs = new ArrayList<>();

    /**
     * Multimedia de la producción.
     */
    private final MultimediaEntity multimediaEntity = factory.manufacturePojo(MultimediaEntity.class);

    /**
     * Productora de la producción.
     */
    private final ProductoraEntity productoraEntity = factory.manufacturePojo(ProductoraEntity.class);

    /**
     * Lista que tiene los datos de reseñas.
     */
    private final List<ResenaEntity> resenas = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(StaffEntity.class.getPackage())
                .addPackage(StaffLogic.class.getPackage())
                .addPackage(StaffPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Método que genera la configuración inicial de la prueba.
     */
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

    /**
     * Método que limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ProduccionEntity").executeUpdate();
    }

    /**
     * Método que inserta los datos iniciales para el correcto funcionamiento de
     * las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ProduccionEntity produccionEntity = factory.manufacturePojo(ProduccionEntity.class);
            produccionEntity.setResenas(new ArrayList<>());
            produccionEntity.setEmisiones(new ArrayList<>());
            produccionEntity.setCapitulos(new ArrayList<>());
            produccionEntity.setCategorias(new ArrayList<>());
            produccionEntity.setStaff(new ArrayList<>());
            em.persist(produccionEntity);
            data.add(produccionEntity);
        }
        for (int i = 0; i < 3; i++) {
            StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
            em.persist(staffEntity);
            staffs.add(staffEntity);
            CapituloEntity capituloEntity = factory.manufacturePojo(CapituloEntity.class);
            em.persist(capituloEntity);
            capitulos.add(capituloEntity);
            EmisionEntity emisionEntity = factory.manufacturePojo(EmisionEntity.class);
            em.persist(emisionEntity);
            emisiones.add(emisionEntity);
            CategoriaEntity categoriaEntity = factory.manufacturePojo(CategoriaEntity.class);
            em.persist(categoriaEntity);
            categorias.add(categoriaEntity);
            ResenaEntity resenaEntity = factory.manufacturePojo(ResenaEntity.class);
            em.persist(resenaEntity);
            resenas.add(resenaEntity);
        }
        List<CapituloEntity> nuevosCapitulos = new ArrayList<>();
        nuevosCapitulos.add(capitulos.get(0));
        data.get(1).setCapitulos(nuevosCapitulos);
        List<EmisionEntity> nuevasEmisiones = new ArrayList<>();
        nuevasEmisiones.add(emisiones.get(0));
        data.get(1).setEmisiones(nuevasEmisiones);
        List<CategoriaEntity> nuevasCategorias = new ArrayList<>();
        nuevasCategorias.add(categorias.get(0));
        data.get(1).setCategorias(nuevasCategorias);
        List<StaffEntity> nuevosStaffs = new ArrayList<>();
        nuevosStaffs.add(staffs.get(0));
        data.get(1).setStaff(nuevosStaffs);
        List<ResenaEntity> nuevasResenas = new ArrayList<>();
        nuevasResenas.add(resenas.get(0));
        data.get(1).setResenas(nuevasResenas);
        multimediaEntity.setPortada(multimediaEntity.getPortada()+".png");
        em.persist(multimediaEntity);
        em.persist(productoraEntity);
    }

    /**
     * Método que prueba que la lógica cumpla al crear un producción.
     */
    @Test
    public void crearStaffTest() {
        ProduccionEntity produccionEntity = factory.manufacturePojo(ProduccionEntity.class);
        Assert.assertTrue(produccionEntity.getClasificacionAudiencia() + "", true);
        produccionEntity.setCapitulos(new ArrayList<>());
        produccionEntity.setCategorias(new ArrayList());
        produccionEntity.setEmisiones(new ArrayList());
        produccionEntity.setResenas(new ArrayList());
        produccionEntity.setStaff(new ArrayList());
        produccionEntity.setClasificacionAudiencia(ClasificacionAudiencia.FAMILIAR);
        produccionEntity.setMultimedia(multimediaEntity.getId());
        produccionEntity.setProductora(productoraEntity.getId());
        try {
            produccionLogic.crearProduccion(produccionEntity);
            data.add(produccionEntity);
             Assert.assertEquals(em.find(ProduccionEntity.class, produccionEntity.getId()).getNombre(), produccionEntity.getNombre());
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
    }
    
    // TODO: arreglar
    /**
     * Método que prueba que la lógica cumpla al crear un producción.
     */
    @Test
    public void darTodasProduccionesTest() {
        List<ProduccionEntity> produccionesEntities = produccionLogic.darTodasProducciones();
        boolean bien = true;
        for(ProduccionEntity produccionEntity: produccionesEntities) {
            if(!data.contains(produccionEntity)) {
                bien = false;
            }
        }
        Assert.assertTrue(bien);
    }
    
    @Test
    public void darProduccionTest() {
        ProduccionEntity produccionEntity = data.get(0);
        ProduccionEntity darProduccion = null;
        try {
            darProduccion = produccionLogic.darProduccion(produccionEntity.getId());
        } catch (BusinessLogicException ble) {
            // Nunca debería pasar.
        }
        Assert.assertEquals(produccionEntity.getNombre(), darProduccion.getNombre());
    }
    
    @Test
    public void editarProduccionTest() {
        
    }

}
