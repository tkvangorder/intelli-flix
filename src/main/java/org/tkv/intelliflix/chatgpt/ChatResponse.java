package org.tkv.intelliflix.chatgpt;

import lombok.Value;

import java.util.List;

/**
 * The response from the OpenAI API. This is not a complete representation of the response, but only the parts
 * currently used by the client.
 *
 * TODO: Add usage fields to the response, useful information to display to the user.
 */
@Value
public class ChatResponse {
    List<Choice> choices;
    Error error;

    @Value
    public static class Choice {
        String text;
    }

    @Value
    public static class Error {
        String message;
    }
}
