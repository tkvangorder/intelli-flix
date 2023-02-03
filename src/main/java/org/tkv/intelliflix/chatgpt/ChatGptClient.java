package org.tkv.intelliflix.chatgpt;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * A simple client for the OpenAI GPT-3 API.
 * <p>
 * See https://beta.openai.com/docs/api-reference/create-completion
 */
public class ChatGptClient {
    private static final String DEFAULT_OPENAI_API_URL = "https://api.openai.com/v1";
    private final String openaiApiKey;
    private final URI openaiApiUrlCompletions;

    private final URI openaiApiUrlEdits;

    // Just using the standard Java 11 HttpClient, to reduce dependencies on external libraries
    private final HttpClient client = HttpClient.newHttpClient();

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
            .build()
            .registerModule(new ParameterNamesModule())
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public ChatGptClient(String openaiApiKey) {
        this(openaiApiKey, DEFAULT_OPENAI_API_URL);
    }

    public ChatGptClient(String openaiApiKey, String openaiApiUrl) {
        if (openaiApiKey == null) {
            throw new IllegalArgumentException("You must supply a valid OpenAI API key to use this client");
        }
        if (!openaiApiKey.endsWith("/")) {
            openaiApiUrl += "/";
        }
        this.openaiApiKey = openaiApiKey;
        this.openaiApiUrlCompletions = URI.create(openaiApiUrl + "completions");
        this.openaiApiUrlEdits = URI.create(openaiApiUrl + "edits");
    }

    public ChatResponse sendCompletionRequest(CompletionRequest request) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(openaiApiUrlCompletions)
                .header("Authorization", "Bearer " + openaiApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(request)))
                .build();
        return objectMapper.readValue(client.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body(), ChatResponse.class);
    }

    public ChatResponse sendEditRequest(EditRequest request) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(openaiApiUrlEdits)
                .header("Authorization", "Bearer " + openaiApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(request)))
                .build();
        return objectMapper.readValue(client.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body(), ChatResponse.class);
    }

}
