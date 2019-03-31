package co.edu.uniandes.csw.foros.ejb;


import co.edu.uniandes.csw.foros.entities.ArchivoEntity;
import co.edu.uniandes.csw.foros.entities.MultimediaEntity;
import co.edu.uniandes.csw.foros.entities.ProduccionEntity;
import co.edu.uniandes.csw.foros.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.foros.persistence.ArchivoPersistence;
import co.edu.uniandes.csw.foros.persistence.MultimediaPersistence;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author bs.rincon
 */
@Stateless 
public class MultimediaLogic {
    
    @Inject
    private MultimediaPersistence mp;
    
    @Inject
    private ArchivoPersistence ap;
    
    @Inject ProduccionLogic produccion;
    
    private static Pattern fileExtnPtrn = Pattern.compile("([^\\s]+(\\.(?i)(png|jpg))$)");
     
    public static boolean validateFileExtn(String url){
        Matcher mtch = fileExtnPtrn.matcher(url);
        return mtch.matches();
    }
    
    
    public MultimediaEntity crearRecursoMultimedia(MultimediaEntity ent)throws BusinessLogicException{
     if(ent.getPortada()==null){
       throw new BusinessLogicException("Seleccione una portada");
     }
     MultimediaEntity m = mp.create(ent);
     return m;
    }
    
    public MultimediaEntity adicionarImagen(Long idMultimedia,String archivo)throws BusinessLogicException{
        MultimediaEntity ent=mp.find(idMultimedia);
        if(ent==null){
            throw new BusinessLogicException("El recurso multimedia no existe");
        }
        if(archivo==null){
            throw new BusinessLogicException("El archivo no es valido");
        }
        ArchivoEntity arch=new ArchivoEntity();
        arch.setUrl(archivo);
        ArchivoEntity archStore= ap.create(arch);
        ent.getImagenes().add(archStore);
        return mp.update(ent);
    }
    
    public MultimediaEntity adicionarVideo(Long idMultimedia,String url)throws BusinessLogicException{
        MultimediaEntity ent=mp.find(idMultimedia);
        if(ent==null){
            throw new BusinessLogicException("El recurso multimedia no existe");
        }
        ent.setVideos(url);
        return mp.update(ent);
    }
    
    public MultimediaEntity darRecursosMultimediaProduccion(Long idProduccion) throws BusinessLogicException{
        ProduccionEntity entProd=produccion.darProduccion(idProduccion);
        return entProd.getMultimedia();
    }
    
    
}
