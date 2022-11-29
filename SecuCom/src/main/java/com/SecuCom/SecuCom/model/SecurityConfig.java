package com.SecuCom.SecuCom.model;

import com.SecuCom.SecuCom.filters.JwtAuthentificationFilter;
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
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
@Data
        //montre qu'il s'agit d'une classe de configuration
@Configuration
//permet à spring de savoir ou se trouve les pages de configurations
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UtilisateurService utilisateurService;
    //établir un mécanisme d'authentification en permettant aux AuthenticationProviders d'être ajoutés facilement
    @Override
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
    protected void configure(HttpSecurity http) throws Exception {
        //ce n'est pas la peine de generer le csrf  et de le placer dans la  session car je ne vais pas l'utiliser
        //permet de desactiver le csrf
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.headers().frameOptions().disable();
        //permet d'afficher le formulaire quand on veut accéder alors qu'on a pas le droit
        http.formLogin();
        //autoriser l'accès à toute les fonctionnalités
        //toute les requettes neccesiite yune authentifications authenticated
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JwtAuthentificationFilter(authenticationManagerBean()));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
