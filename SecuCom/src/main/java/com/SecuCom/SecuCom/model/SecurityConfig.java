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
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UtilisateurService utilisateurService;
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
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.headers().frameOptions().disable();
        //permet d'afficher le formulaire quand on veut accéder alors qu'on a pas le droit
        http.formLogin();
        //autoriser l'accès à toute les fonctionnalités
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JwtAuthentificationFilter(authenticationManagerBean()));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
