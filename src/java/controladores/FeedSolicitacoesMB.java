package controladores;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import modelos.Imagem;
import modelos.Solicitacao;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import persistencia.DAO;

@Named(value = "feedSolicitacoesMB")
@SessionScoped
public class FeedSolicitacoesMB implements Serializable {

    Solicitacao solicitacao;
    DAO<Solicitacao> solicitacaoDAO;
    List<Solicitacao> lista;

    StreamedContent image;
    
    public FeedSolicitacoesMB() {
    }
    
    @PostConstruct
    public void inicializar(){
        solicitacao = new Solicitacao();
        solicitacaoDAO = new DAO<>("Projeto-ExtensaoPU");
        lista = new ArrayList<>();
        lista = solicitacaoDAO.getAll(Solicitacao.class, "Solicitacao.findAll");
    }

    @PreDestroy
    public void fechar() {
        solicitacaoDAO.close();
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public List<Solicitacao> getLista() {
        return lista;
    }

    public void setLista(List<Solicitacao> lista) {
        this.lista = lista;
    }

    public StreamedContent getImage(byte[] dados) {
        if(dados != null){
            InputStream is = new ByteArrayInputStream(dados);
            image = new DefaultStreamedContent(is);
            return image;
        }
        return null;
    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }


}


