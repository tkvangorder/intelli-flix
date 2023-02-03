package org.tkv.intelliflix.chatgpt;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

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
