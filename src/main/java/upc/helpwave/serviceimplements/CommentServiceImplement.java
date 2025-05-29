package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Comments;
import upc.helpwave.repositories.CommentsRepository;
import upc.helpwave.serviceinterfaces.ICommentsService;

import java.util.List;

@Service
public class CommentServiceImplement implements ICommentsService {
    @Autowired
    private CommentsRepository cR;

    @Override
    public void insert(Comments comments) {
        cR.save(comments);
    }

    @Override
    public void delete(Integer idComment) {
        cR.deleteById(idComment);
    }

    @Override
    public Comments listId(Integer idComment) {
        return cR.findById(idComment).orElse(new Comments());
    }

    @Override
    public List<Comments> list() {
        return cR.findAll();
    }
}
