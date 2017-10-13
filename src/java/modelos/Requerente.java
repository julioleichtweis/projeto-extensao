package modelos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "requerente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Requerente.findAll", query = "SELECT r FROM Requerente r")
    , @NamedQuery(name = "Requerente.findById", query = "SELECT r FROM Requerente r WHERE r.id = :id")
    , @NamedQuery(name = "Requerente.findByNome", query = "SELECT r FROM Requerente r WHERE r.nome = :nome")
    , @NamedQuery(name = "Requerente.findByEmail", query = "SELECT r FROM Requerente r WHERE r.email = :email")
    , @NamedQuery(name = "Requerente.findByFoto", query = "SELECT r FROM Requerente r WHERE r.foto = :foto")
    , @NamedQuery(name = "Requerente.findByDataNasc", query = "SELECT r FROM Requerente r WHERE r.dataNasc = :dataNasc")
    , @NamedQuery(name = "Requerente.findBySexo", query = "SELECT r FROM Requerente r WHERE r.sexo = :sexo")})
public class Requerente implements Serializable {

    @OneToMany(mappedBy = "requerente")
    private List<Solicitacao> solicitacaoList;
    @OneToMany(mappedBy = "requerente")
    private List<Comentario> comentarioList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "id")
    private String id;
    @Size(max = 2147483647)
    @Column(name = "nome")
    private String nome;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @Size(max = 2147483647)
    @Column(name = "foto")
    private String foto;
    @Size(max = 2147483647)
    @Column(name = "data_nasc")
    private String dataNasc;
    @Size(max = 2147483647)
    @Column(name = "sexo")
    private String sexo;

    public Requerente() {
    }

    public Requerente(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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
        if (!(object instanceof Requerente)) {
            return false;
        }
        Requerente other = (Requerente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Requerente[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Solicitacao> getSolicitacaoList() {
        return solicitacaoList;
    }

    public void setSolicitacaoList(List<Solicitacao> solicitacaoList) {
        this.solicitacaoList = solicitacaoList;
    }

    @XmlTransient
    public List<Comentario> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
    }
    
}
