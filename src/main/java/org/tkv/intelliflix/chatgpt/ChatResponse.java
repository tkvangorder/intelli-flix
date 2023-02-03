package org.tkv.intelliflix.chatgpt;

import lombok.Value;

import java.util.List;

@Value
public class ChatResponse {
    private List<Choice> choices;
    private Error error;

    @Value
    public static class Choice {
        private String text;
    }

    @Value
    public static class Error {
        private String message;
    }
}
