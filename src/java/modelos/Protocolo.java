/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author root
 */
@Entity
@Table(name = "protocolo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Protocolo.findAll", query = "SELECT p FROM Protocolo p")
    , @NamedQuery(name = "Protocolo.findById", query = "SELECT p FROM Protocolo p WHERE p.id = :id")
    , @NamedQuery(name = "Protocolo.findByDataProtocolo", query = "SELECT p FROM Protocolo p WHERE p.dataProtocolo = :dataProtocolo")
    , @NamedQuery(name = "Protocolo.findByDescricao", query = "SELECT p FROM Protocolo p WHERE p.descricao = :descricao")
    , @NamedQuery(name = "Protocolo.findByStatus", query = "SELECT p FROM Protocolo p WHERE p.status = :status")
    , @NamedQuery(name = "Protocolo.findByAnonimo", query = "SELECT p FROM Protocolo p WHERE p.anonimo = :anonimo")})
public class Protocolo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data_protocolo")
    @Temporal(TemporalType.DATE)
    private Date dataProtocolo;
    @Size(max = 2147483647)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "status")
    private Character status;
    @Column(name = "anonimo")
    private Boolean anonimo;
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
    private Setor setor;

    public Protocolo() {
    }

    public Protocolo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataProtocolo() {
        return dataProtocolo;
    }

    public void setDataProtocolo(Date dataProtocolo) {
        this.dataProtocolo = dataProtocolo;
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

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
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
        if (!(object instanceof Protocolo)) {
            return false;
        }
        Protocolo other = (Protocolo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Protocolo[ id=" + id + " ]";
    }
    
}
