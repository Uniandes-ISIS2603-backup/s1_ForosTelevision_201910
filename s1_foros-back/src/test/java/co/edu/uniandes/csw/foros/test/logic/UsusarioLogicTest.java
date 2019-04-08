package co.edu.uniandes.csw.foros.test.logic;

import co.edu.uniandes.csw.foros.ejb.UsuarioLogic;
import co.edu.uniandes.csw.foros.entities.UsuarioEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.UsuarioPersistence;
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
        newEntity.setClave("claveUser2020201");
        newEntity.setPrivilegio(UsuarioEntity.Acceso.USUARIO);
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
        newEntity.setClave("123");
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
        masterEntity.setClave("asloa12");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        UsuarioEntity seguidorEntity = factory.manufacturePojo(UsuarioEntity.class);
        seguidorEntity.setEmail("seguidor.master@gmail.com");
        seguidorEntity.setClave("asloa12segu");
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
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        UsuarioEntity seguidorEntity = factory.manufacturePojo(UsuarioEntity.class);
        seguidorEntity.setEmail("seguidor.master@gmail.com");
        seguidorEntity.setClave("Moladals2aqs000");
        seguidorEntity.setPrivilegio(UsuarioEntity.Acceso.USUARIO);
        UsuarioEntity per2=usuarioLogic.crearUsuario(seguidorEntity);
        usuarioLogic.seguirUsuario(per1.getId(), per2.getId());  
    }
    
      /**
     * Metodo que verifica seguir usuario nulo
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void crearUsuarioSeguirMasterNullTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity seguidorEntity = factory.manufacturePojo(UsuarioEntity.class);
        seguidorEntity.setEmail("seguidor.master@gmail.com");
        seguidorEntity.setClave("Moladals2aqs000");
        seguidorEntity.setPrivilegio(UsuarioEntity.Acceso.USUARIO);
        UsuarioEntity per2=usuarioLogic.crearUsuario(seguidorEntity);
        usuarioLogic.seguirUsuario(-1L, per2.getId());  
    } 
    
     /**
     * Metodo que verifica seguir seguidor
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void crearUsuarioSeguirSeguidorNullTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        masterEntity.setId(Long.MIN_VALUE);
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        usuarioLogic.seguirUsuario(per1.getId(),-1L);  
    }
    
     /**
     * Metodo que verifica darUsuarios
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void darUsuariosTest() throws BusinessLogicException{
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            utx.commit();
           List<UsuarioEntity> l= usuarioLogic.getAll();
           l.isEmpty();
        } catch (Exception ex) {
            throw new BusinessLogicException("Error");
        }
        
    }
    
     /**
     * Metodo que verifica buscar usuario nulo
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void crearUsuarioFindTest() throws BusinessLogicException{
        usuarioLogic.find(-1L);
    }
    
    /**
     * Metodo que verifica la actualizacion de usuario
     * @throws BusinessLogicException 
     */
    @Test
    public void updateUsuarioTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        String email="forma.update@gmail.com";
        per1.setEmail(email);
        per1=usuarioLogic.update(per1);
        Assert.assertEquals(per1.getEmail(),email);
    }
    
    /**
     * Metodo que verifica la eliminacion
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void deleteUsuarioTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        usuarioLogic.delete(per1.getId());
        usuarioLogic.find(-1L);
    }
    
    /**
     * Metodo que verifica el ingreso a plataforma por clave
     * @throws BusinessLogicException contraseña incorrecta
     */
    @Test(expected=BusinessLogicException.class)
    public void logibUsuarioTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        usuarioLogic.login(per1.getEmail(),per1.getClave()+"fk");
    }
    
     /**
     * Metodo que verifica el ingreso a plataforma por correo
     * @throws BusinessLogicException correo no registrado
     */
    @Test(expected=BusinessLogicException.class)
    public void logibUsuarioCorreoTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        usuarioLogic.login(per1.getEmail()+"fk",per1.getClave());
    }
    
      /**
     * Metodo que verifica seguir seguidor
     * @throws BusinessLogicException 
     */
    @Test
    public void usuarioSeguidos() throws BusinessLogicException{
       PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        UsuarioEntity seguidorEntity = factory.manufacturePojo(UsuarioEntity.class);
        seguidorEntity.setEmail("seguidor.master@gmail.com");
        seguidorEntity.setClave("Moladals2aqs000");
        seguidorEntity.setPrivilegio(UsuarioEntity.Acceso.USUARIO);
        UsuarioEntity per2=usuarioLogic.crearUsuario(seguidorEntity);
        usuarioLogic.seguirUsuario(per1.getId(), per2.getId());
        List<UsuarioEntity> seguidos= usuarioLogic.darUsuariosFavoritos(per1.getId());
        Assert.assertNotNull(seguidos.get(0));
    }
     /**
     * Metodo registrar productores favoritos
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void registrarProductoraFavoritos() throws BusinessLogicException{
       PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        usuarioLogic.registrarProductoraFavorito(per1.getId(),-1L);
    }
    
     /**
     * Metodo eliminar productores favoritos
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void eliminarProductoraFavoritos() throws BusinessLogicException{
       PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        usuarioLogic.eliminarProductoraFavorito(per1.getId(),-1L);
    }
    
     /**
     * Metodo retorna productores favoritos
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void darProductoraFavoritos() throws BusinessLogicException{
       PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        usuarioLogic.darProductoraFavoritas(per1.getId());
    }
     /**
     * Metodo registrar emisiones favoritos
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void registrarEmisionFavoritos() throws BusinessLogicException{
       PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        usuarioLogic.registrarEmision(per1.getId(),-1L);
    }
    
     /**
     * Metodo eliminar emisiones favoritos
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void eliminarEmisionesFavoritos() throws BusinessLogicException{
       PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        usuarioLogic.eliminarEmision(per1.getId(),-1L);
    }
    
     /**
     * Metodo retorna productores favoritos
     * @throws BusinessLogicException 
     */
    @Test(expected=BusinessLogicException.class)
    public void darEmisionesFavoritos() throws BusinessLogicException{
       PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity masterEntity = factory.manufacturePojo(UsuarioEntity.class);
        masterEntity.setEmail("local.master@gmail.com");
        masterEntity.setClave("claveUser2020");
        UsuarioEntity per1=usuarioLogic.crearUsuario(masterEntity);
        usuarioLogic.darEmisiones(per1.getId());
    }
}
