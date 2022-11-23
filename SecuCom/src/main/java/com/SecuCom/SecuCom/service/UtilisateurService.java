package com.SecuCom.SecuCom.service;

import com.SecuCom.SecuCom.model.Role;
import com.SecuCom.SecuCom.model.Utilisateurs;

import java.util.List;

public interface UtilisateurService {
    Utilisateurs AjouterUtilisateurs(Utilisateurs utilisateurs);
    Role AjouterRole(Role role);
    void AjouterUnRoleAunUtilisateur(String nomUtilisateur, String nomRole);
    Utilisateurs RetournerUnUtilisateurParSonNom(String nomUtilisateur);
    List<Utilisateurs>listUtilisateurs();
}
