/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.CanalLogic;
import co.edu.uniandes.csw.foros.ejb.ProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.ResenaLogic;
import co.edu.uniandes.csw.foros.ejb.UsuarioLogic;
import co.edu.uniandes.csw.foros.entities.CanalEntity;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.enums.ClasificacionAudiencia;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.CanalPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
     * Inyección de la dependencia a la clase CanalLogic cuyos métodos se van a
     * probar
     */
    @Inject
    private ResenaLogic resenaLogic;
    @Inject
    private UsuarioLogic usuarioLogic;
    @Inject
    private ProduccionLogic produccionLogic;

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

    @Test
    public void createResenaTest() throws BusinessLogicException {
        ResenaEntity newResenaEntity = crear();
        newResenaEntity.setCalificacionProduccion(1);
        newResenaEntity.setCalificacionResena(1);
        ResenaEntity result = resenaLogic.createResena(newResenaEntity.getUsuarioResena().getId(), newResenaEntity.getProduccionResena().getId(), newResenaEntity);
        Assert.assertNotNull(result);
        ResenaEntity entity = em.find(ResenaEntity.class, result.getId());
        Assert.assertEquals(newResenaEntity.getId(), entity.getId());

    }

    @Test
    public void updateResenaTest() throws BusinessLogicException {
        ResenaEntity resenaEntity = crear();
        resenaEntity.setCalificacionProduccion(1);
        resenaEntity.setCalificacionResena(1);
        resenaEntity.setRecomendada(false);
        resenaLogic.createResena(resenaEntity.getUsuarioResena().getId(), resenaEntity.getProduccionResena().getId(), resenaEntity);
        ResenaEntity actualizada = resenaLogic.update(resenaEntity);
        Assert.assertFalse(actualizada.isRecomendada());

    }

    @Test(expected = BusinessLogicException.class)
    public void deleteResenaTest() throws BusinessLogicException {
        ResenaEntity newResenaEntity = crear();
        newResenaEntity.setCalificacionProduccion(1);
        newResenaEntity.setCalificacionResena(1);
        ResenaEntity result = resenaLogic.createResena(newResenaEntity.getUsuarioResena().getId(), newResenaEntity.getProduccionResena().getId(), newResenaEntity);
        resenaLogic.delete(result.getId());
        resenaLogic.find(result.getId());

    }

    @Test
    public void getAllResenaTest() throws BusinessLogicException {
        List<ResenaEntity> resultado1 = resenaLogic.getAll();
        Integer aux = resultado1.size();
        Integer tamL = aux;
        List<ResenaEntity> lista = new ArrayList<ResenaEntity>();
        ResenaEntity newResenaEntity = crear();
        lista.add(newResenaEntity);
        newResenaEntity.setCalificacionProduccion(1);
        newResenaEntity.setCalificacionResena(1);
        List<ResenaEntity> resultado = resenaLogic.getAll();
        Integer tamR = resultado.size();
        Assert.assertEquals(tamR.longValue(), tamL.longValue());
    }

    @Test
    public void getResenaTest() throws BusinessLogicException {
        ResenaEntity resenaEntity = crear();
        resenaEntity.setCalificacionProduccion(1);
        resenaEntity.setCalificacionResena(1);
        ResenaEntity creada = resenaLogic.createResena(resenaEntity.getUsuarioResena().getId(), resenaEntity.getProduccionResena().getId(), resenaEntity);
        ResenaEntity resultado = resenaLogic.find(creada.getId());
        Assert.assertEquals(resultado.getId(), creada.getId());
    }

    @Test(expected = BusinessLogicException.class)
    public void agregarCalificacionProduccionNegativaTest() throws BusinessLogicException {
        ResenaEntity resenaEntity = crear();
        resenaEntity.setCalificacionProduccion(-2);
        resenaLogic.createResena(resenaEntity.getUsuarioResena().getId(), resenaEntity.getProduccionResena().getId(), resenaEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void agregarCalificacionResenaNegativaTest() throws BusinessLogicException {
        ResenaEntity resenaEntity = crear();
        resenaEntity.setCalificacionResena(-2);
        resenaLogic.createResena(resenaEntity.getUsuarioResena().getId(), resenaEntity.getProduccionResena().getId(), resenaEntity);
    }

    private ResenaEntity crear() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        ResenaEntity resenaEntity = factory.manufacturePojo(ResenaEntity.class);
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        char d = (char)(r.nextInt(26) + 'a');
        usuario.setEmail("juan"+c+d+"@gmail.com");
        usuario.setClave("claveUser2020201");
        usuario.setPrivilegio(UsuarioEntity.Acceso.USUARIO);
        ProduccionEntity produccion = factory.manufacturePojo(ProduccionEntity.class);
        produccion.setCalificacionPromedio(0);
        usuarioLogic.crearUsuario(usuario);
        produccion.setClasificacionAudiencia(ClasificacionAudiencia.FAMILIAR);
        produccionLogic.crearProduccion(produccion);
        resenaEntity.setUsuarioResena(usuario);
        resenaEntity.setProduccionResena(produccion);
        return resenaEntity;
    }

}
