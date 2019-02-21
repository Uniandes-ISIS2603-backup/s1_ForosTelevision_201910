/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.StaffEntity;
import co.edu.uniandes.csw.foros.persistence.StaffPersistence;
import java.util.ArrayList;
import java.util.Iterator;
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
 * Test de la clase persistencia de un miembro del staff.
 * 
 * @author jf.castaneda
 */
@RunWith(Arquillian.class)
public class StaffPersistenceTest {
    
    /**
     * Inyección de la dependencia a la clase StaffPersistence cuyos métodos se van a probar.
     */
    @Inject
    private StaffPersistence staffPersistence;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba.
     */
    private final List<StaffEntity> data = new ArrayList<>();

    /**
     * Método que despliega el ambiente de Arquillian y Glassfish.
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Staff, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(StaffEntity.class.getPackage())
                .addPackage(StaffPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Método que establece la configuración inicial de la prueba.
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
     * Método que limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("DELETE FROM StaffEntity").executeUpdate();
    }

    /**
     * Método que inserta los datos iniciales para el correcto funcionamiento de
     * las pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            StaffEntity entity = factory.manufacturePojo(StaffEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Método que prueba crear un miembro del staff.
     */
    @Test
    public void createStaffTest() {
        PodamFactory factory = new PodamFactoryImpl();
        StaffEntity nuevaEntidad = factory.manufacturePojo(StaffEntity.class);
        StaffEntity resultado = staffPersistence.create(nuevaEntidad);
        int tamanoOriginal = data.size();
        data.add(nuevaEntidad);
        Assert.assertEquals(tamanoOriginal + 1, data.size());
        Assert.assertNotNull(resultado);

        StaffEntity nuevaEntidadEnDB = em.find(StaffEntity.class, resultado.getId());
        Assert.assertEquals(nuevaEntidad.getNombre(), nuevaEntidadEnDB.getNombre());
    }

    /**
     * Método que prueba eliminar un miembro del staff.
     */
    @Test
    public void deleteStaffTest() {
        StaffEntity entidad = data.get(0);
        staffPersistence.delete(entidad.getId());
        StaffEntity entidadBorrada = em.find(StaffEntity.class, entidad.getId());
        StaffEntity remove = data.remove(0);
        Assert.assertFalse(data.contains(remove));
        Assert.assertNull(entidadBorrada);
    }

    /**
     * Método que prueba encontrar un miembro del staff.
     */
    @Test
    public void findStaffTest() {
        StaffEntity entidadEnData = data.get(0);
        StaffEntity entidadEnDB = staffPersistence.find(entidadEnData.getId());
        Assert.assertNotNull(entidadEnDB);
        Assert.assertEquals(entidadEnData.getId(), entidadEnDB.getId());
        Assert.assertEquals(entidadEnData.getNombre(), entidadEnDB.getNombre());
        Assert.assertEquals(entidadEnData.getDescripcion(), entidadEnDB.getDescripcion());
        Assert.assertEquals(entidadEnData.getRol(), entidadEnDB.getRol());
        Assert.assertEquals(entidadEnData.getFoto(), entidadEnDB.getFoto());
    }
    
    /**
     * Método que prueba retornar todos los miembros del staff.
     */
    @Test
    public void getAllStaffTest() {
        List<StaffEntity> staffesEnDB = staffPersistence.getAll();
        Iterator<StaffEntity> iteradorStaffesEnDB = staffesEnDB.iterator();
        while(iteradorStaffesEnDB.hasNext()) {
            Assert.assertTrue(data.contains(iteradorStaffesEnDB.next()));
        }
    }

    /**
     * Método que prueba actualizar un miembro del staff.
     */
    @Test
    public void updateStaffTest() {
        StaffEntity entidadEnData = data.get(0);
        StaffEntity entidadDuplicada = new StaffEntity();
        entidadDuplicada.setRol(entidadEnData.getRol());
        entidadDuplicada.setDescripcion(entidadEnData.getDescripcion());
        entidadDuplicada.setNombre(entidadEnData.getNombre());
        entidadDuplicada.setFoto(entidadEnData.getFoto());
        PodamFactory factory = new PodamFactoryImpl();
        StaffEntity nuevaEntidad = factory.manufacturePojo(StaffEntity.class);
        entidadEnData.setRol(nuevaEntidad.getRol());
        entidadEnData.setDescripcion(nuevaEntidad.getDescripcion());
        entidadEnData.setNombre(nuevaEntidad.getNombre());
        entidadEnData.setFoto(nuevaEntidad.getFoto());
        
        StaffEntity entidadActualizada = staffPersistence.update(entidadEnData);
        
        Assert.assertNotEquals(entidadDuplicada.getNombre(), entidadActualizada.getNombre());
        Assert.assertNotEquals(entidadDuplicada.getDescripcion(), entidadActualizada.getDescripcion());
        Assert.assertNotEquals(entidadDuplicada.getFoto(), entidadActualizada.getFoto());
    }
}
