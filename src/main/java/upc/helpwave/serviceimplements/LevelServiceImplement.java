package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Level;
import upc.helpwave.repositories.LevelRepository;
import upc.helpwave.serviceinterfaces.ILevelService;

import java.util.List;

@Service
public class LevelServiceImplement implements ILevelService {
    @Autowired
    private LevelRepository lR;

    @Override
    public void insert(Level level) {
        lR.save(level);
    }

    @Override
    public void delete(Integer idLevel) {
        lR.deleteById(idLevel);
    }

    @Override
    public Level listId(Integer idLevel) {
        return lR.findById(idLevel).orElse(new Level());
    }

    @Override
    public List<Level> list() {
        return lR.findAll();
    }
}
