package ru.bainc.security.serviceSecurity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bainc.security.modelSecurity.Role;
import ru.bainc.security.repositorySecurity.RoleRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;

  //  private final

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional
    public Optional<Role> getById(Long id){
        return roleRepository.findById(id);
    }
}
