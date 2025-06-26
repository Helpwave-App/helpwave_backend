package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.CommentsDTO;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.CommentsRepository;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.VideocallRepository;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.ICommentsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentsController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "firebase.enabled=false")
@TestPropertySource(locations = "classpath:application-test.properties")
class CommentsControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ICommentsService commentsService;

        @MockBean
        private CommentsRepository commentsRepository;

        @MockBean
        private VideocallRepository videocallRepository;

        @MockBean
        private ProfileRepository profileRepository;

        @MockBean
        private JwtUserDetailsService jwtUserDetailsService;

        @MockBean
        private JwtRequestFilter jwtRequestFilter;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void registerComment_shouldSaveCommentAndUpdateProfileScore_whenScoreAboveThreshold() throws Exception {
                // Arrange
                int score = 4;
                CommentsDTO dto = new CommentsDTO();
                dto.setIdVideocall(1);
                dto.setDescriptionComment("Muy buena asistencia");
                dto.setScoreVolunteer(score);
                dto.setScoreVideocall(5);

                Profile volunteer = new Profile();
                volunteer.setAssistances(2);
                volunteer.setScoreProfile(new BigDecimal("4.5"));

                Empairing empairing = new Empairing();
                empairing.setProfile(volunteer);

                Videocall videocall = new Videocall();
                videocall.setEmpairing(empairing);

                Mockito.when(videocallRepository.findById(1)).thenReturn(Optional.of(videocall));
                Mockito.when(commentsRepository.save(any(Comments.class))).thenReturn(null);
                Mockito.when(profileRepository.save(any(Profile.class))).thenReturn(null);

                // Act & Assert
                mockMvc.perform(post("/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Comentario registrado correctamente."));
        }

        @Test
        void delete_shouldInvokeServiceDeleteMethod() throws Exception {
                int id = 10;

                mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .delete("/comments/{id}", id))
                                .andExpect(status().isOk());

                Mockito.verify(commentsService).delete(id);
        }

        @Test
        void listId_shouldReturnCommentDTO_whenIdExists() throws Exception {
                int id = 5;

                Comments comment = new Comments();
                comment.setDescriptionComment("Buen soporte");
                comment.setScoreVolunteer(4);
                comment.setScoreVideocall(5);
                Videocall videocall = new Videocall();
                comment.setVideocall(videocall);

                Mockito.when(commentsService.listId(id)).thenReturn(comment);

                mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .get("/comments/{id}", id))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.descriptionComment").value("Buen soporte"))
                                .andExpect(jsonPath("$.scoreVolunteer").value(4))
                                .andExpect(jsonPath("$.scoreVideocall").value(5));
        }

        @Test
        void list_shouldReturnListOfCommentDTOs() throws Exception {
                Comments comment1 = new Comments();
                comment1.setDescriptionComment("Excelente");
                comment1.setScoreVolunteer(5);
                comment1.setScoreVideocall(5);
                comment1.setVideocall(new Videocall());

                Comments comment2 = new Comments();
                comment2.setDescriptionComment("Regular");
                comment2.setScoreVolunteer(2);
                comment2.setScoreVideocall(3);
                comment2.setVideocall(new Videocall());

                Mockito.when(commentsService.list()).thenReturn(List.of(comment1, comment2));

                mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .get("/comments"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].descriptionComment").value("Excelente"))
                                .andExpect(jsonPath("$[1].descriptionComment").value("Regular"));
        }

        @Test
        void update_shouldCallInsertWithMappedEntity() throws Exception {
                CommentsDTO dto = new CommentsDTO();
                dto.setIdVideocall(3);
                dto.setDescriptionComment("Actualizado");
                dto.setScoreVolunteer(4);
                dto.setScoreVideocall(5);

                mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .put("/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isOk());

                Mockito.verify(commentsService)
                                .insert(Mockito.argThat(comment -> comment.getDescriptionComment().equals("Actualizado")
                                                && comment.getScoreVolunteer() == 4
                                                && comment.getScoreVideocall() == 5));
        }

}
