package modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "solicitacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitacao.findAll", query = "SELECT s FROM Solicitacao s")
    , @NamedQuery(name = "Solicitacao.findById", query = "SELECT s FROM Solicitacao s WHERE s.id = :id")
    , @NamedQuery(name = "Solicitacao.findByDataSolicitacao", query = "SELECT s FROM Solicitacao s WHERE s.dataSolicitacao = :dataSolicitacao")
    , @NamedQuery(name = "Solicitacao.findByDescricao", query = "SELECT s FROM Solicitacao s WHERE s.descricao = :descricao")
    , @NamedQuery(name = "Solicitacao.findByStatus", query = "SELECT s FROM Solicitacao s WHERE s.status = :status")
    , @NamedQuery(name = "Solicitacao.findByAnonimo", query = "SELECT s FROM Solicitacao s WHERE s.anonimo = :anonimo")
    , @NamedQuery(name = "Solicitacao.findByEndereco", query = "SELECT s FROM Solicitacao s WHERE s.endereco = :endereco")})
public class Solicitacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data_solicitacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacao;
    @Size(max = 2147483647)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "status")
    private Character status;
    @Column(name = "anonimo")
    private Boolean anonimo;
    @Size(max = 2147483647)
    @Column(name = "endereco")
    private String endereco;
    @JoinColumn(name = "funcionario", referencedColumnName = "id")
    @ManyToOne
    private Funcionario funcionario;
    @JoinColumn(name = "imagem", referencedColumnName = "id")
    @ManyToOne
    private Imagem imagem;
    @JoinColumn(name = "localizacao", referencedColumnName = "id")
    @ManyToOne
    private Localizacao localizacao;
    @JoinColumn(name = "prioridade", referencedColumnName = "id")
    @ManyToOne
    private Prioridade prioridade;
    @JoinColumn(name = "requerente", referencedColumnName = "id")
    @ManyToOne
    private Requerente requerente;
    @JoinColumn(name = "setor", referencedColumnName = "id")
    @ManyToOne
    private Lotacao lotacao;

    public Solicitacao() {
    }

    public Solicitacao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Boolean getAnonimo() {
        return anonimo;
    }

    public void setAnonimo(Boolean anonimo) {
        this.anonimo = anonimo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Requerente getRequerente() {
        return requerente;
    }

    public void setRequerente(Requerente requerente) {
        this.requerente = requerente;
    }

    public Lotacao getLotacao() {
        return lotacao;
    }

    public void setLotacao(Lotacao lotacao) {
        this.lotacao = lotacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitacao)) {
            return false;
        }
        Solicitacao other = (Solicitacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Solicitacao[ id=" + id + " ]";
    }
    
}
