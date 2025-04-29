package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.Level;

import java.util.List;

public interface ILevelService {
    public void insert(Level level);

    public void delete(Integer idLevel);

    public Level listId(Integer idLevel);

    public List<Level> list();
}
