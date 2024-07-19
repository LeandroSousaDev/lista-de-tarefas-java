package br.com.leandrosousa.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.leandrosousa.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.equals("/tasks/")) {

            // coletar usuario e senha
            var authorization = request.getHeader("Authorization");
            var senhaCodificada = authorization.substring("Basic".length()).trim();
            byte[] senhaDecodificada = Base64.getDecoder().decode(senhaCodificada);
            var senhaString = new String(senhaDecodificada);
            String[] dadosUsuario = senhaString.split(":");
            String username = dadosUsuario[0];
            String password = dadosUsuario[1];

            // validar usuario
            var user = userRepository.findByUsername(username);

            if (user == null) {
                response.sendError(401);
            }
            // validar senha
            var senhaValidada = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

            if (senhaValidada.verified) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401);
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

}
