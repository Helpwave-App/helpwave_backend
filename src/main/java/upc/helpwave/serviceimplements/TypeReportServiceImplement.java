package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.TypeReport;
import upc.helpwave.repositories.TypeReportRepository;
import upc.helpwave.serviceinterfaces.ITypeReportService;

import java.util.List;

@Service
public class TypeReportServiceImplement implements ITypeReportService {
    @Autowired
    private TypeReportRepository tRR;

    @Override
    public void insert(TypeReport typeReport) {
        tRR.save(typeReport);
    }
    @Override
    public void delete(Integer idTypeReport) {
        tRR.deleteById(idTypeReport);
    }
    @Override
    public TypeReport listId(Integer idTypeReport) {
        return tRR.findById(idTypeReport).orElse(new TypeReport());
    }
    @Override
    public List<TypeReport> list() {
        return tRR.findAll();
    }
}
