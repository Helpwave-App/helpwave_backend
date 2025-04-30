package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.User;
import upc.helpwave.repositories.UserRepository;
import upc.helpwave.serviceinterfaces.IUserService;

import java.util.List;

@Service
public class UserServiceImplement implements IUserService {
    @Autowired
    private UserRepository uR;

    @Autowired
    private PasswordEncoder pE;

    @Override
    public Integer insert(User user) {
        String newPassword = pE.encode(user.getPassword());
        user.setPassword(newPassword);

        int exists = uR.buscarUsername(user.getUsername());
        if (exists == 0) {
            uR.save(user);
        }
        return exists;
    }

    @Override
    public void delete(int idUser) {
        uR.deleteById(idUser);
    }

    @Override
    public User listId(int idUser) {
        return uR.findById(idUser).orElse(new User());
    }

    @Override
    public List<User> list() {
        return uR.findAll();
    }
}