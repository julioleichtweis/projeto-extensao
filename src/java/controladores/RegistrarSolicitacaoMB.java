package controladores;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelos.Imagem;
import modelos.Localizacao;
import modelos.Solicitacao;
import modelos.Requerente;
import org.apache.commons.io.IOUtils;
import org.brickred.socialauth.Profile;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import persistencia.DAO;
import util.Sessao;

@Named(value = "registarSolicitacaoMB")
@SessionScoped
public class RegistrarSolicitacaoMB implements Serializable {

    private Requerente requerente;
    private Solicitacao solicitacao;
    private Localizacao localizacao;
    private Imagem imagem;
    
    private DAO<Requerente> requerenteDAO;
    private DAO<Solicitacao> solicitacaoDAO;
    private DAO<Localizacao> localizacaoDAO;
    private DAO<Imagem> imagemDAO;
    
    private MapModel mapa;
      
    private UploadedFile file;
    private StreamedContent image;

    private boolean confirmacao;

    public RegistrarSolicitacaoMB(){
    }

    @PostConstruct
    public void inicializar(){
        requerente = new Requerente();
        solicitacao = new Solicitacao();
        localizacao = new Localizacao();
        imagem = null;
        
        requerenteDAO = new DAO<>("Projeto-ExtensaoPU");
        solicitacaoDAO = new DAO<>("Projeto-ExtensaoPU");
        localizacaoDAO = new DAO<>("Projeto-ExtensaoPU");
        imagemDAO = new DAO<>("Projeto-ExtensaoPU");

        mapa = new DefaultMapModel();
        
        confirmacao = false;
        
        carregarRequerente();
    }

    @PreDestroy
    public void fechar() {
        requerenteDAO.close();
        solicitacaoDAO.close();
        localizacaoDAO.close();
        imagemDAO.close();
    }

    public Requerente getRequerente() {
        return requerente;
    }

    public void setRequerente(Requerente requerente) {
        this.requerente = requerente;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public MapModel getMapa() {
        return mapa;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(boolean confirmacao) {
        this.confirmacao = confirmacao;
    }
    
    public StreamedContent getImage() {
        if(imagem != null){
            InputStream is = new ByteArrayInputStream(imagem.getDados());
            image = new DefaultStreamedContent(is);
        }
        else{
            image = null;
        }
        return image;
    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }
      
    public void addMarker() {
        Marker marker = new Marker(new LatLng(localizacao.getLatitude(),localizacao.getLongitude()) ,localizacao.getTitulo());
        mapa.addOverlay(marker);
        marker.setDraggable(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marcador adicionado", "Lat:" + localizacao.getLatitude() + ", Lng:" + localizacao.getLongitude()));
    }

    public void onMarkerDrag(MarkerDragEvent event) {
        Marker marker;
        marker = event.getMarker();
          
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Dragged", "Lat:" + marker.getLatlng().getLat() + ", Lng:" + marker.getLatlng().getLng()));
    }
    
    public void continuar(){
        if(imagem != null && localizacao.getLatitude() != null)
            this.confirmacao = true;
        if(imagem == null)
            FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage("Faça o upload da imagem para continuar"));
        if(localizacao.getLatitude() == null)
            FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage("Indique a localização no mapa"));
    }
    
    public void cancelar(){
        this.confirmacao = false;        
    }

    public void concluir() {
        localizacao.setTitulo(solicitacao.getDescricao());
        localizacaoDAO.insert(localizacao);
        imagemDAO.insert(imagem);
        solicitacao.setStatus('S'); // Solicitado
        solicitacao.setDataSolicitacao(new Date(System.currentTimeMillis()));
        solicitacao.setRequerente(requerente);
        solicitacao.setLocalizacao(localizacao);
        solicitacao.setImagem(imagem);
        solicitacaoDAO.insert(solicitacao); 
        imagem = null;
        localizacao = new Localizacao();
        solicitacao = new Solicitacao();
        confirmacao = false;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pronto", "Solicitação enviada com sucesso"));
    }

    public void upload(FileUploadEvent evt) throws IOException{
        file = evt.getFile();

        if (file != null){
            imagem = new Imagem();
            imagem.setDados(IOUtils.toByteArray(file.getInputstream()));
            imagem.setNome(file.getFileName());

            image = new DefaultStreamedContent(file.getInputstream());
        }
    }

    public void carregarRequerente(){
        
        Profile perfil = (Profile) Sessao.getObjectSession("perfil");
        if(perfil != null){
                        
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
                        
            List<Requerente> lista_requerente = new ArrayList<>();
            
            lista_requerente = requerenteDAO.getByNamedQuery(Requerente.class,"Requerente.findById","id", perfil.getValidatedId());
            
            if(!lista_requerente.isEmpty())
                return;

            requerenteDAO.insert(requerente);
        }
    }

}
