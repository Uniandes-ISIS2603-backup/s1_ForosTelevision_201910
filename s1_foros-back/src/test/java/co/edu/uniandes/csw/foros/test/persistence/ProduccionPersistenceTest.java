/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.persistence;

import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
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
    private List<ProduccionEntity> data = new ArrayList<>();

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
        Assert.assertNotNull(resultado);

        ProduccionEntity nuevaEntidadEnDB = em.find(ProduccionEntity.class, resultado.getId());
        Assert.assertEquals(nuevaEntidad.darNombre(), nuevaEntidadEnDB.darNombre());
    }

    /**
     * Método que prueba eliminar una producción.
     */
    @Test
    public void deleteProduccionTest() {
        ProduccionEntity entidad = data.get(0);
        produccionPersistence.delete(entidad.getId());
        ProduccionEntity entidadBorrada = em.find(ProduccionEntity.class, entidad.getId());
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
        Assert.assertEquals(entidadEnData.darNombre(), entidadEnDB.darNombre());
        Assert.assertEquals(entidadEnData.darDescripcion(), entidadEnDB.darDescripcion());
        Assert.assertEquals(entidadEnData.darClasificacionAudiencia(), entidadEnDB.darClasificacionAudiencia());
        Assert.assertEquals(entidadEnData.darCalificacionPromedio(), entidadEnDB.darCalificacionPromedio());
    }

    /**
     * Método que prueba actualizar una producción.
     */
    @Test
    public void updateProduccionTest() {
        ProduccionEntity entidadEnData = data.get(0);
        ProduccionEntity entidadDuplicada = new ProduccionEntity();
        entidadDuplicada.editarCalificacionPromedio(entidadEnData.darCalificacionPromedio());
        entidadDuplicada.editarDescripcion(entidadEnData.darDescripcion());
        entidadDuplicada.editarNombre(entidadEnData.darNombre());
        entidadDuplicada.editarClasificacionAudiencia(entidadEnData.darClasificacionAudiencia());
        PodamFactory factory = new PodamFactoryImpl();
        ProduccionEntity nuevaEntidad = factory.manufacturePojo(ProduccionEntity.class);
        entidadEnData.editarCalificacionPromedio(nuevaEntidad.darCalificacionPromedio());
        entidadEnData.editarDescripcion(nuevaEntidad.darDescripcion());
        entidadEnData.editarNombre(nuevaEntidad.darNombre());
        entidadEnData.editarClasificacionAudiencia(nuevaEntidad.darClasificacionAudiencia());
        
        ProduccionEntity entidadActualizada = produccionPersistence.update(entidadEnData);
        Assert.assertNotEquals(entidadDuplicada.getId(), entidadActualizada.getId());
        Assert.assertNotEquals(entidadDuplicada.darNombre(), entidadActualizada.darNombre());
        Assert.assertNotEquals(entidadDuplicada.darDescripcion(), entidadActualizada.darDescripcion());
        Assert.assertNotEquals(entidadDuplicada.darClasificacionAudiencia(), entidadActualizada.darClasificacionAudiencia());
        Assert.assertNotEquals(entidadDuplicada.darCalificacionPromedio(), entidadActualizada.darCalificacionPromedio());
    }

}
