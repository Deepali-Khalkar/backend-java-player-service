package controller.chat;

import com.app.playerservicejava.controller.chat.ChatController;
import com.app.playerservicejava.service.chat.ChatClientService;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ChatControllerTest {

    @Mock
    private ChatClientService chatClientService;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testChat() throws OllamaBaseException, IOException, InterruptedException {
        // Arrange
        String expectedResponse = "This is a generated chat response.";
        when(chatClientService.chat()).thenReturn(expectedResponse);

        // Act
        String response = chatController.chat();

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(chatClientService, times(1)).chat();
    }

    @Test
    void testChat_ExceptionThrown() throws OllamaBaseException, IOException, InterruptedException {
        // Arrange
        when(chatClientService.chat()).thenThrow(new IOException("Failed to connect"));

        // Act & Assert
        try {
            chatController.chat();
        } catch (IOException e) {
            assertEquals("Failed to connect", e.getMessage());
        }
        verify(chatClientService, times(1)).chat();
    }

    @Test
    void testListModels() throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        Model model1 = new Model();
        model1.setName("TinyLlama");

        Model model2 = new Model();
        model2.setName("LargeLlama");


        // Arrange
        List<Model> mockModels = Arrays.asList(model1, model2);
        when(chatClientService.listModels()).thenReturn(mockModels);

        // Act
        ResponseEntity<List<Model>> response = chatController.listModels();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals("TinyLlama", response.getBody().get(0).getName());
        assertEquals("LargeLlama", response.getBody().get(1).getName());
        verify(chatClientService, times(1)).listModels();
    }

    @Test
    void testListModels_ExceptionThrown() throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        // Arrange
        when(chatClientService.listModels()).thenThrow(new OllamaBaseException("Failed to fetch models"));

        // Act & Assert
        try {
            chatController.listModels();
        } catch (OllamaBaseException e) {
            assertEquals("Failed to fetch models", e.getMessage());
        }
        verify(chatClientService, times(1)).listModels();
    }
}