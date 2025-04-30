package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.Role;

import java.util.List;

public interface IRoleService {
    public void insert(Role role);
    public void delete(Long idRole);
    public Role listId(Long idRole);
    public List<Role> list();
}
