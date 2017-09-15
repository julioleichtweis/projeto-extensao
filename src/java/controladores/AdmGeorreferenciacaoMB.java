package controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelos.Localizacao;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import persistencia.DAO;

@Named(value = "admGeorreferenciacaoMB")
@ViewScoped
public class AdmGeorreferenciacaoMB implements Serializable {

    List<Localizacao> lista_localizacoes;
    
    DAO<Localizacao> localizacaoDAO;
    
    private MapModel mapa;
      

    public AdmGeorreferenciacaoMB(){
    }

    @PostConstruct
    public void inicializar() {
        lista_localizacoes = new ArrayList<>();
        localizacaoDAO = new DAO<>("Projeto-ExtensaoPU");
        mapa = new DefaultMapModel();
        carregarLocalizacoes();
    }

    @PreDestroy
    public void fechar() {
        localizacaoDAO.close();
    }

    public List<Localizacao> getLista_localizacoes() {
        return lista_localizacoes;
    }

    public void setLista_localizacoes(List<Localizacao> lista_localizacoes) {
        this.lista_localizacoes = lista_localizacoes;
    }

    public MapModel getMapa() {
        return mapa;
    }

    public void carregarLocalizacoes(){
        lista_localizacoes = localizacaoDAO.getAll(Localizacao.class, "Localizacao.findAll");
        Marker marker;
        int x=0;
        for(Localizacao loc : lista_localizacoes){
            marker = new Marker(new LatLng(loc.getLatitude(),loc.getLongitude()) ,loc.getTitulo());
            mapa.addOverlay(marker);
            x++;
        }
        System.out.println("Tamanho : "+x);
    }
    
}

