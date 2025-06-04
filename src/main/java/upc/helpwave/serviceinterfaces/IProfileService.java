package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.Profile;

import java.util.List;
import java.util.Optional;

public interface IProfileService {
    public void insert(Profile profile);

    public void delete(Integer idProfile);

    public Profile listId(Integer idProfile);

    public List<Profile> list();

    Optional<Profile> findById(Integer id);
}
