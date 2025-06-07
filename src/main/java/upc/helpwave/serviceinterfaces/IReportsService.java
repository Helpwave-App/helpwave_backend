package upc.helpwave.serviceinterfaces;
import upc.helpwave.entities.Reports;

import java.util.List;

public interface IReportsService {
    public void insert(Reports reports);

    public void delete(Integer idReport);

    public Reports listId(Integer idReport);

    public List<Reports> list();
}
