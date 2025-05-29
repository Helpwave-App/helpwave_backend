package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.Comments;

import java.util.List;

public interface ICommentsService {
    public void insert(Comments comments);

    public void delete(Integer idComment);

    public Comments listId(Integer idComment);

    public List<Comments> list();
}
