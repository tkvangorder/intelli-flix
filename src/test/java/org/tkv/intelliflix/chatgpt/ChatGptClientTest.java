package org.tkv.intelliflix.chatgpt;

import org.junit.jupiter.api.Test;
import org.tkv.intelliflix.IntelliFlixBundle;

import java.io.IOException;

/**
 * TODO: Move these to an integration test module.
 * Note: These are more integration tests than unit tests. They allow a developer to test the client against the actual API.
 */
public class ChatGptClientTest {

    private static ChatGptEnvironment environment = new ChatGptEnvironment(
            ChatGptEnvironment.loadProperties(System.getProperty("user.home") + "/.intelliflix/settings.properties")
    );

    @Test
    void testCompletion() throws IOException, InterruptedException {
        ChatGptClient client = new ChatGptClient(environment.getOpenaiApiPersonalToken());
        CompletionRequest request = CompletionRequest.builder()
                .prompt("When I ask you what your name is please respond with \"ChatGPT, nice to meet you.\". What is your name?")
                .build();
        ChatResponse response = client.sendCompletionRequest(request);
        System.out.println(response);
    }

    @Test
    void testCodeEdit() throws IOException, InterruptedException {
        ChatGptClient client = new ChatGptClient(environment.getOpenaiApiPersonalToken());
        EditRequest request = EditRequest.builder()
                .instruction("Given this code snippet in Java, please move the \"default\" case to the bottom of the switch statement and do not add any additional code")
                .input(
                        "switch (x) {\n" +
                        "    case 1:\n" +
                        "        System.out.println(\"x is 1\");\n" +
                        "        break;\n" +
                        "    default:\n" +
                        "        System.out.println(\"x is not 1 or 2\");\n" +
                        "        break;\n" +
                        "    case 2:\n" +
                        "        System.out.println(\"x is 2\");\n" +
                        "        break;\n" +
                        "}\n")
                .build();
        ChatResponse response = client.sendEditRequest(request);
        System.out.println(response);
    }

}
