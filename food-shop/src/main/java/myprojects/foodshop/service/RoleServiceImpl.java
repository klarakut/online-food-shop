package myprojects.foodshop.service;

import lombok.extern.slf4j.Slf4j;
import myprojects.foodshop.models.Role;
import myprojects.foodshop.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional // I need everything in this class to be transactional
@Slf4j // for log
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database",role.getName());
        return roleRepository.save(role);
    }
}
