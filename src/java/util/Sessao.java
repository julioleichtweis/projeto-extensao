package util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Sessao {
   
    public static Object getObjectSession(String object){
        return getSession().getAttribute(object);
    }
    
    public static void putObjectSession(String name, Object object){
        getSession().setAttribute(name, object);
    }
    
    public static void removeObjectSession(String object){
        getSession().removeAttribute(object);
    }
    
    public static HttpSession getSession(){
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }
    
    public static void facesMessage(Severity severity, String message){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));                  
    }
}

