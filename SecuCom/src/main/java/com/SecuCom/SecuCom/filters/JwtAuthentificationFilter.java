package com.SecuCom.SecuCom.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //quand l'utilisateur va tenter de se connecter
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("--- Essai d'authentification attemptAuthentification ----");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }
    //quand l'authentification a reussi
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println(" ---- Authentification Reusi ---");
        User user=(User) authResult.getPrincipal();
        Algorithm algorithm=Algorithm.HMAC256("mysecret".getBytes());
        //creation du token
        //Un Access token est un token qui n'a pas une longue durée de vie
        String JwtAccesToken= JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date((System.currentTimeMillis()+5*60*1000)))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm);
        //creation d'un refresh token pour que quand notre token expire on puisse lui donner une autre durée de vie

        Map<String,String> idToken=new HashMap<>();
        idToken.put("access-token",JwtAccesToken);
        response.setContentType("application/json");
        //on va envoyer l'objet dans le format json le corps de la reponse
        //ObjectMapper Uitiliser en spring pour serialiser un objet en json
        new ObjectMapper().writeValue(response.getOutputStream(),idToken);

        //pour integre le jwt on a besoin d'integerer une bibliotheque

    }
}
