package controladores;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.BirthDate;
import org.brickred.socialauth.util.SocialAuthUtil;
import util.Sessao;

@Named(value = "loginMB")
@SessionScoped
public class LoginMB implements Serializable{

    private SocialAuthManager manager;
    private Profile profile;
    
    private final String mainURL = "http://localhost:31812/Projeto_Extensao/faces/registrar_solicitacao.xhtml";
    private final String redirectURL = "http://localhost:31812/Projeto_Extensao/faces/redirect_home.xhtml";
    private final String provider = "facebook";
        
    public LoginMB() {
    }

    public void conectarFacebook(){
        Properties props = System.getProperties();
        props.put("graph.facebook.com.consumer_key","904034986439799");
        props.put("graph.facebook.com.consumer_secret","66870b577faab3be991a77534761a367");
        props.put("graph.facebook.com.custom_permissions","public_profile,email");
        
        SocialAuthConfig conf = SocialAuthConfig.getDefault();
        try{
            conf.load(props);
            manager = new SocialAuthManager();
            manager.setSocialAuthConfig(conf);
            String URLretorno = manager.getAuthenticationUrl(provider, redirectURL);
            FacesContext.getCurrentInstance().getExternalContext().redirect(URLretorno);
        }
        catch(Exception e){
            e.getStackTrace();
        }
    }
    
    public void getPerfil() throws Exception{
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        Map <String , String> parametros = SocialAuthUtil.getRequestParametersMap(request);
        
        if(manager != null){
            AuthProvider aut = manager.connect(parametros);
            this.setProfile(aut.getUserProfile());
            Sessao.putObjectSession("perfil", getProfile());
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect(mainURL);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile; 
    }
    
}
