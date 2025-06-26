package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Skill;
import upc.helpwave.repositories.SkillRepository;
import upc.helpwave.serviceinterfaces.ISkillService;

import java.util.List;

@Service
public class SkillServiceImplement implements ISkillService {
    @Autowired
    private SkillRepository sR;

    public SkillServiceImplement(SkillRepository sR) {
        this.sR = sR;
    }

    @Override
    public void insert(Skill skill) {
        sR.save(skill);
    }

    @Override
    public void delete(Integer idSkill) {
        sR.deleteById(idSkill);
    }

    @Override
    public Skill listId(Integer idSkill) {
        return sR.findById(idSkill).orElse(new Skill());
    }

    @Override
    public List<Skill> list() {
        return sR.findAll();
    }
}
