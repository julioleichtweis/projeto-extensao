package seguranca;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter("/paginas_autenticadas/*")
public class FiltroAutenticacao implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy(){        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        
       HttpSession sessao = ((HttpServletRequest)request).getSession();
      
        if(sessao.getAttribute("usuario_logado") == null){ 
            //sessao.setAttribute("aviso", "falha na autenticação"); 
            ((HttpServletResponse) response).sendRedirect("../login.xhtml");
        }
        else
            chain.doFilter(request,response);
    }
    
}
