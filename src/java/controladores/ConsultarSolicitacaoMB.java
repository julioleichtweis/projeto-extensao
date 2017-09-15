package controladores;

import javax.inject.Named;
import javax.enterprise.context.Dependent;

@Named(value = "consultarSolicitacaoMB")
@Dependent
public class ConsultarSolicitacaoMB {

    private String pesquisa;
    
    public ConsultarSolicitacaoMB() {
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public void pesquisar(){
        
    }
    
}
