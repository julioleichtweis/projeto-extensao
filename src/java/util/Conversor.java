package util;

import persistencia.DAO;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelos.Lotacao;

@FacesConverter(forClass = Lotacao.class, value = "conversorLotacao")
public class Conversor implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,String descricao){
        Lotacao lotacao = null;
        DAO<Lotacao> lotacaoDAO = new DAO<>("Projeto-ExtensaoPU");
        lotacao = lotacaoDAO.getLotacaoPorDescricao(descricao);
        lotacaoDAO.close();
        return lotacao;
    }

    @Override
    public String getAsString(FacesContext context,UIComponent component,Object value ){
        Lotacao lotacao = (Lotacao) value;
        return lotacao.getSigla();
    }
}
