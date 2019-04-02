package co.edu.uniandes.csw.foros.ejb;

import co.edu.uniandes.csw.foros.entities.CapituloEntity;
import co.edu.uniandes.csw.foros.entities.CategoriaEntity;
import co.edu.uniandes.csw.foros.entities.MultimediaEntity;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.entities.ProductoraEntity;
import co.edu.uniandes.csw.foros.entities.ResenaEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ProduccionPersistence;
import co.edu.uniandes.csw.foros.persistence.StaffPersistence;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author jf.castaneda, bs.rincon
 */
@Stateless 
public class ProduccionLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ProduccionLogic.class.getName());
    
    /**
     * Inyección de un objeto que trata la persistencia de las producciones.
     */
    @Inject
    private ProduccionPersistence produccionPersistence;

    /**
     * Inyección de lo capítulos.
     */
    @Inject
    private CapituloLogic capituloLogic;
    
    /**
     * Inyección de las reseñas.
     */
    @Inject
    private ResenaLogic resenaLogic;
    
    /**
     * Inyección de las categorías.
     */
    @Inject
    private CategoriaLogic categoriaLogic;
    
    /**
     * Inyección de la multimedia.
     */
    @Inject
    private MultimediaLogic multimediaLogic;
    
    /**
     * Inyección de la productora.
     */
    @Inject
    private ProductoraLogic productoraLogic;

    /**
     * Método con el cual se crea una producción.
     *
     * @param produccionEntity entidad de la producción a ser creada.
     * @return la producción recién creada.
     * @throws BusinessLogicException cuando alguno de los atributos no cumple las reglas de negocio.
     */
    public ProduccionEntity crearProduccion(ProduccionEntity produccionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la producción.");
        //comprobarReglasDeNegocioProduccion(produccionEntity);
        if(produccionEntity.getMultimedia() != null && produccionEntity.getMultimedia().getId() != null) {
            MultimediaEntity multimediaEntity = multimediaLogic.darMultimedia(produccionEntity.getMultimedia().getId());
            produccionEntity.setMultimedia(multimediaEntity);
        }
        if(produccionEntity.getProductora() != null && produccionEntity.getProductora().getId() != null) {
            ProductoraEntity productoraEntity = productoraLogic.find(produccionEntity.getProductora().getId());
            produccionEntity.setProductora(productoraEntity);
        }
        LOGGER.log(Level.INFO, "Termina proceso de creación de la producción");
        return produccionPersistence.create(produccionEntity);
    }

    /**
     * Método que retorna todas las producciones.
     *
     * @return lista con todas las producciones.
     */
    public List<ProduccionEntity> darTodasProducciones() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las producciones");
        List<ProduccionEntity> producciones = produccionPersistence.getAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las producciones");
        return producciones;
    }

    /**
     * Método que retorna una producción según su id.
     *
     * @param idProduccion id de la producción a retornar.
     * @return la entidad de la producción. Si no existe, null.
     * @throws BusinessLogicException cuando el id que entra por parámetro es nulo.
     */
    public ProduccionEntity darProduccion(Long idProduccion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la producción con id = {0}", idProduccion);
        if (idProduccion == null) {
            LOGGER.log(Level.SEVERE, "La producción con el id = {0} no existe", idProduccion);
            throw new BusinessLogicException( "Identificador no valido");
        }
        ProduccionEntity produccionEntity = produccionPersistence.find(idProduccion);
        if(produccionEntity==null)
            throw new BusinessLogicException( "La producción no existe");
        LOGGER.log(Level.INFO, "Termina proceso de consultar la producción con id = {0}", idProduccion);
        return produccionEntity;
    }

    /**
     * Método que edita la información de una producción.
     *
     * @param idProduccion el id de la producción a editar.
     * @param produccionEntity la nueva información de la producción.
     * @return la entidad de la producción editada.
     * @throws BusinessLogicException cuando alguno de los atributos no cumple con las reglas de negocio.
     */
    public ProduccionEntity editarProduccion(Long idProduccion, ProduccionEntity produccionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la producción con id = {0}", idProduccion);
        if(idProduccion == null) {
            throw new BusinessLogicException("El id de la producción debe existir");
        }
        comprobarReglasDeNegocioProduccion(produccionEntity);
        ProduccionEntity newEntity = produccionPersistence.update(produccionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el libro con id = {0}", produccionEntity.getId());
        return newEntity;
    }

    /**
     * Método que elimina una producción.
     *
     * @param idProduccion id de la producción a eliminar.
     * @return producción que se eliminó.
     * @throws BusinessLogicException si no existe la entidad.
     */
    public ProduccionEntity eliminarProduccion(Long idProduccion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminación de una producción");
        ProduccionEntity produccionEntity = produccionPersistence.find(idProduccion);
        if (produccionEntity == null) {
            throw new BusinessLogicException("No existe una producción con ese id.");
        }
        LOGGER.log(Level.INFO, "Termina proceso de eliminación del miembro del staff.");
        return produccionEntity;
    }

    /**
     * Método que comprueba las reglas de negocio.
     *
     * @param produccionEntity entidad a comprobar.
     * @throws BusinessLogicException cuando alguno de los atributos no cumple con las reglas de negocio.
     */
    private void comprobarReglasDeNegocioProduccion(ProduccionEntity produccionEntity) throws BusinessLogicException {
        // Validación atributo nombre.
        
        if(produccionEntity.getNombre() == null) {
            LOGGER.log(Level.INFO, "1");
            throw new BusinessLogicException("El nombre de la producción debe existir.");
        }
        if(produccionEntity.getNombre().equals("")) {
            LOGGER.log(Level.INFO, "2");
            throw new BusinessLogicException("El nombre de la producción no debe ser vacío.");
        }
        // Validación atributo descripción.
        if(produccionEntity.getDescripcion() == null) {
            LOGGER.log(Level.INFO, "3");
            throw new BusinessLogicException("La descripción de la producción debe existir.");
        }
        if(produccionEntity.getDescripcion().length() == 0) {
            LOGGER.log(Level.INFO, "4");
            throw new BusinessLogicException("La descripción de la producción no debe ser vacía.");
        }
        // Validación atibuto clasificación.
        if(produccionEntity.getClasificacionAudiencia() == null) {
            LOGGER.log(Level.INFO, "5");
            throw new BusinessLogicException("La clasificación de la audiencia no puede ser nula.");
        }
        // Validación atributo calificación promedio.
        if(produccionEntity.getCalificacionPromedio() < 0) {
            LOGGER.log(Level.INFO, "6");
            throw new BusinessLogicException("La calificación promedio es menor a 0.");
        }
        if(produccionEntity.getCalificacionPromedio() > 1000) {
            LOGGER.log(Level.INFO, "7");
            throw new BusinessLogicException("La calificación promedio es mayor a 1000.");
        }
        
    }
    
    /**
     * Método que elimina una reseña de una producción.
     * 
     * @param idProduccion id de la producción.
     * @param idResena id de la reseña.
     * @throws BusinessLogicException 
     */
    public void eliminarResena(Long idProduccion, Long idResena) throws BusinessLogicException {
        ProduccionEntity produccionEntity = this.darProduccion(idProduccion);
        boolean encontrado = false;
        for(ResenaEntity resenaEntity : produccionEntity.getResenas()) {
            if(Objects.equals(idResena, resenaEntity.getId())) {
                encontrado = true;
                produccionEntity.getResenas().remove(resenaEntity);
                break;
            }
        }
        if(encontrado == false) {
            throw new BusinessLogicException("No existe esa reseña dento de la producción.");
        }
    }
    
    /**
     * Retorna los capítulos de la producción.
     * 
     * @param idProduccion id de la producción a retornar sus capítulos.
     * @return una lista con los capítulos de la producción.
     * @throws BusinessLogicException si algo se pifea.
     */
    public List<CapituloEntity> darCapitulos(Long idProduccion) throws BusinessLogicException {
        List<CapituloEntity> capitulos = darProduccion(idProduccion).getCapitulos();
        if(capitulos.isEmpty()) throw  new BusinessLogicException("No hay capítulos registrados");
        return capitulos;
    }
    
    /**
     * Registra un capítulo de la producción.
     * 
     * @param idProduccion id de la producción a la que se le registrará un capítulo.
     * @param idCapitulo id del capítulo a registrar.
     * @throws BusinessLogicException 
     */
    public void registrarCapitulo(Long idProduccion, Long idCapitulo)throws BusinessLogicException{
        ProduccionEntity produccionEntity = this.darProduccion(idProduccion);
        produccionEntity.getCapitulos().add(capituloLogic.getCapituloPorId(idCapitulo));
        this.editarProduccion(produccionEntity.getId(), produccionEntity);
    }
    
    /**
     * Retorna las reseñas de la producción.
     * 
     * @param idProduccion id de la producción a retornar sus reseñas.
     * @return una lista con las reseñas de la producción.
     * @throws BusinessLogicException si algo se pifea.
     */
    public List<ResenaEntity> darResenas(Long idProduccion) throws BusinessLogicException {
        List<ResenaEntity> resenas = darProduccion(idProduccion).getResenas();
        if(resenas.isEmpty()) throw  new BusinessLogicException("No hay reseñas registradas");
        return resenas;
    }
    
    /**
     * Registra una reseña de la producción.
     * 
     * @param idProduccion id de la producción a la que se le registrará un capítulo.
     * @param idResena id de la reseña a registrar.
     * @throws BusinessLogicException 
     */
    public void registrarResena(Long idProduccion, Long idResena)throws BusinessLogicException{
        ProduccionEntity produccionEntity = this.darProduccion(idProduccion);
        produccionEntity.getResenas().add(resenaLogic.find(idResena));
        this.editarProduccion(produccionEntity.getId(), produccionEntity);
    }
    
    /**
     * Retorna las categorías de la producción.
     * 
     * @param idProduccion id de la producción a retornar sus categorías.
     * @return una lista con las categorías de la producción.
     * @throws BusinessLogicException si algo se pifea.
     */
    public List<CategoriaEntity> darCategorias(Long idProduccion) throws BusinessLogicException {
        List<CategoriaEntity> categorias = darProduccion(idProduccion).getCategorias();
        if(categorias.isEmpty()) throw  new BusinessLogicException("No hay categorías registradas");
        return categorias;
    }
    
    /**
     * Registra una categoría de la producción.
     * 
     * @param idProduccion id de la producción a la que se le registrará un capítulo.
     * @param idCategoria id de la categoría a registrar.
     * @throws BusinessLogicException 
     */
    public void registrarCategoria(Long idProduccion, Long idCategoria)throws BusinessLogicException{
        ProduccionEntity produccionEntity = this.darProduccion(idProduccion);
        produccionEntity.getCategorias().add(categoriaLogic.getCategoria(idCategoria));
        this.editarProduccion(produccionEntity.getId(), produccionEntity);
    }
    
    /**
     * Retorna la multimedia de la producción.
     * 
     * @param idProduccion id de la producción a retornar su multimedia.
     * @return la multimedia de la producción.
     * @throws BusinessLogicException si algo se pifea.
     */
    public MultimediaEntity darMultimedia(Long idProduccion) throws BusinessLogicException {
        MultimediaEntity multimedia = darProduccion(idProduccion).getMultimedia();
        if(multimedia == null) throw  new BusinessLogicException("No hay multimedia registrada");
        return multimedia;
    }
    
    /**
     * Registra una multimedia de la producción.
     * 
     * @param idProduccion id de la producción a la que se le registrará una multimedia.
     * @param idMultimedia id de la multimedia a registrar.
     * @throws BusinessLogicException 
     */
    public void registrarMultimediaNueva(Long idProduccion, Long idMultimedia) throws BusinessLogicException{
        ProduccionEntity produccionEntity = this.darProduccion(idProduccion);
        produccionEntity.setMultimedia(multimediaLogic.darMultimedia(idMultimedia));
        this.editarProduccion(produccionEntity.getId(), produccionEntity);
    }

}
