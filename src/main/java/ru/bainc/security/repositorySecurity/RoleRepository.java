package ru.bainc.security.repositorySecurity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bainc.security.modelSecurity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
