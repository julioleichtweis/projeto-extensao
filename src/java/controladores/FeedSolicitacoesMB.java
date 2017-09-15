package controladores;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named(value = "feedSolicitacoesMB")
@SessionScoped
public class FeedSolicitacoesMB implements Serializable {

    public FeedSolicitacoesMB() {
    }
    
}
