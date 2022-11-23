package com.SecuCom.SecuCom.serviceImplementation;

import com.SecuCom.SecuCom.model.Role;
import com.SecuCom.SecuCom.model.Utilisateurs;
import com.SecuCom.SecuCom.repository.RoleRepository;
import com.SecuCom.SecuCom.repository.UtilisateursRepository;
import com.SecuCom.SecuCom.service.UtilisateurService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional

public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateursRepository utilisateursRepository;
    private RoleRepository roleRepository;

    public UtilisateurServiceImpl(UtilisateursRepository utilisateursRepository, RoleRepository roleRepository) {
        this.utilisateursRepository = utilisateursRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Utilisateurs AjouterUtilisateurs(Utilisateurs utilisateurs) {
        return utilisateursRepository.save(utilisateurs);
    }

    @Override
    public Role AjouterRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void AjouterUnRoleAunUtilisateur(String nomUtilisateur, String nomRole) {
        Utilisateurs utilisateurs=utilisateursRepository.findByNomUtilisateur(nomUtilisateur);
        Role role=roleRepository.findByNomRole(nomRole);
        utilisateurs.getRoles().add(role);

    }

    @Override
    public Utilisateurs RetournerUnUtilisateurParSonNom(String nomUtilisateur) {
        return utilisateursRepository.findByNomUtilisateur(nomUtilisateur);
    }

    @Override
    public List<Utilisateurs> listUtilisateurs() {
        return utilisateursRepository.findAll();
    }
}
