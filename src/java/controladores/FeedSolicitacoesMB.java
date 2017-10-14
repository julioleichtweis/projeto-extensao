package controladores;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import modelos.Comentario;
import modelos.Imagem;
import modelos.Requerente;
import modelos.Solicitacao;
import org.brickred.socialauth.Profile;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import persistencia.DAO;
import util.Sessao;

@Named(value = "feedSolicitacoesMB")
@SessionScoped
public class FeedSolicitacoesMB implements Serializable{

    Solicitacao solicitacao;
    Imagem imagem;
    Comentario comentario;
    Requerente requerente;
    DAO<Solicitacao> solicitacaoDAO;
    DAO<Comentario> comentarioDAO;
    List<Solicitacao> lista;
    List<Comentario> lista_comentarios;
    List<StreamedContent> lista_fotos;
    
    DAO<Imagem> imagemDAO;
   
    StreamedContent image;
    
    boolean novo_comentario;
    Profile perfil;

    int x = 0;
    
    public FeedSolicitacoesMB() {
    }
    
    @PostConstruct
    public void inicializar(){
        solicitacao = new Solicitacao();
        comentario = new Comentario();
        imagem = new Imagem();
        requerente = new Requerente();
        solicitacaoDAO = new DAO<>("Projeto-ExtensaoPU");
        comentarioDAO = new DAO<>("Projeto-ExtensaoPU");
        lista = new ArrayList<>();
        lista_comentarios = new ArrayList<>();
        lista_fotos = new ArrayList<>();
        imagemDAO = new DAO<>("Projeto-ExtensaoPU"); 
        novo_comentario = false;
        lista = solicitacaoDAO.getAll(Solicitacao.class, "Solicitacao.findAll");
    }

    @PreDestroy
    public void fechar() {
        solicitacaoDAO.close();
        imagemDAO.close();
        comentarioDAO.close();
    }

    public Profile getPerfil() {
        return perfil;
    }

    public void setPerfil(Profile perfil) {
        this.perfil = perfil;
    }

    public Requerente getRequerente() {
        return requerente;
    }

    public void setRequerente(Requerente requerente) {
        this.requerente = requerente;
    }

    public boolean isNovo_comentario() {
        return novo_comentario;
    }

    public void setNovo_comentario(boolean novo_comentario) {
        this.novo_comentario = novo_comentario;
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
    
    public void registrarComentario(Solicitacao slc){
        comentario.setSolicitacao(slc);
        comentario.setDataComentario(new Date(System.currentTimeMillis()));
        carregarRequerente();
        comentario.setRequerente(requerente);
        comentarioDAO.insert(comentario);
        lista_comentarios.clear();
        lista_comentarios.add(comentario);
        comentario = new Comentario();
        novo_comentario = true;
    }
    
    public List<Comentario> getListaComentarios(Solicitacao slc){
        if(!novo_comentario){
            lista_comentarios = comentarioDAO.getByNamedQuery(Comentario.class, "Comentario.findBySolicitacao", "solicitacao", slc);
            return lista_comentarios;
        }
        novo_comentario = false;

        return lista_comentarios;        
    }

    public void carregarRequerente(){

        perfil = (Profile) Sessao.getObjectSession("perfil");
        if(perfil != null){
                        
            if(perfil.getValidatedId() != null)
                requerente.setId(perfil.getValidatedId());
            
            if(perfil.getFullName() != null)
                requerente.setNome(perfil.getFullName());
            if(perfil.getEmail() != null)
                requerente.setEmail(perfil.getEmail());
            if(perfil.getDob() != null)
                requerente.setDataNasc(perfil.getDob().toString());
            if(perfil.getProfileImageURL() != null)
                requerente.setFoto(perfil.getProfileImageURL());
            if(perfil.getGender() != null)
                requerente.setSexo(perfil.getGender());
                        
        }
    }

    public void atualizar() throws IOException{
        lista = solicitacaoDAO.getAll(Solicitacao.class, "Solicitacao.findAll");
    }
    
}


