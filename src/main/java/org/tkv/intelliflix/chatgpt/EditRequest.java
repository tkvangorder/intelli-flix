package org.tkv.intelliflix.chatgpt;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * A code edit request to be sent to the OpenAI API. This is not a complete representation of the request, but only the parts
 * currently used by the client.
 *
 * This class provides a builder that will set default values for the model and temperature fields when they are not
 * otherwise provided.
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = EditRequest.EditRequestBuilder.class)
public class EditRequest {

    String instruction;

    String input;

    String model;

    double temperature;

    public static class EditRequestBuilder {

        public EditRequest build() {
            if (model == null) {
                model = "code-davinci-edit-001";
            }
            if (temperature == 0) {
                temperature = 0.3;
            }
            if (instruction == null || instruction.isBlank()) {
                throw new IllegalArgumentException("Instruction cannot be empty");
            }
            return new EditRequest(instruction, input, model, temperature);
        }
    }
}
