package upc.helpwave.serviceinterfaces;
import upc.helpwave.entities.Skill;

import java.util.List;

public interface ISkillService {
    public void insert(Skill skill);

    public void delete(Integer idSkill);

    public Skill listId(Integer idSkill);

    public List<Skill> list();
}
