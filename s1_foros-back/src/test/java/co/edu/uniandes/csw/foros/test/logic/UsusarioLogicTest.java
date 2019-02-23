package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.UsuarioLogic;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.UsuarioPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author bsrincon
 */
@RunWith(Arquillian.class)
public class UsusarioLogicTest {
    
    @Inject
    UsuarioLogic usuarioLogic;
    
    @Inject
    private UsuarioPersistence usuarioPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
     private List<UsuarioEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
                usuarioPersistence.create(entity);
                data.add(entity);
        }
    }
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from UsuarioEntity").executeUpdate();
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
     * Prueba para crear un nuevo usuario.
     */
    @Test
    public void createUsuarioTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setEmail("local.name@gmail.com");
        newEntity.setClave("fLoe232$dlWq");
        UsuarioEntity result = usuarioLogic.crearUsuario(newEntity);
        Assert.assertNotNull(result);
        UsuarioEntity entity = em.find(UsuarioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    /**
     * Metodo que verifica que el nombre sea diferente de null
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void crearUsuarioNombreNullTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setNombre(null);
        UsuarioEntity result = usuarioLogic.crearUsuario(newEntity);
    }
    /**
     * Metodo que verfica la unicidad de nombres
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void createUsuarioNombreRepetidoTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setEmail("local.name@gmail.com");
        UsuarioEntity result = usuarioLogic.crearUsuario(newEntity);
        UsuarioEntity other = factory.manufacturePojo(UsuarioEntity.class);
        other.setEmail("localfaker.name@gmail.com");
        other.setNombre(result.getNombre());
        usuarioLogic.crearUsuario(other);
    }
     /**
     * Metodo que verifica que el email sea diferente de null
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void crearUsuarioEmailNullTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setEmail(null);
        UsuarioEntity result = usuarioLogic.crearUsuario(newEntity);
    }
    /**
     * Metodo que verfica la unicidad de email
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void createUsuarioEmailRepetidoTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setEmail("local.name@gmail.com");
        UsuarioEntity result = usuarioLogic.crearUsuario(newEntity);
        UsuarioEntity other = factory.manufacturePojo(UsuarioEntity.class);
        other.setEmail("local.name@gmail.com");
        usuarioLogic.crearUsuario(other);
    }
    
     /**
     * Metodo que verifica que el email sea un correo
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void crearUsuarioEmailCorreoTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        usuarioLogic.crearUsuario(newEntity);
    }
    
     /**
     * Metodo que verifica que la clave sea diferente de null
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void crearUsuarioClaveTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setEmail("localclave.name@gmail.com");
        newEntity.setClave(null);
        usuarioLogic.crearUsuario(newEntity);
    }
    
     /**
     * Metodo que verifica que la clave sea segura
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void crearUsuarioClaveSeguridadTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setEmail("local.clave@gmail.com");
        newEntity.setClave("1234");
        usuarioLogic.crearUsuario(newEntity);
    }
    
    /**
     * Metodo que verifica no seguirse a si mismo
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void crearUsuarioSeguirMismoTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("sfLoe232$dlWq");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        UsuarioEntity seguidorEntity = factory.manufacturePojo(UsuarioEntity.class);
        seguidorEntity.setEmail("seguidor.master@gmail.com");
        seguidorEntity.setClave("seguirfLoe232$dlWq");
        UsuarioEntity per2=usuarioLogic.crearUsuario(seguidorEntity);
        usuarioLogic.seguirUsuario(per1.getId(), per1.getId());  
    }
    
     /**
     * Metodo que verifica seguir usuario
     * @throws BusinessLogicException 
     */
    @Test
    public void crearUsuarioSeguirTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("sfLoe232$dlWq");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        UsuarioEntity seguidorEntity = factory.manufacturePojo(UsuarioEntity.class);
        seguidorEntity.setEmail("seguidor.master@gmail.com");
        seguidorEntity.setClave("seguirfLoe232$dlWq");
        UsuarioEntity per2=usuarioLogic.crearUsuario(seguidorEntity);
        usuarioLogic.seguirUsuario(per1.getId(), per2.getId());  
    }
   
    
    
    
    
}
