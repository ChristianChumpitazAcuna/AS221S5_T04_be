package pe.edu.vallegrande.assistant.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.assistant.model.ClaudeChat;
import pe.edu.vallegrande.assistant.repository.ClaudeChatRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ClaudeService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ClaudeChatRepository chatRepository;

    public Mono<String> sendMessage(String userContent) {
        HttpHeaders headers = createHeaders();
        Flux<ClaudeChat> conversation = chatRepository.findAll();
        Mono<JSONObject> requestBody = createRequestBody(conversation, userContent);

        return requestBody.flatMap(body -> {

            // Create WebClient
            WebClient webClient = webClientBuilder.defaultHeaders(httpHeaders ->
                    httpHeaders.addAll(headers)).build();

            // Send request to Claude API
            return webClient.post()
                    .uri("https://api.anthropic.com/v1/messages")
                    .bodyValue(body.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(response -> {

                        // Parse response
                        String claudeResponse = new JSONObject(response)
                                .getJSONArray("content")
                                .getJSONObject(0)
                                .getString("text");

                        // Save chat in database and return response Claude
                        return saveChat(userContent, claudeResponse)
                                .thenReturn(claudeResponse);
                    });
        });
    }

    // Create headers
    private HttpHeaders createHeaders() {
        // Load API key from .env file
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("CLAUDE_API_KEY");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");
        headers.set("max_tokens", "1024");

        return headers;
    }

    // Create request body
    private Mono<JSONObject> createRequestBody(Flux<ClaudeChat> conversation, String userContent) {

        return conversation.collectList().map(chats -> {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "claude-3-opus-20240229");
            requestBody.put("max_tokens", 1024);

            for (ClaudeChat chat : chats) {
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
        });
    }

    // Save chat in database
    private Mono<ClaudeChat> saveChat(String userMessage, String claudeResponse) {
        ClaudeChat chat = new ClaudeChat();
        chat.setMessage(userMessage);
        chat.setResponse(claudeResponse);
        return chatRepository.save(chat);
    }
}