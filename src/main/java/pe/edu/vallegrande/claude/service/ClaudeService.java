package pe.edu.vallegrande.claude.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import pe.edu.vallegrande.claude.model.Chat;
import pe.edu.vallegrande.claude.repository.ChatRepository;


import java.util.List;


@Service
public class ClaudeService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ChatRepository chatRepository;

    public String sendMessage(String userContent) {
        HttpHeaders headers = createHeaders();
        List<Chat> conversation = chatRepository.findAll();
        JSONObject requestBody = createRequestBody(conversation, userContent);

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        // Send POST request
        String response = restTemplate.postForObject("https://api.anthropic.com/v1/messages",
                requestEntity, String.class);

        // Parse response
        String claudeResponse = new JSONObject(response).getJSONArray("content")
                .getJSONObject(0).getString("text");

        String userMessage = new JSONObject(userContent).getString("message");

        // Save chat in database
        saveChat(userMessage, claudeResponse);

        return claudeResponse;
    }

    // Create headers
    private HttpHeaders createHeaders() {
        // Load API key from .env file
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");
        headers.set("max_tokens", "1024");

        return headers;
    }

    // Create request body
    private JSONObject createRequestBody(List<Chat> conversation, String userContent) {

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "claude-3-opus-20240229");
        requestBody.put("max_tokens", 1024);

        for (Chat chat : conversation) {
            JSONObject messageObject = new JSONObject();
            messageObject.put("role", "user");
            messageObject.put("content", chat.getMessage());
            requestBody.append("messages", messageObject);

            messageObject = new JSONObject();
            messageObject.put("role", "assistant");
            messageObject.put("content", chat.getResponse());
            requestBody.append("messages", messageObject);
        }

        JSONObject messageObject = new JSONObject();
        messageObject.put("role", "user");
        messageObject.put("content", userContent);

        // Add message to requestBody
        requestBody.append("messages", messageObject);

        return requestBody;
    }

    // Save chat in database
    private void saveChat(String userMessage, String claudeResponse) {
        Chat chat = new Chat();
        chat.setMessage(userMessage);
        chat.setResponse(claudeResponse);
        chatRepository.save(chat);
    }
}