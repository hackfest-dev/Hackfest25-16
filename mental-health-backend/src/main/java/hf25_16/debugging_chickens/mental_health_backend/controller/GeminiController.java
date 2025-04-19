package hf25_16.debugging_chickens.mental_health_backend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/gemini")
@RequiredArgsConstructor
public class GeminiController {

    @Value("${spring.ai.google.gemini.api-key}")
    private String apiKey;

    @Value("${spring.ai.google.gemini.url}")
    private String geminiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/process")
    public ResponseEntity<String> processInput(@RequestBody String userInput) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build Gemini API request body
        Map<String, Object> part = new HashMap<>();
        part.put("text", userInput);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", new Object[]{part});

        Map<String, Object> body = new HashMap<>();
        body.put("contents", new Object[]{content});

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        String fullUrl = geminiUrl + "?key=" + apiKey;

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            // Parse and extract the final message
            JsonNode root = objectMapper.readTree(response.getBody());
            String message = root.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText();

            return ResponseEntity.ok(message);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Gemini API call failed: " + e.getMessage());
        }
    }
}
