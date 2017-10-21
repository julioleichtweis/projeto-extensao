package controladores;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import modelos.Comentario;
import modelos.Imagem;
import modelos.Lotacao;
import modelos.Requerente;
import modelos.Solicitacao;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import persistencia.DAO;

@Named(value = "admGerenciarSolicitacoesMB")
@SessionScoped
public class AdmGerenciarSolicitacoesMB implements Serializable{

    Solicitacao solicitacao;
    Imagem imagem;
    Comentario comentario;
    Requerente requerente;
    Lotacao lotacao;
    DAO<Solicitacao> solicitacaoDAO;
    DAO<Comentario> comentarioDAO;
    DAO<Lotacao> lotacaoDAO;
    List<Solicitacao> lista;
    List<Comentario> lista_comentarios;
    List<StreamedContent> lista_fotos;
    List<Lotacao> lista_lotacoes;
    
    DAO<Imagem> imagemDAO;
   
    StreamedContent image;
    

    int x = 0;
    
    public AdmGerenciarSolicitacoesMB() {
    }
    
    @PostConstruct
    public void inicializar(){
        solicitacao = new Solicitacao();
        comentario = new Comentario();
        imagem = new Imagem();
        requerente = new Requerente();
        lotacao = new Lotacao();
        solicitacaoDAO = new DAO<>("Projeto-ExtensaoPU");
        comentarioDAO = new DAO<>("Projeto-ExtensaoPU");
        lotacaoDAO = new DAO<>("Projeto-ExtensaoPU");
        lista = new ArrayList<>();
        lista_comentarios = new ArrayList<>();
        lista_fotos = new ArrayList<>();
        lista_lotacoes = new ArrayList<>();
        imagemDAO = new DAO<>("Projeto-ExtensaoPU"); 
        lista = solicitacaoDAO.getAll(Solicitacao.class, "Solicitacao.findAll");
        lista_lotacoes = lotacaoDAO.getAll(Lotacao.class, "Lotacao.findAll");
    }

    @PreDestroy
    public void fechar() {
        solicitacaoDAO.close();
        imagemDAO.close();
        comentarioDAO.close();
    }

    public Lotacao getLotacao() {
        return lotacao;
    }

    public void setLotacao(Lotacao lotacao) {
        this.lotacao = lotacao;
    }

    public List<Lotacao> getLista_lotacoes() {
        return lista_lotacoes;
    }

    public void setLista_lotacoes(List<Lotacao> lista_lotacoes) {
        this.lista_lotacoes = lista_lotacoes;
    }

    public Requerente getRequerente() {
        return requerente;
    }

    public void setRequerente(Requerente requerente) {
        this.requerente = requerente;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public List<StreamedContent> getLista_fotos() {
        return lista_fotos;
    }

    public void setLista_fotos(List<StreamedContent> lista_fotos) {
        this.lista_fotos = lista_fotos;
    }

    public List<Solicitacao> getLista() {
        return lista;
    }

    public List<Comentario> getLista_comentarios() {
        return lista_comentarios;
    }

    public void setLista_comentarios(List<Comentario> lista_comentarios) {
        this.lista_comentarios = lista_comentarios;
    }

    public void setLista(List<Solicitacao> lista) {
        this.lista = lista;
    }

    public StreamedContent getImage() {

        return image;
    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }

    public StreamedContent getFoto(Solicitacao slc){
        
        InputStream is;
        is = new ByteArrayInputStream(slc.getImagem().getDados());
        image = new DefaultStreamedContent(is);
        return image;
    }
        
    public List<Comentario> getListaComentarios(Solicitacao slc){

        lista_comentarios = comentarioDAO.getByNamedQuery(Comentario.class, "Comentario.findBySolicitacao", "solicitacao", slc);

        return lista_comentarios;        
    }

    public void atualizar() throws IOException{
        lista = solicitacaoDAO.getAll(Solicitacao.class, "Solicitacao.findAll");
    }

    public void excluirComentario(Comentario cmt){
        comentarioDAO.delete(cmt);
    }
}
