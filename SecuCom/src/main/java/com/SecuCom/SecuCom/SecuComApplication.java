package com.SecuCom.SecuCom;

import com.SecuCom.SecuCom.model.Role;
import com.SecuCom.SecuCom.model.Utilisateurs;
import com.SecuCom.SecuCom.service.UtilisateurService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SecuComApplication {
	//private UtilisateurService utilisateurService;

	public static void main(String[] args) {
		SpringApplication.run(SecuComApplication.class, args);
	}

	@Bean
	CommandLineRunner start( UtilisateurService utilisateurService){
		return args -> {
			utilisateurService.AjouterRole(new Role(null,"user"));
			utilisateurService.AjouterRole(new Role(null,"Admin"));

			utilisateurService.AjouterUtilisateurs(new Utilisateurs(null,"kalifa","1234",new ArrayList<>()));
			utilisateurService.AjouterUtilisateurs(new Utilisateurs(null,"chaka","1234",new ArrayList<>()));
			utilisateurService.AjouterUtilisateurs(new Utilisateurs(null,"bourama","1234",new ArrayList<>()));

			utilisateurService.AjouterUnRoleAunUtilisateur("kalifa","user");
			utilisateurService.AjouterUnRoleAunUtilisateur("chaka","admin");
			utilisateurService.AjouterUnRoleAunUtilisateur("chaka","user");
			utilisateurService.AjouterUnRoleAunUtilisateur("bourama","admin");





		};
	}

	}

