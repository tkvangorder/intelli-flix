package org.tkv.intelliflix.chatgpt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provide an abstraction for environment variables and properties used by the ChatGptClient. This is in anticipation
 * of externalizing this as a library. Currently the only two settings are the access key and the API URL. The
 * access key is required and the API URL will default to the OpenAI API URL, if not specified.
 */
public class ChatGptEnvironment {

    public static final String OPENAI_API_PERSONAL_TOKEN_KEY = "openai.api.access.key";
    public static final String OPENAI_API_URL_KEY = "openai.api.url";

    Properties properties;

    public ChatGptEnvironment(Properties additionalProperties) {
        properties = additionalProperties;
    }

    @Nullable
    public String getOpenaiApiAccessKey() {
        return getValue(OPENAI_API_PERSONAL_TOKEN_KEY);
    }

    public void setOpenaiApiAccessKey(@Nullable String openaiApiAccessKey) {
        properties.setProperty(OPENAI_API_PERSONAL_TOKEN_KEY, openaiApiAccessKey);
    }

    @Nullable
    public String getOpenaiApiUrl() {
        return getValue(OPENAI_API_URL_KEY);
    }

    public void setOpenaiApiUrl(@Nullable String openaiApiUrl) {
        properties.setProperty(OPENAI_API_URL_KEY, openaiApiUrl);
    }

    public @NotNull String getValue(@NotNull String key) {
        String value = System.getProperty(key);
        return value != null ? value : properties.getProperty(key);
    }

    /**
     * Helper method to load properties from a file.
     * @param filename The name of the file to load properties from.
     * @return The properties loaded from the file.
     */
    @NotNull
    public static Properties loadProperties(@NotNull String filename) {
        try (InputStream input = new FileInputStream(filename)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot load properties from " + filename, ex);
        }
    }
}
