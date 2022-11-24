package com.SecuCom.SecuCom.controller;

import com.SecuCom.SecuCom.model.Role;
import com.SecuCom.SecuCom.model.RoleUtilisateurForm;
import com.SecuCom.SecuCom.model.Utilisateurs;
import com.SecuCom.SecuCom.service.UtilisateurService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import java.nio.file.Path;
import java.util.List;

@RestController
public class UtilisateurController {
    private UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    //afficher la liste des utilisateurs
    @GetMapping(path = "/utilisateurs")
    public List<Utilisateurs> utilisateurs(){
        return utilisateurService.listUtilisateurs();

    }
    //Ajouter un utilisateur
    @PostMapping(path = "/Utilisateur")
    public Utilisateurs saveUtilisateurs(@RequestBody Utilisateurs utilisateurs){
        return utilisateurService.AjouterUtilisateurs(utilisateurs);

    }
    //Ajouter un role
    @PostMapping(path = "/role")
    public Role ajouterRole(@RequestBody Role role){
        return utilisateurService.AjouterRole(role);
    }
    //Ajouter un role à un utilisateur
    @PostMapping(path = "/ajouterUnRoleAunUtilisateur")
    public void AjouterUnRoleAunUtilisateur(@RequestBody RoleUtilisateurForm roleUtilisateurForm){
         utilisateurService.AjouterUnRoleAunUtilisateur(roleUtilisateurForm.getNomUtilisateur(),roleUtilisateurForm.getNomRole());
    }
}

