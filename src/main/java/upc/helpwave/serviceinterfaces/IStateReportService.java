package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.StateReport;
import java.util.List;

public interface IStateReportService {
    public void insert(StateReport stateReport);

    public void delete(Integer idStateReport);

    public StateReport listId(Integer idStateReport);

    public List<StateReport> list();
}
