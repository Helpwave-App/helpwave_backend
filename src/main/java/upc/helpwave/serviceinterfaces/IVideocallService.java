package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.Videocall;

import java.util.List;

public interface IVideocallService {
    public void insert(Videocall videocall);

    public void delete(Integer idVideocall);

    public Videocall listId(Integer idVideocall);

    public List<Videocall> list();
}
