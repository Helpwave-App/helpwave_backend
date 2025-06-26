package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import upc.helpwave.entities.Request;
import upc.helpwave.repositories.RequestRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class RequestServiceImplementTest {

    @InjectMocks
    private RequestServiceImplement service;

    @Mock
    private RequestRepository requestRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void expireUnansweredRequests_shouldUpdateExpiredRequests() {
        Request expired = new Request();
        expired.setStateRequest(true); // Simula estado pendiente

        List<Request> expiredList = List.of(expired);
        when(requestRepository.findTrulyPendingRequestsOlderThan(any())).thenReturn(expiredList);

        service.expireUnansweredRequests();

        verify(requestRepository, times(1)).findTrulyPendingRequestsOlderThan(any());
        verify(requestRepository, times(1)).save(argThat(req -> !req.getStateRequest()));
    }

    @Test
    void expireUnansweredRequests_shouldDoNothingIfNoExpiredRequests() {
        when(requestRepository.findTrulyPendingRequestsOlderThan(any())).thenReturn(Collections.emptyList());

        service.expireUnansweredRequests();

        verify(requestRepository, never()).save(any());
    }
}
