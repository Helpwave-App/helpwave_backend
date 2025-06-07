package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.TypeReport;

import java.util.List;

public interface ITypeReportService {
    public void insert(TypeReport typeReport);

    public void delete(Integer idTypeReport);

    public TypeReport listId(Integer idTypeReport);

    public List<TypeReport> list();
}
