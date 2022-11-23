package com.SecuCom.SecuCom.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomUtilisateur;
    private  String motDePasse;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles=new ArrayList<>();
}
