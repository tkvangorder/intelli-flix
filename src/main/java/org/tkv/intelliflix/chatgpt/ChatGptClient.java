package org.tkv.intelliflix.chatgpt;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * A simple client for the OpenAI GPT-3 API. This client uses the simply HTTP client that is part of Java and the
 * only additional dependencies are on Jackson for JSON serialization and deserialization.
 *
 * This client should be moved to a separate library, that would allow it to be shared across Intellij and VSCode.
 * I have not worked with VSCode, but I believe we could create a language server that is running on a JVM and
 * calls to CHAT-GPT would available through the language server protocol. Maybe...
 */
public class ChatGptClient {
    private static final String DEFAULT_OPENAI_API_URL = "https://api.openai.com/v1";
    private final String openaiApiKey;
    private final URI openaiApiUrlCompletions;

    private final URI openaiApiUrlEdits;

    private final HttpClient client = HttpClient.newHttpClient();

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
            .build()
            .registerModule(new ParameterNamesModule())
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public ChatGptClient(@NotNull String openaiApiKey) {
        this(openaiApiKey, DEFAULT_OPENAI_API_URL);
    }

    public ChatGptClient(@NotNull String openaiApiKey, @Nullable String openaiApiUrl) {
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

    /**
     * Send a completion request to the OpenAI API. This is similar to the experience a user would have when using the
     * Chat-GPT web interface. Each time this method is called, it will consume OpenAI's API quota and callers can
     * limit the number of tokens that are used to reduce the cost of the request.
     *
     * @param request The completion request to send
     * @return The response from the OpenAI API
     * @throws IOException If there is an error sending the request or reading the response
     * @throws InterruptedException If the request is interrupted
     */
    public @NotNull ChatResponse sendCompletionRequest(@NotNull CompletionRequest request) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(openaiApiUrlCompletions)
                .header("Authorization", "Bearer " + openaiApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(request)))
                .build();
        return objectMapper.readValue(client.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body(), ChatResponse.class);
    }

    /**
     *  Send an edit request to the OpenAI API. An edit request can supply both an input and instructions to the API.
     *  The input is typically a code snippet and the instructions are a description of what the user wants to do with
     *  the code.
     *
     * @param request The edit request to send
     * @return The response from the OpenAI API
     * @throws IOException If there is an error sending the request or reading the response
     * @throws InterruptedException If the request is interrupted
     */
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
