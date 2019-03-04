package co.edu.uniandes.csw.foros.dtos;

import java.io.Serializable;

/**
 * Util para respuestas de API
 * @author bsrincon
 */
public class UtilRespuesta<T>  implements Serializable{
    private T data;
    private int status;
    
    public UtilRespuesta(){}
    
    public UtilRespuesta(int status,T data){
        this.data=data;
        this.status=status;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
