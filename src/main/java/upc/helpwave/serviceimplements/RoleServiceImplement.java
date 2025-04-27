package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Role;
import upc.helpwave.repositories.RoleRepository;
import upc.helpwave.serviceinterfaces.IRoleService;

import java.util.List;
@Service
public class RoleServiceImplement implements IRoleService {
    @Autowired
    private RoleRepository rR;
    @Override
    public void insert(Role role) {
        rR.save(role);
    }

    @Override
    public void delete(Long idRole) {
        rR.deleteById(idRole);
    }
    @Override
    public List<Role> list() {
        return rR.findAll();
    }

    @Override
    public Role listId(Long idRole) {
        return rR.findById(idRole).orElse(new Role());
    }


}
