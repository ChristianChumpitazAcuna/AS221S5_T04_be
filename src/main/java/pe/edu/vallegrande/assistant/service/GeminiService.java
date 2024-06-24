package pe.edu.vallegrande.assistant.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.assistant.model.GeminiChat;
import pe.edu.vallegrande.assistant.repository.GeminiChatRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class GeminiService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private GeminiChatRepository geminiChatRepository;

    @Value("${GEMINI_API_KEY}")
    private String apiKey;

    // method sendMessage
    public Mono<String> sendMessage(UUID userId, String userMessage) {
        Flux<GeminiChat> conversation = geminiChatRepository.findByUserIdAndStatusOrderById(userId, "A");
        Mono<JSONObject> requestBody = createRequestBody(conversation, userMessage);

        return requestBody.flatMap(body -> {
            WebClient webClient = webClientBuilder.build();

            return webClient.post()
                    .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro-latest:generateContent?key=" + apiKey)
                    .bodyValue(body.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(response -> {
                        // Parse response
                        String geminiResponse = new JSONObject(response)
                                .getJSONArray("candidates")
                                .getJSONObject(0)
                                .getJSONObject("content")
                                .getJSONArray("parts")
                                .getJSONObject(0)
                                .getString("text");
                        // Save chat in database and return response Gemini
                        return saveChat(userId, userMessage, geminiResponse)
                                .thenReturn(geminiResponse);
                    });
        });

    }

    public Mono<String> updateSendMessage(Long id, UUID userId, String userMessage) {
        Mono<GeminiChat> conversation = geminiChatRepository.findByIdAndUserIdAndStatus(id, userId, "A");
        JSONObject requestBody = createRequestBodyUpdated(userMessage);

        return conversation.flatMap(chat -> {
            WebClient webClient = webClientBuilder.build();

            return webClient.post()
                    .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro-latest:generateContent?key=" + apiKey)
                    .bodyValue(requestBody.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(response -> {
                        // Parse response
                        String geminiResponse = new JSONObject(response)
                                .getJSONArray("candidates")
                                .getJSONObject(0)
                                .getJSONObject("content")
                                .getJSONArray("parts")
                                .getJSONObject(0)
                                .getString("text");
                        // Save chat in database and return response Gemini
                        return updateChat(id, userMessage, geminiResponse)
                                .thenReturn(geminiResponse);
                    });
        });

    }

    // method create request body
    private Mono<JSONObject> createRequestBody(Flux<GeminiChat> conversation, String userMessage) {

        return conversation.collectList().map(chats -> {
            JSONObject requestBody = new JSONObject();

            JSONObject generationConfig = new JSONObject();
            generationConfig.put("temperature", 1);
            generationConfig.put("topK", 0);
            generationConfig.put("topP", 0.95);
            generationConfig.put("maxOutputTokens", 8192);
            generationConfig.put("stopSequences", new String[0]);

            requestBody.put("generationConfig", generationConfig);

            for (GeminiChat chat : chats) {
                JSONObject messageObject = new JSONObject();
                messageObject.put("role", "user");
                messageObject.put("parts", new JSONObject().put("text", chat.getMessage()));
                requestBody.append("contents", messageObject);

                messageObject = new JSONObject();
                messageObject.put("role", "model");
                messageObject.put("parts", new JSONObject().put("text", chat.getResponse()));
                requestBody.append("contents", messageObject);
            }

            JSONObject messageObject = new JSONObject();
            messageObject.put("role", "user");
            messageObject.put("parts", new JSONObject().put("text", userMessage));

            requestBody.append("contents", messageObject);

            return requestBody;
        });

    }

    private JSONObject createRequestBodyUpdated(String userMessage) {
        JSONObject requestBody = new JSONObject();

        JSONObject generationConfig = new JSONObject();
        generationConfig.put("temperature", 1);
        generationConfig.put("topK", 0);
        generationConfig.put("topP", 0.95);
        generationConfig.put("maxOutputTokens", 8192);
        generationConfig.put("stopSequences", new String[0]);

        requestBody.put("generationConfig", generationConfig);

        JSONObject messageObject = new JSONObject();
        messageObject.put("role", "user");
        messageObject.put("parts", new JSONObject().put("text", userMessage));
        requestBody.append("contents", messageObject);

        return requestBody;
    }

    private Mono<GeminiChat> saveChat(UUID userId, String userMessage, String geminiResponse) {
        GeminiChat chat = new GeminiChat();
        chat.setUserId(userId);
        chat.setMessage(userMessage);
        chat.setResponse(geminiResponse);
        return geminiChatRepository.save(chat);
    }

    private Mono<GeminiChat> updateChat(Long id, String userMessage, String geminiResponse) {
        return geminiChatRepository.findById(id)
                .flatMap(chat -> {
                    chat.setMessage(userMessage);
                    chat.setResponse(geminiResponse);
                    return geminiChatRepository.save(chat);
                });
    }

    public Mono<GeminiChat> changeStatus(Long id, String status) {
        return geminiChatRepository.findById(id)
                .flatMap(chat -> {
                    chat.setStatus(status);
                    return geminiChatRepository.save(chat);
                });
    }
}
