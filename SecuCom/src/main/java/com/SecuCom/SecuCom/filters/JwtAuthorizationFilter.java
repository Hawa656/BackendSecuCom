package com.SecuCom.SecuCom.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter  {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*try {
            String authorizationToken= request.getHeader("Authorization");
            if (authorizationToken !=null && authorizationToken.startsWith("Bearer ")){
                String jwt=authorizationToken.substring("Bearer ".length());
                Algorithm algorithm=Algorithm.HMAC256("mysecret".getBytes());
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT= jwtVerifier.verify(jwt);
                String username=decodedJWT.getSignature();
                String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
                filterChain.doFilter(request, response);
            }
        }catch (Exception e){
            response.setHeader("error", e.getMessage());
        }*/


        String authorizationToken= request.getHeader("Authorization");
        if (authorizationToken !=null && authorizationToken.startsWith("Bearer ")){
            try {
                String jwt=authorizationToken.substring("Bearer ".length());
                Algorithm algorithm=Algorithm.HMAC256("mysecret".getBytes());
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT= jwtVerifier.verify(jwt);
                String username=decodedJWT.getSignature();
                String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
                filterChain.doFilter(request, response);
            }catch (Exception e){
                response.setHeader("error", e.getMessage());
            }

        }else{
            filterChain.doFilter(request, response);
        }

    }
}
