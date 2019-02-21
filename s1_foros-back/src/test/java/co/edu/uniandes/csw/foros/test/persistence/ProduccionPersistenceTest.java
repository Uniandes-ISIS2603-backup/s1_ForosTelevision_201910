/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
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
 * Tests de la clase persistencia de una producción.
 *
 * @author jf.castaneda
 */
@RunWith(Arquillian.class)
public class ProduccionPersistenceTest {

    /**
     * Inyección de la dependencia a la clase ProduccionPersistence cuyos
     * métodos se van a probar.
     */
    @Inject
    private ProduccionPersistence produccionPersistence;

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
    private final List<ProduccionEntity> data = new ArrayList<>();

    /**
     * Método que despliega el ambiente de Arquillian y Glassfish.
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Produccion, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProduccionEntity.class.getPackage())
                .addPackage(ProduccionPersistence.class.getPackage())
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
        em.createQuery("DELETE FROM ProduccionEntity").executeUpdate();
    }

    /**
     * Método que inserta los datos iniciales para el correcto funcionamiento de
     * las pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ProduccionEntity entity = factory.manufacturePojo(ProduccionEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Método que prueba crear una producción.
     */
    @Test
    public void createProduccionTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ProduccionEntity nuevaEntidad = factory.manufacturePojo(ProduccionEntity.class);
        ProduccionEntity resultado = produccionPersistence.create(nuevaEntidad);
        int tamanoOriginal = data.size();
        data.add(nuevaEntidad);
        Assert.assertEquals(tamanoOriginal + 1, data.size());
        Assert.assertNotNull(resultado);

        ProduccionEntity nuevaEntidadEnDB = em.find(ProduccionEntity.class, resultado.getId());
        Assert.assertEquals(nuevaEntidad.getNombre(), nuevaEntidadEnDB.getNombre());
    }

    /**
     * Método que prueba eliminar una producción.
     */
    @Test
    public void deleteProduccionTest() {
        ProduccionEntity entidad = data.get(0);
        produccionPersistence.delete(entidad.getId());
        ProduccionEntity entidadBorrada = em.find(ProduccionEntity.class, entidad.getId());
        ProduccionEntity remove = data.remove(0);
        Assert.assertFalse(data.contains(remove));
        Assert.assertNull(entidadBorrada);
    }

    /**
     * Método que prueba encontrar una producción.
     */
    @Test
    public void findProduccionTest() {
        ProduccionEntity entidadEnData = data.get(0);
        ProduccionEntity entidadEnDB = produccionPersistence.find(entidadEnData.getId());
        Assert.assertNotNull(entidadEnDB);
        Assert.assertEquals(entidadEnData.getId(), entidadEnDB.getId());
        Assert.assertEquals(entidadEnData.getNombre(), entidadEnDB.getNombre());
        Assert.assertEquals(entidadEnData.getDescripcion(), entidadEnDB.getDescripcion());
        Assert.assertEquals(entidadEnData.getClasificacionAudiencia(), entidadEnDB.getClasificacionAudiencia());
        Assert.assertEquals(entidadEnData.getCalificacionPromedio(), entidadEnDB.getCalificacionPromedio());
    }

    /**
     * Método que prueba retornar todas las producciones.
     */
    @Test
    public void getAllProduccionTest() {
        List<ProduccionEntity> produccionesEnDB = produccionPersistence.getAll();
        Iterator<ProduccionEntity> iteradorProduccionesEnDB = produccionesEnDB.iterator();
        while (iteradorProduccionesEnDB.hasNext()) {
            Assert.assertTrue(data.contains(iteradorProduccionesEnDB.next()));
        }
    }

    /**
     * Método que prueba actualizar una producción.
     */
    @Test
    public void updateProduccionTest() {
        ProduccionEntity entidadEnData = data.get(0);
        ProduccionEntity entidadDuplicada = new ProduccionEntity();
        entidadDuplicada.setCalificacionPromedio(entidadEnData.getCalificacionPromedio());
        entidadDuplicada.setDescripcion(entidadEnData.getDescripcion());
        entidadDuplicada.setNombre(entidadEnData.getNombre());
        entidadDuplicada.setClasificacionAudiencia(entidadEnData.getClasificacionAudiencia());
        PodamFactory factory = new PodamFactoryImpl();
        ProduccionEntity nuevaEntidad = factory.manufacturePojo(ProduccionEntity.class);
        entidadEnData.setCalificacionPromedio(nuevaEntidad.getCalificacionPromedio());
        entidadEnData.setDescripcion(nuevaEntidad.getDescripcion());
        entidadEnData.setNombre(nuevaEntidad.getNombre());
        entidadEnData.setClasificacionAudiencia(nuevaEntidad.getClasificacionAudiencia());

        ProduccionEntity entidadActualizada = produccionPersistence.update(entidadEnData);

        Assert.assertNotEquals(entidadDuplicada.getNombre(), entidadActualizada.getNombre());
        Assert.assertNotEquals(entidadDuplicada.getDescripcion(), entidadActualizada.getDescripcion());
        Assert.assertNotEquals(entidadDuplicada.getCalificacionPromedio(), entidadActualizada.getCalificacionPromedio());
    }

}
