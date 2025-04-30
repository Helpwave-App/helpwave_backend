package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.User;

import java.util.List;

public interface IUserService {
    public Integer insert(User user);

    public void delete(int idUser);

    public User listId(int idUser);

    public List<User> list();

    public boolean existsByUsername(String username);
}
