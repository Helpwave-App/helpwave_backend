package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Language;
import upc.helpwave.repositories.LanguageRepository;
import upc.helpwave.serviceinterfaces.ILanguageService;

import java.util.List;

@Service
public class LanguageServiceImplement implements ILanguageService {
    @Autowired
    private LanguageRepository lR;

    @Override
    public void insert(Language language) {
        lR.save(language);
    }
    @Override
    public void delete(Integer idLanguage) {
        lR.deleteById(idLanguage);
    }
    @Override
    public Language listId(Integer idLanguage) {
        return lR.findById(idLanguage).orElse(new Language());
    }
    @Override
    public List<Language> list() {
        return lR.findAll();
    }
}
