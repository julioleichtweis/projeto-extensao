package controladores;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelos.Imagem;
import modelos.Localizacao;
import modelos.Protocolo;
import modelos.Requerente;
import org.apache.commons.io.IOUtils;
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

@Named(value = "registarSolicitacaoMB")
@SessionScoped
public class RegistrarSolicitacaoMB implements Serializable {

    Requerente requerente;
    Protocolo protocolo;
    Localizacao localizacao;
    Imagem imagem;
    
    DAO<Requerente> requerenteDAO;
    DAO<Protocolo> protocoloDAO;
    DAO<Localizacao> localizacaoDAO;
    DAO<Imagem> imagemDAO;
    
    private MapModel mapa;
      
    private UploadedFile file;
    private StreamedContent image;

    private boolean confirmacao;

    public RegistrarSolicitacaoMB(){
    }

    @PostConstruct
    public void inicializar(){
        requerente = new Requerente();
        protocolo = new Protocolo();
        localizacao = new Localizacao();
        
        requerenteDAO = new DAO<>("Projeto-ExtensaoPU");
        protocoloDAO = new DAO<>("Projeto-ExtensaoPU");
        localizacaoDAO = new DAO<>("Projeto-ExtensaoPU");
        imagemDAO = new DAO<>("Projeto-ExtensaoPU");

        mapa = new DefaultMapModel();
        
        confirmacao = false;
    }

    @PreDestroy
    public void fechar() {
        requerenteDAO.close();
        protocoloDAO.close();
        localizacaoDAO.close();
        imagemDAO.close();
    }
    
    public Requerente getRequerente() {
        return requerente;
    }

    public void setRequerente(Requerente requerente) {
        this.requerente = requerente;
    }

    public Protocolo getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(Protocolo protocolo) {
        this.protocolo = protocolo;
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
        else
            image = null;
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
        getProtocolo().getLocalizacao().setLatitude(localizacao.getLatitude());
        getProtocolo().getLocalizacao().setLongitude(localizacao.getLongitude());
    }

    public void onMarkerDrag(MarkerDragEvent event) {
        Marker marker;
        marker = event.getMarker();
          
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Dragged", "Lat:" + marker.getLatlng().getLat() + ", Lng:" + marker.getLatlng().getLng()));
    }

    public void salvarLocalizacao(){
        //localizacao.setTitulo("nada");
        //localizacaoDAO.insert(localizacao);
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marcador salvo", "Lat:" + localizacao.getLatitude()+ ", Lng:" + localizacao.getLongitude()));
        //localizacao = new Localizacao();
    }
    
    public void continuar(){
        this.confirmacao = true;
    }
    
    public void cancelar(){
        this.confirmacao = false;        
    }

    public void concluir() {
        localizacao.setTitulo(protocolo.getDescricao());
        localizacaoDAO.insert(localizacao);
        imagemDAO.insert(imagem);
        requerenteDAO.insert(requerente);
        protocolo.setStatus('S'); // Solicitado
        protocolo.setDataProtocolo(new Date(System.currentTimeMillis()));
        protocolo.setRequerente(requerente);
        protocolo.setLocalizacao(localizacao);
        protocolo.setImagem(imagem);
    }
    /*private String getDateTime() { 
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	Date date = new Date(); 
	return dateFormat.format(date); 
}*/

    public void upload(FileUploadEvent evt) throws IOException{
        file = evt.getFile();

        if (file != null){
            imagem = new Imagem();
            imagem.setDados(IOUtils.toByteArray(file.getInputstream()));
            imagem.setNome(file.getFileName());

            image = new DefaultStreamedContent(file.getInputstream());
      
            //FacesMessage message = new FacesMessage("Upload realizado com sucesso", file.getFileName());
            //FacesContext.getCurrentInstance().addMessage(null, message);
            //InputStream input = file.getInputstream();
            //String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
            //File saida = new File(path + "/resources/images/teste.png");
            //OutputStream os = new FileOutputStream(saida); 
            //imagemDAO.insert(imagem);
        }
    }
}
