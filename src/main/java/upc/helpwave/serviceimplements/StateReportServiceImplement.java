package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.StateReport;
import upc.helpwave.repositories.StateReportRepository;
import upc.helpwave.serviceinterfaces.IStateReportService;


import java.util.List;

@Service
public class StateReportServiceImplement implements IStateReportService {
    @Autowired
    private StateReportRepository sRR;

    @Override
    public void insert(StateReport stateReport) {
        sRR.save(stateReport);
    }
    @Override
    public void delete(Integer idStateReport) {
        sRR.deleteById(idStateReport);
    }
    @Override
    public StateReport listId(Integer idStateReport) {
        return sRR.findById(idStateReport).orElse(new StateReport());
    }
    @Override
    public List<StateReport> list() {
        return sRR.findAll();
    }
}
