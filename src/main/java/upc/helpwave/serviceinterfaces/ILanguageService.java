package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.Language;
import java.util.List;

public interface ILanguageService {
    public void insert(Language language);

    public void delete(Integer idLanguage);

    public Language listId(Integer idLanguage);

    public List<Language> list();
}
