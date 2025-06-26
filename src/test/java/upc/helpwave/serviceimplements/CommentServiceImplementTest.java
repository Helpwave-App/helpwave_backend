package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import upc.helpwave.entities.Comments;
import upc.helpwave.repositories.CommentsRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceImplementTest {

    @InjectMocks
    private CommentServiceImplement commentService;

    @Mock
    private CommentsRepository commentsRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insert_shouldSaveComment() {
        Comments comment = new Comments();
        commentService.insert(comment);
        verify(commentsRepository, times(1)).save(comment);
    }

    @Test
    public void delete_shouldRemoveCommentById() {
        Integer id = 1;
        commentService.delete(id);
        verify(commentsRepository, times(1)).deleteById(id);
    }

    @Test
    public void listId_shouldReturnCommentWhenExists() {
        Comments comment = new Comments();
        when(commentsRepository.findById(1)).thenReturn(Optional.of(comment));
        Comments result = commentService.listId(1);
        assertEquals(comment, result);
    }

    @Test
    public void listId_shouldReturnEmptyCommentWhenNotExists() {
        when(commentsRepository.findById(1)).thenReturn(Optional.empty());
        Comments result = commentService.listId(1);
        assertNotNull(result);
    }

    @Test
    public void list_shouldReturnAllComments() {
        List<Comments> comments = Arrays.asList(new Comments(), new Comments());
        when(commentsRepository.findAll()).thenReturn(comments);
        List<Comments> result = commentService.list();
        assertEquals(2, result.size());
    }
}
