package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Reports;
import upc.helpwave.repositories.ReportsRepository;
import upc.helpwave.serviceinterfaces.IReportsService;

import java.util.List;

@Service
public class ReportServiceImplement implements IReportsService {
    @Autowired
    private ReportsRepository rR;

    @Override
    public void insert(Reports reports) {
        rR.save(reports);
    }
    @Override
    public void delete(Integer idReport) {
        rR.deleteById(idReport);
    }
    @Override
    public Reports listId(Integer idReport) {
        return rR.findById(idReport).orElse(new Reports());
    }
    @Override
    public List<Reports> list() {
        return rR.findAll();
    }
}
