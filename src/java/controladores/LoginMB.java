package controladores;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import modelos.Funcionario;
import persistencia.DAO;
import util.Sessao;

@Named(value = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {

    Funcionario funcionario;
    DAO<Funcionario> funcionarioDAO;
    
    public LoginMB() {
    }

    @PostConstruct
    public void inicializar() {
        funcionario = new Funcionario();
        funcionarioDAO = new DAO<>("Projeto-ExtensaoPU");
    }

    @PreDestroy
    public void fechar() {

    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String autenticarLogin(){
        List funcionarios = new ArrayList<>();
        funcionarios = funcionarioDAO.getByNamedQuery(Funcionario.class, "Funcionario.findForLogin" , "matricula",funcionario.getMatricula(),"senha", funcionario.getSenha());
        if(funcionarios.isEmpty()){
            return "login";
        }
        funcionario = funcionarioDAO.get(Funcionario.class, Integer.parseInt(funcionario.getMatricula()));
        Sessao.putObjectSession("usuario", funcionario);
        return "administracao";
    }

}


