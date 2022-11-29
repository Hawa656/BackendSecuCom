package com.SecuCom.SecuCom.model;

import com.SecuCom.SecuCom.filters.JwtAuthentificationFilter;
import com.SecuCom.SecuCom.filters.JwtAuthorizationFilter;
import com.SecuCom.SecuCom.service.UtilisateurService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.HttpMethod.GET;
//Pour configurer

@Data
//Indique qu'il s'agit d'une classe de configuration
@Configuration
//Permet à spring boot de savoir où se trouve les configurations
@EnableWebSecurity
//Notre class de configuration va heritée de WebSecurityConfigurerAdapter pour nous permettre de gerer notre chaine de filtres
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //Nous avons deux methodes
    @Autowired
    private UtilisateurService utilisateurService;
    //Pour gerer nos filtres nous avons besoins de la methode configure qui prendra en entré nos requettes http
    //    @Override permet de redefinir une methode

    @Override
    // $$$$$$$AuthenticationManagerBuilder qui permet de specifier les uers qui sont autorisés;
    // Elle nous permet d'utiliser les identifiants venat de la base de donnée(elle permet de gérer l'authentification)
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Utilisateurs utilisateurs=utilisateurService.RetournerUnUtilisateurParSonNom(username);
                Collection<GrantedAuthority> autorities=new ArrayList<>();
                utilisateurs.getRoles().forEach(r -> {
                    autorities.add(new SimpleGrantedAuthority(r.getNomRole()));
                });
                return new User(utilisateurs.getNomUtilisateur(),utilisateurs.getMotDePasse(),autorities);
            }
        });
    }
    @Override
    //$$$$$$$$Ainsi que la methode qui prend en paramettre HttpSecurity il permet de specifier les droits d'accès;
    // il permet de (HttpSecurity) de faire passer toute les requettes à travers la chaine de filtre de sécurité
    // et configurer le formulaire de connexion par defaut avec la methode form login()
    protected void configure(HttpSecurity http) throws Exception {
        //CSRF (Cross-Site Request Forgery)
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.headers().frameOptions().disable();
        //permet d'afficher le formulaire quand on veut accéder alors qu'on a pas le droit
        http.authorizeRequests().antMatchers("/API/login").permitAll();
        http.authorizeRequests().antMatchers(GET,"/API/utilisateurs/**").hasAnyAuthority("user");
        http.formLogin();
        //autoriser l'accès à toute les fonctionnalités permitall()
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(new JwtAuthentificationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}