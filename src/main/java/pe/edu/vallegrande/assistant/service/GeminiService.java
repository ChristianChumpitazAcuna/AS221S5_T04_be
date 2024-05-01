package pe.edu.vallegrande.assistant.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.assistant.model.GeminiChat;
import pe.edu.vallegrande.assistant.repository.GeminiChatRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GeminiService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private GeminiChatRepository geminiChatRepository;

    // method sendMessage
    public Mono<String> sendMessage(String userContent) {
        Flux<GeminiChat> conversation = geminiChatRepository.findAll();
        Mono<JSONObject> requestBody = createRequestBody(conversation, userContent);

        // Load API key
        Dotenv dotenv = Dotenv.configure().load();
        String apiKey = dotenv.get("GEMINI_API_KEY");

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
                        return saveChat(userContent, geminiResponse)
                                .thenReturn(geminiResponse);
                    });
        });

    }

    // method create request body
    private Mono<JSONObject> createRequestBody(Flux<GeminiChat> conversation, String userContent) {

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
            messageObject.put("parts", new JSONObject().put("text", userContent));

            requestBody.append("contents", messageObject);

            return requestBody;
        });

    }

    private Mono<GeminiChat> saveChat(String userContent, String geminiResponse) {
        GeminiChat chat = new GeminiChat();
        chat.setMessage(userContent);
        chat.setResponse(geminiResponse);
        return geminiChatRepository.save(chat);
    }
}
