package com.SecuCom.SecuCom.repository;

import com.SecuCom.SecuCom.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByNomRole(String NomRole);
}
