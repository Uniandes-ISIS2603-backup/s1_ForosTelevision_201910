package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.ProduccionLogic;
import co.edu.uniandes.csw.foros.ejb.ResenaLogic;
import co.edu.uniandes.csw.foros.ejb.ResenaUsuarioLogic;
import co.edu.uniandes.csw.foros.ejb.UsuarioLogic;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.enums.ClasificacionAudiencia;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ResenaPersistence;
import co.edu.uniandes.csw.foros.persistence.UsuarioPersistence;
import java.util.ArrayList;
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
    private ProduccionLogic produccionLogic;

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
        ResenaEntity resenaEntity = crear();
        resenaEntity.setCalificacionProduccion(1);
        resenaEntity.setCalificacionResena(1);
        Assert.assertNotNull(resenaEntity.getUsuarioResena());
    }
    
    private ResenaEntity crear() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        ResenaEntity resenaEntity = factory.manufacturePojo(ResenaEntity.class);
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        usuario.setEmail("juan"+c+"@gmail.com");
        usuario.setClave("claveUser2020201");
        usuario.setPrivilegio(UsuarioEntity.Acceso.USUARIO);
        ProduccionEntity produccion = factory.manufacturePojo(ProduccionEntity.class);
        produccion.setCalificacionPromedio(0);
        produccion.setClasificacionAudiencia(ClasificacionAudiencia.FAMILIAR);
        usuarioLogic.crearUsuario(usuario);
        produccionLogic.crearProduccion(produccion);
        resenaEntity.setUsuarioResena(usuario);
        resenaEntity.setProduccionResena(produccion);
        return resenaEntity;
    }

}
