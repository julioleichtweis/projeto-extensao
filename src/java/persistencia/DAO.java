package persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import modelos.Lotacao;
import modelos.Solicitacao;

public class DAO<T>{

    private EntityManager em;
    
    public DAO(String pu) {
        if (this.em == null) {
            this.em = Persistence.createEntityManagerFactory(pu).createEntityManager();
        }
    }
   
    public void insert(T object){
        this.em.getTransaction().begin();
        this.em.persist(object);
        this.em.getTransaction().commit();
    }
    
    public void update(T object){
        this.em.getTransaction().begin();
        this.em.merge(object);
        this.em.getTransaction().commit();        
    }    
    
    public void delete(T object){
        this.em.getTransaction().begin();
        this.em.remove(object);
        this.em.getTransaction().commit();        
    }
    
    public T get(Class<T> c, long id){
        return this.em.find(c, id);
    }
    public T get(Class<T> c, String id){
        return this.em.find(c, id);
    }

    public T get(Class<T> c, int id){
        return this.em.find(c, id);
    }
    
    public List<T> getAll(Class<T> c, String sql){
        Query query = this.em.createNamedQuery(sql, c);
        return query.getResultList();
    }

    public List<T> getByNamedQuery(Class<T> c,String sql,String parametro1,String valor1,
                                                         String parametro2,String valor2){
        Query query = this.em.createNamedQuery(sql,c);
        query.setParameter(parametro1,valor1);
        query.setParameter(parametro2,valor2);
        return query.getResultList();
    }
        
    public List<T> getByNamedQuery(Class<T> c,String sql,String parametro1,Solicitacao valor1){
        Query query = this.em.createNamedQuery(sql,c);
        query.setParameter(parametro1,valor1);
        return query.getResultList();
    }

    public List<T> getByNamedQuery(Class<T> c,String sql,String parametro1,String valor1){
        Query query = this.em.createNamedQuery(sql,c);
        query.setParameter(parametro1,valor1);
        return query.getResultList();
    }

    public T getLotacaoPorDescricao(String descricao){
        Query query = this.em.createNamedQuery("Lotacao.findByCodNome",Lotacao.class);
        query.setParameter("sigla",descricao);
        return (T)query.getSingleResult();
    }

    public void close(){
        this.em.close();
    }
}
