package org.tkv.intelliflix.chatgpt;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

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
