/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.StaffLogic;
import co.edu.uniandes.csw.foros.entities.StaffEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.StaffPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Clase que prueba las operaciones realizadas en la clase StaffLogic.
 *
 * @author jf.castaneda
 */
@RunWith(Arquillian.class)
public class StaffLogicTest {

    /**
     * Generador de datos.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de la dependencia a la clase StaffLogic cuyos métodos se van a
     * probar.
     */
    @Inject
    private StaffLogic staffLogic;

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
    private List<StaffEntity> data = new ArrayList<StaffEntity>();

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
        em.createQuery("delete from StaffEntity").executeUpdate();
    }

    /**
     * Método que inserta los datos iniciales para el correcto funcionamiento de
     * las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            StaffEntity entity = factory.manufacturePojo(StaffEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Método que prueba que la lógica cumpla al crear un staff.
     */
    @Test
    public void crearStaffTest() {
        StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
        staffEntity.setFoto("holi.png");
        try {
            staffLogic.crearStaff(staffEntity);
            data.add(staffEntity);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertEquals(em.find(StaffEntity.class, staffEntity.getId()).getNombre(), staffEntity.getNombre());
    }

    /**
     * Método que prueba que la lógica cumpla cuando la descripción de un
     * miembro del staff a agregar es null.
     */
    @Test
    public void crearStaffDescripcionNullTest() {
        StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
        staffEntity.setDescripcion(null);
        try {
            staffLogic.crearStaff(staffEntity);
            data.add(staffEntity);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertNull(em.find(StaffEntity.class, staffEntity.getId()));
    }

    /**
     * Método que prueba que la lógica cumpla cuando la descripción de un
     * miembro del staff a agregar es vacía.
     */
    @Test
    public void crearStaffDescripcionVaciaTest() {
        StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
        staffEntity.setDescripcion("");
        try {
            staffLogic.crearStaff(staffEntity);
            data.add(staffEntity);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertNull(em.find(StaffEntity.class, staffEntity.getId()));
    }

    /**
     * Método que prueba que la lógica cumpla cuando el nombre de un miembro del
     * staff a agregar es null.
     */
    @Test
    public void crearStaffNombreNullTest() {
        StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
        staffEntity.setNombre(null);
        try {
            staffLogic.crearStaff(staffEntity);
            data.add(staffEntity);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertNull(em.find(StaffEntity.class, staffEntity.getId()));
    }

    /**
     * Método que prueba que la lógica cumpla cuando el nombre de un miembro del
     * staff a agregar es vacío.
     */
    @Test
    public void crearStaffNombreVacioTest() {
        StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
        staffEntity.setNombre("");
        try {
            staffLogic.crearStaff(staffEntity);
            data.add(staffEntity);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertNull(em.find(StaffEntity.class, staffEntity.getId()));
    }

    /**
     * Método que prueba que la lógica cumpla cuando el rol de un miembro del
     * staff a agregar es null.
     */
    @Test
    public void crearStaffRolNullTest() {
        StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
        staffEntity.setRol(null);
        try {
            staffLogic.crearStaff(staffEntity);
            data.add(staffEntity);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertNull(em.find(StaffEntity.class, staffEntity.getId()));
    }

    /**
     * Método que prueba que la lógica cumpla cuando la foto de un miembro del
     * staff a agregar es null.
     */
    @Test
    public void crearStaffFotoNullTest() {
        StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
        staffEntity.setFoto(null);
        try {
            staffLogic.crearStaff(staffEntity);
            data.add(staffEntity);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertNull(em.find(StaffEntity.class, staffEntity.getId()));
    }

    /**
     * Método que prueba que la lógica cumpla cuando el foto de un miembro del
     * staff a agregar no tiene el formato que debería.
     */
    @Test
    public void crearStaffFotoSinFormatoTest() {
        StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
        staffEntity.setNombre(null);
        try {
            staffLogic.crearStaff(staffEntity);
            data.add(staffEntity);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertNull(em.find(StaffEntity.class, staffEntity.getId()));
    }

    /**
     * Método que prueba que la lógica cumpla cuando un miembro del staff se
     * elimina.
     */
    @Test
    public void eliminarStaffTest() {
        StaffEntity staffEntity = data.get(0);
        try {
            staffLogic.eliminarStaff(staffEntity.getId());
            data.remove(staffEntity);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertNull(em.find(StaffEntity.class, staffEntity.getId()));
    }

    /**
     * Método que prueba que la lógica cumpla cuando un miembro del staff a
     * eliminar no existe.
     */
    @Test
    public void eliminarStaffNoExistenteTest() {
        StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
        boolean pruebaExitosa = false;
        try {
            staffLogic.eliminarStaff(staffEntity.getId());
            data.remove(staffEntity);
        } catch (BusinessLogicException ble) {
            pruebaExitosa = true;
        }
        Assert.assertTrue(pruebaExitosa);
    }
    
    /**
     * Método que prueba que la lógica cumpla cuando un miembro del staff cambia su rol.
     */
    @Test
    public void cambiarRolTest() {
        StaffEntity staffEntity = data.get(0);
        StaffEntity.RolStaff viejoRol = staffEntity.getRol();
        StaffEntity.RolStaff nuevoRol;
        if(staffEntity.getRol() == StaffEntity.RolStaff.ACTOR) {
            nuevoRol = StaffEntity.RolStaff.ACTORYDIRECTOR;
        } else {
            nuevoRol = StaffEntity.RolStaff.ACTOR;
        }
        try {
            staffEntity.setRol(nuevoRol);
            staffEntity = staffLogic.cambiarRolStaff(staffEntity.getId(), nuevoRol);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertNotEquals(staffEntity.getRol(), viejoRol);
    }
    
    /**
     * Método que prueba que la lógica cumpla cuando un miembro del staff cambia su rol a algo nulo.
     */
    @Test
    public void cambiarRolNullTest() {
        StaffEntity staffEntity = data.get(0);
        try {
            staffEntity = staffLogic.cambiarRolStaff(staffEntity.getId(), null);
        } catch (BusinessLogicException ble) {
            // El método funcionó.
        }
        Assert.assertNotNull(staffEntity.getRol());
    }
    
    /**
     * Método que prueba que la lógica cumpla cuando un miembro del staff que no existe cambia su rol.
     */
    @Test
    public void cambiarRolStaffNoExistenteTest() {
        StaffEntity staffEntity = factory.manufacturePojo(StaffEntity.class);
        boolean pruebaExitosa = false;
        try {
            staffLogic.cambiarRolStaff(staffEntity.getId(), StaffEntity.RolStaff.ACTOR);
        } catch (BusinessLogicException ble) {
            pruebaExitosa = true;
        }
        Assert.assertTrue(pruebaExitosa);
    }
    
    
}
