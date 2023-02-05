package org.tkv.intelliflix.chatgpt;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * A completion request to be sent to the OpenAI API. This is not a complete representation of the request, but only the parts
 * currently used by the client.
 *
 * This class provides a builder that will set default values for the model, temperature and maxTokens fields when they
 * are not otherwise provided.
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = CompletionRequest.CompletionRequestBuilder.class)
public class CompletionRequest {

    String prompt;

    String model;

    double temperature;

    int maxTokens;

    public static class CompletionRequestBuilder {
        public CompletionRequest build() {
            if (model == null) {
                model = "text-davinci-003";
            }
            if (temperature == 0) {
                temperature = 0.7;
            }
            if (maxTokens == 0) {
                maxTokens = 50;
            }
            if (prompt == null || prompt.isBlank()) {
                throw new IllegalArgumentException("Prompt cannot be empty");
            }
            return new CompletionRequest(prompt, model, temperature, maxTokens);
        }
    }
}
