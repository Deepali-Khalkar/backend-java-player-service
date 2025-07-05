package service.chat;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.Model;
import io.github.ollama4j.models.OllamaResult;
import io.github.ollama4j.types.OllamaModelType;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.PromptBuilder;
import com.app.playerservicejava.service.chat.ChatClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatClientServiceTest {

    @Mock
    private OllamaAPI ollamaAPI;

    @InjectMocks
    private ChatClientService chatClientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListModels() throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        // Arrange
        Model model1 = new Model();
        model1.setName("Model1");

        Model model2 = new Model();
        model2.setName("Model2");

        List<Model> mockModels = Arrays.asList(model1, model2);
        when(ollamaAPI.listModels()).thenReturn(mockModels);

        // Act
        List<Model> result = chatClientService.listModels();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Model1", result.get(0).getName());
        assertEquals("Model2", result.get(1).getName());
        verify(ollamaAPI, times(1)).listModels();
    }

    @Test
    void testListModels_ExceptionThrown() throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        // Arrange
        when(ollamaAPI.listModels()).thenThrow(new IOException("Failed to fetch models"));

        // Act & Assert
        assertThrows(IOException.class, () -> chatClientService.listModels());
        verify(ollamaAPI, times(1)).listModels();
    }

    @Test
    void testChat() throws OllamaBaseException, IOException, InterruptedException {
        // Arrange
        String expectedResponse = "Recursion is deep,\nA function calling itself,\nInfinite haiku.";
        OllamaResult mockResult = mock(OllamaResult.class);
        when(mockResult.getResponse()).thenReturn(expectedResponse);
        OptionsBuilder optionsBuilder = new OptionsBuilder();

        when(ollamaAPI.generate(
                eq(OllamaModelType.TINYLLAMA),
                anyString(),
                eq(false),
                any(optionsBuilder.build().getClass())
        )).thenReturn(mockResult);

        // Act
        String result = chatClientService.chat();

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(ollamaAPI, times(1)).generate(
                eq(OllamaModelType.TINYLLAMA),
                anyString(),
                eq(false),
                any(optionsBuilder.build().getClass())
        );
    }

    @Test
    void testChat_ExceptionThrown() throws OllamaBaseException, IOException, InterruptedException {
        OptionsBuilder optionsBuilder = new OptionsBuilder();
        // Arrange
        when(ollamaAPI.generate(
                eq(OllamaModelType.TINYLLAMA),
                anyString(),
                eq(false),
                any(optionsBuilder.build().getClass())
        )).thenThrow(new IOException("Failed to generate chat response"));

        // Act & Assert
        assertThrows(IOException.class, () -> chatClientService.chat());
        verify(ollamaAPI, times(1)).generate(
                eq(OllamaModelType.TINYLLAMA),
                anyString(),
                eq(false),
                any(optionsBuilder.build().getClass())
        );
    }
}